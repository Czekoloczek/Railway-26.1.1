package com.railwayteam.railways.multiloader.neoforge;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.Nullable;

/**
 * Holds a reference to the running MinecraftServer for use in Fabric.
 * Initialized via ServerLifecycleEvents in the mod initializer.
 */
public class FabricServerHolder {

    @Nullable
    private static MinecraftServer server;

    /** Call this from the Fabric main entrypoint to register lifecycle hooks. */
    public static void register() {
        ServerLifecycleEvents.SERVER_STARTED.register(s -> server = s);
        ServerLifecycleEvents.SERVER_STOPPED.register(s -> server = null);
    }

    @Nullable
    public static MinecraftServer getServer() {
        return server;
    }
}
