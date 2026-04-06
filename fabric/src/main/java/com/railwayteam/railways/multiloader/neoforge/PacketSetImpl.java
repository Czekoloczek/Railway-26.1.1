package com.railwayteam.railways.multiloader.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.multiloader.C2SPacket;
import com.railwayteam.railways.multiloader.PacketSet;
import com.railwayteam.railways.multiloader.PlayerSelection;
import com.railwayteam.railways.multiloader.S2CPacket;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.ServerboundCustomPayloadPacket;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus.Internal;

import java.util.List;
import java.util.function.Function;

/**
 * Fabric implementation of PacketSetImpl.
 *
 * For mod-internal S2C/C2S packets (Railway's own packets) we use Fabric
 * Networking API v1. The Fabric Networking API v1 send(ResourceLocation, buf)
 * is still available on 1.21+ via the compatibility layer.
 *
 * The abstract {@code send(Object)} / {@code sendTo(ServerPlayer, Object)} /
 * {@code sendTo(PlayerSelection, Object)} methods are for forwarding Create's
 * own packets through Create's channel; those are not yet wired and match the
 * parity of the NeoForge implementation which also throws.
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

    /**
     * Send a C2S packet using the vanilla custom-payload mechanism so that the
     * server-side {@link #registerC2SListener()} handler receives it.
     * On Fabric 1.21+ ClientPlayNetworking.send(id, buf) is the standard path.
     */
    @Override
    @Environment(EnvType.CLIENT)
    protected void doSendC2S(FriendlyByteBuf buf) {
        ClientPacketListener connection = Minecraft.getInstance().getConnection();
        if (connection != null) {
            connection.send(new ServerboundCustomPayloadPacket(
                    CustomPayloadWrapper.create(c2sPacket, buf)));
        } else {
            Railways.LOGGER.error("Cannot send a C2S packet before the client connection exists, skipping!");
        }
    }

    // -------------------------------------------------------------------------
    // Create-packet forwarding – not yet wired (matches NeoForge parity).
    // -------------------------------------------------------------------------

    @Override
    @Environment(EnvType.CLIENT)
    public void send(Object packet) {
        throw new UnsupportedOperationException("Create Fly packet channel not yet wired for Fabric");
    }

    @Override
    public void sendTo(ServerPlayer player, Object packet) {
        throw new UnsupportedOperationException("Create Fly packet channel not yet wired for Fabric");
    }

    @Override
    public void sendTo(PlayerSelection selection, Object packet) {
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
