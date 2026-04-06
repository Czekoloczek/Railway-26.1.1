package com.railwayteam.railways.util.neoforge;

import com.railwayteam.railways.Railways;
import com.simibubi.create.content.trains.entity.Train;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

/**
 * Fabric implementation of UtilsImpl.
 * Uses FabricLoader for config/mods directories instead of FMLPaths.
 */
public class UtilsImpl {

    public static Path configDir() {
        return FabricLoader.getInstance().getConfigDir();
    }

    public static boolean isDevEnv() {
        return FabricLoader.getInstance().isDevelopmentEnvironment();
    }

    public static void sendCreatePacketToServer(Object packet) {
        Railways.LOGGER.warn("sendCreatePacketToServer not yet implemented for Fabric 26.1.1");
    }

    public static void sendHonkPacket(Train train, boolean isHonk) {
        try {
            java.util.UUID id = train.id;
            com.railwayteam.railways.util.packet.HonkTrainPacket packet =
                    new com.railwayteam.railways.util.packet.HonkTrainPacket(id, isHonk);
            com.railwayteam.railways.registry.CRPackets.PACKETS.send(packet);
        } catch (Throwable t) {
            Railways.LOGGER.warn("sendHonkPacket: failed to send honk packet: {}", t.toString());
        }
    }

    public static Path modsDir() {
        return FabricLoader.getInstance().getGameDir().resolve("mods");
    }
}
