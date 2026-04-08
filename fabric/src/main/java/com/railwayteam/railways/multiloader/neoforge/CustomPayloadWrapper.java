package com.railwayteam.railways.multiloader.neoforge;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

/**
 * Fabric stub for CustomPayloadWrapper.
 * On NeoForge this wraps legacy packet data into the 1.21+ CustomPacketPayload format.
 * On Fabric, packet registration uses Fabric Networking API directly.
 * This class is included for compilation compatibility; it is not used at runtime.
 */
public record CustomPayloadWrapper(Type<CustomPayloadWrapper> type, FriendlyByteBuf data)
        implements CustomPacketPayload {

    public static CustomPayloadWrapper create(ResourceLocation id, FriendlyByteBuf data) {
        return new CustomPayloadWrapper(new Type<>(id), data);
    }

    public static Type<CustomPayloadWrapper> type(ResourceLocation id) {
        return new Type<>(id);
    }

    public ResourceLocation id() {
        return type.id();
    }

    public static StreamCodec<FriendlyByteBuf, CustomPayloadWrapper> codec(ResourceLocation id) {
        return StreamCodec.of(
                (buf, payload) -> buf.writeBytes(payload.data, payload.data.readerIndex(), payload.data.readableBytes()),
                (buf) -> {
                    int readable = buf.readableBytes();
                    FriendlyByteBuf data = new FriendlyByteBuf(buf.readRetainedSlice(readable));
                    return new CustomPayloadWrapper(new Type<>(id), data);
                }
        );
    }
}
