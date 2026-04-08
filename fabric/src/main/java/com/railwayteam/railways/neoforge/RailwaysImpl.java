package com.railwayteam.railways.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import com.railwayteam.railways.Railways;
import com.railwayteam.railways.registry.neoforge.CRExtraRegistrationImpl;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.CommandSourceStack;

import java.util.function.BiConsumer;

/**
 * Fabric-side replacement for NeoForge's RailwaysImpl.
 * Common code calls static methods on this class at startup.
 */
public class RailwaysImpl {

    /** Called by Railways.init() – triggers Registrate deferred registrations. */
    public static void finalizeRegistrate() {
        Railways.registrate().register();
    }

    /** Register server commands via Fabric's CommandRegistrationCallback. */
    public static void registerCommands(BiConsumer<CommandDispatcher<CommandSourceStack>, Boolean> consumer) {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                consumer.accept(dispatcher, environment.includeDedicated));
    }

    /**
     * Platform-specific registration: wires Create-Registrate callbacks
     * (vent copycat, signal block source) for Fabric.
     */
    public static void platformBasedRegistration() {
        CRExtraRegistrationImpl.platformSpecificRegistration();
    }
}
