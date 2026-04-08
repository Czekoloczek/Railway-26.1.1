package com.railwayteam.railways.neoforge;

import com.mojang.authlib.GameProfile;
import com.railwayteam.railways.content.conductor.ConductorEntity;
import com.railwayteam.railways.content.conductor.IConductorHoldingFakePlayer;
import net.minecraft.network.Connection;
import net.minecraft.network.PacketSendListener;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * Fabric implementation of the conductor fake player.
 *
 * Fabric has no {@code FakePlayer} base class, so this extends {@link ServerPlayer}
 * directly and suppresses all network I/O via an overridden
 * {@link ServerGamePacketListenerImpl}. The connection object is assigned in a
 * two-phase pattern: super() constructs the player (which internally calls
 * {@link ServerPlayer#getGameProfile()} before {@code this.connection} is set),
 * then we replace the connection handler with our no-op subclass.
 */
public class ConductorFakePlayerForge extends ServerPlayer implements IConductorHoldingFakePlayer {

    private static final Connection NETWORK_MANAGER = new Connection(PacketFlow.CLIENTBOUND);

    private final WeakReference<ConductorEntity> conductor;

    public ConductorFakePlayerForge(ServerLevel level, ConductorEntity conductor) {
        super(level.getServer(), level,
                new GameProfile(
                        UUID.nameUUIDFromBytes("ConductorFakePlayer".getBytes()),
                        "[ConductorFakePlayer]"),
                CommonListenerCookie.createInitial(
                        new GameProfile(
                                UUID.nameUUIDFromBytes("ConductorFakePlayer".getBytes()),
                                "[ConductorFakePlayer]"),
                        false));
        this.conductor = new WeakReference<>(conductor);
        // Replace the auto-created connection with a no-op handler after super() finishes.
        this.connection = new SilentNetHandler(level.getServer(), NETWORK_MANAGER, this);
    }

    @Override
    public @Nullable ConductorEntity getConductor() {
        return conductor.get();
    }

    @Override
    public @NotNull OptionalInt openMenu(@Nullable MenuProvider menu) {
        return OptionalInt.empty();
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("[ConductorFakePlayer]");
    }

    @Override
    public float getCurrentItemAttackStrengthDelay() {
        return 1 / 64f;
    }

    @Override
    public boolean canEat(boolean ignoreHunger) {
        return false;
    }

    @Override
    public Vec3 position() {
        ConductorEntity c = conductor.get();
        return c != null ? c.position() : super.position();
    }

    /** No-op connection handler – discards all outbound packets silently. */
    private static class SilentNetHandler extends ServerGamePacketListenerImpl {
        SilentNetHandler(MinecraftServer server, Connection connection, ServerPlayer player) {
            super(server, connection, player,
                    CommonListenerCookie.createInitial(player.getGameProfile(), false));
        }

        @Override
        public void send(@NotNull Packet<?> packet) {}

        @Override
        public void send(@NotNull Packet<?> packet, @Nullable PacketSendListener listener) {}

        @Override
        public void disconnect(@NotNull Component reason) {}
    }
}
