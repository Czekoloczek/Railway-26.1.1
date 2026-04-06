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
 * Mirrors ConductorFakePlayerForge but without the NeoForge FakePlayer superclass.
 * TODO: On Fabric there is no FakePlayer base class – consider using
 *       fabric-gametest-api or a simple ServerPlayer subclass.
 */
public class ConductorFakePlayerForge extends ServerPlayer implements IConductorHoldingFakePlayer {

    private static final Connection NETWORK_MANAGER = new Connection(PacketFlow.CLIENTBOUND);

    private final WeakReference<ConductorEntity> conductor;

    public ConductorFakePlayerForge(ServerLevel level, ConductorEntity conductor) {
        super(level.getServer(), level,
                new GameProfile(UUID.nameUUIDFromBytes("ConductorFakePlayer".getBytes()),
                        "[ConductorFakePlayer]"),
                CommonListenerCookie.createInitial(
                        new GameProfile(UUID.nameUUIDFromBytes("ConductorFakePlayer".getBytes()),
                                "[ConductorFakePlayer]"), false));
        this.conductor = new WeakReference<>(conductor);
        // Suppress packet sending for fake player
        this.connection = new ServerGamePacketListenerImpl(
                level.getServer(), NETWORK_MANAGER, this,
                CommonListenerCookie.createInitial(getGameProfile(), false)) {
            @Override
            public void send(@NotNull Packet<?> packet, @Nullable PacketSendListener listener) {}
            @Override
            public void disconnect(@NotNull Component reason) {}
        };
    }

    @Override
    public @Nullable ConductorEntity getConductor() {
        return conductor.get();
    }

    @Override
    public OptionalInt openMenu(@Nullable MenuProvider menu) {
        return OptionalInt.empty();
    }

    @Override
    public void teleportTo(double x, double y, double z) {
        setPos(x, y, z);
    }

    @Override
    public Vec3 position() {
        ConductorEntity c = conductor.get();
        return c != null ? c.position() : super.position();
    }
}
