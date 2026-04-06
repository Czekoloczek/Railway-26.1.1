package com.railwayteam.railways.multiloader.neoforge;

import com.railwayteam.railways.multiloader.C2SPacket;
import com.railwayteam.railways.multiloader.PacketSet;
import com.railwayteam.railways.multiloader.PlayerSelection;
import com.railwayteam.railways.multiloader.S2CPacket;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.List;
import java.util.function.Function;

/**
 * Fabric implementation of PacketSetImpl.
 * Uses Fabric Networking API v1 for packet sending/receiving.
 * TODO: Update to Fabric Networking API v2 (CustomPayload) when migrating to MC 26.1.1.
 */
public class PacketSetImpl extends PacketSet {

    protected PacketSetImpl(String id, int version,
                            List<Function<FriendlyByteBuf, S2CPacket>> s2cPackets,
                            Object2IntMap<Class<? extends S2CPacket>> s2cTypes,
                            List<Function<FriendlyByteBuf, C2SPacket>> c2sPackets,
                            Object2IntMap<Class<? extends C2SPacket>> c2sTypes) {
        super(id, version, s2cPackets, s2cTypes, c2sPackets, c2sTypes);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void registerS2CListener() {
        ClientPlayNetworking.registerGlobalReceiver(s2cPacket, (mc, listener, buf, sender) ->
                handleS2CPacket(mc, buf));
    }

    @Override
    public void registerC2SListener() {
        ServerPlayNetworking.registerGlobalReceiver(c2sPacket, (server, player, listener, buf, sender) ->
                handleC2SPacket(player, buf));
    }

    @Override
    @Environment(EnvType.CLIENT)
    protected void doSendC2S(FriendlyByteBuf buf) {
        ClientPlayNetworking.send(c2sPacket, buf);
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void send(SimplePacketBase packet) {
        // TODO: use Create Fly's packet channel when available
        throw new UnsupportedOperationException("Create Fly packet channel not yet wired for Fabric");
    }

    @Override
    public void sendTo(ServerPlayer player, SimplePacketBase packet) {
        // TODO: use Create Fly's packet channel when available
        throw new UnsupportedOperationException("Create Fly packet channel not yet wired for Fabric");
    }

    @Override
    public void sendTo(PlayerSelection selection, SimplePacketBase packet) {
        // TODO: use Create Fly's packet channel when available
        throw new UnsupportedOperationException("Create Fly packet channel not yet wired for Fabric");
    }

    @Internal
    public static PacketSet create(String id, int version,
                                   List<Function<FriendlyByteBuf, S2CPacket>> s2cPackets,
                                   Object2IntMap<Class<? extends S2CPacket>> s2cTypes,
                                   List<Function<FriendlyByteBuf, C2SPacket>> c2sPackets,
                                   Object2IntMap<Class<? extends C2SPacket>> c2sTypes) {
        return new PacketSetImpl(id, version, s2cPackets, s2cTypes, c2sPackets, c2sTypes);
    }
}
