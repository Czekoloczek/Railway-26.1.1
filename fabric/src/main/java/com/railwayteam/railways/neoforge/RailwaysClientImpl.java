package com.railwayteam.railways.neoforge;

import com.mojang.brigadier.CommandDispatcher;
import com.railwayteam.railways.Railways;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.commands.SharedSuggestionProvider;
import net.simibubi.create.foundation.utility.Components;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Fabric-side replacement for NeoForge's RailwaysClientImpl.
 * Common client code calls static methods on this class.
 */
public class RailwaysClientImpl {

    /** Client initialisation – called from Fabric ClientModInitializer. */
    public static void init() {
        // TODO: register ConductorCapItemRenderer equivalent for Fabric
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static void registerClientCommands(Consumer<CommandDispatcher<SharedSuggestionProvider>> consumer) {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            CommandDispatcher<SharedSuggestionProvider> casted = (CommandDispatcher) dispatcher;
            consumer.accept(casted);
        });
    }

    public static void registerModelLayer(ModelLayerLocation layer, Supplier<LayerDefinition> definition) {
        EntityModelLayerRegistry.registerModelLayer(layer, definition::get);
    }

    public static void registerBuiltinPack(String id, String name) {
        ModContainer mod = FabricLoader.getInstance()
                .getModContainer(Railways.MOD_ID)
                .orElseThrow();
        ResourceManagerHelper.registerBuiltinResourcePack(
                Railways.asResource(id), mod,
                Components.literal(name),
                ResourcePackActivationType.NORMAL);
    }
}
