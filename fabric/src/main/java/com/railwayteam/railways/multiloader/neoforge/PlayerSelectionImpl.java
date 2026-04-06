package com.railwayteam.railways.multiloader.neoforge;

import com.railwayteam.railways.multiloader.PlayerSelection;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;

/**
 * Fabric implementation of PlayerSelectionImpl using Fabric's PlayerLookup API.
 *
 * S2C packets are sent by constructing a {@link ClientboundCustomPayloadPacket}
 * from our {@link CustomPayloadWrapper} shim and sending it via each player's
 * connection. This mirrors what the NeoForge version does with
 * {@code connection.send(packet)}.
 */
public class PlayerSelectionImpl extends PlayerSelection {

    final Collection<ServerPlayer> players;

    private PlayerSelectionImpl(Collection<ServerPlayer> players) {
        this.players = players;
    }

    @Override
    public void accept(ResourceLocation id, FriendlyByteBuf buffer) {
        // Build the vanilla packet once and deliver it to every player.
        ClientboundCustomPayloadPacket packet =
                new ClientboundCustomPayloadPacket(CustomPayloadWrapper.create(id, buffer));
        for (ServerPlayer player : players) {
            player.connection.send(packet);
        }
    }

    // --- Factory methods ----------------------------------------------------

    public static PlayerSelection all() {
        MinecraftServer server = FabricServerHolder.getServer();
        if (server == null) return new PlayerSelectionImpl(Collections.emptyList());
        return new PlayerSelectionImpl(PlayerLookup.all(server));
    }

    public static PlayerSelection allWith(Predicate<ServerPlayer> condition) {
        MinecraftServer server = FabricServerHolder.getServer();
        if (server == null) return new PlayerSelectionImpl(Collections.emptyList());
        return new PlayerSelectionImpl(
                PlayerLookup.all(server).stream().filter(condition).toList()
        );
    }

    public static PlayerSelection of(ServerPlayer player) {
        return new PlayerSelectionImpl(Collections.singleton(player));
    }

    public static PlayerSelection tracking(Entity entity) {
        return new PlayerSelectionImpl(PlayerLookup.tracking(entity));
    }

    public static PlayerSelection trackingWith(Entity entity, Predicate<ServerPlayer> condition) {
        return new PlayerSelectionImpl(
                PlayerLookup.tracking(entity).stream().filter(condition).toList()
        );
    }

    public static PlayerSelection tracking(BlockEntity be) {
        return new PlayerSelectionImpl(PlayerLookup.tracking(be));
    }

    public static PlayerSelection tracking(ServerLevel level, BlockPos pos) {
        return new PlayerSelectionImpl(PlayerLookup.tracking(level, pos));
    }

    public static PlayerSelection trackingAndSelf(ServerPlayer player) {
        ArrayList<ServerPlayer> result = new ArrayList<>(PlayerLookup.tracking(player));
        result.add(player);
        return new PlayerSelectionImpl(result);
    }
}
