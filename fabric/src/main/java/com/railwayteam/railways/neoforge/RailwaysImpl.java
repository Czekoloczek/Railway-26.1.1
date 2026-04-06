package com.railwayteam.railways.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import com.railwayteam.railways.Railways;
import com.railwayteam.railways.config.fabric.CRConfigsFabric;
import com.railwayteam.railways.multiloader.neoforge.FabricServerHolder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.BiConsumer;

/**
 * Fabric-side replacement for NeoForge's RailwaysImpl.
 * Common code calls static methods on this class at startup.
 */
public class RailwaysImpl {

    /** Called by Railways.init() – nothing extra needed on Fabric (entrypoint handles it). */
    public static void finalizeRegistrate() {
        Railways.registrate().register();
    }

    /** Register server commands via Fabric's CommandRegistrationCallback. */
    public static void registerCommands(BiConsumer<CommandDispatcher<CommandSourceStack>, Boolean> consumer) {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                consumer.accept(dispatcher, environment.includeDedicated));
    }

    /**
     * Called from ModSetup to handle platform-specific registration.
     * TODO: Port NeoForge capability registration and other platform-specific
     *       registration to Fabric equivalents.
     */
    public static void platformBasedRegistration() {
        // Fabric equivalent registrations go here.
        // See: neoforge/RailwaysImpl.java#platformBasedRegistration for the NeoForge version.
    }
}
