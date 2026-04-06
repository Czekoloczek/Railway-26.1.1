package com.railwayteam.railways.fabric;

import com.railwayteam.railways.RailwaysClient;
import com.railwayteam.railways.neoforge.RailwaysClientImpl;
import com.railwayteam.railways.registry.CRKeys;
import com.railwayteam.railways.registry.CRPackets;
import com.railwayteam.railways.registry.neoforge.CRKeysImpl;
import com.railwayteam.railways.registry.neoforge.CRParticleTypesParticleEntryImpl;
import net.fabricmc.api.ClientModInitializer;

/**
 * Fabric client entry point for Steam 'n' Rails targeting MC 26.1.1 + Create Fly.
 */
public class RailwaysFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Common client-side init (registers S2C listener, keybinds, etc.)
        RailwaysClient.init();

        // Fabric-specific client systems (model layers, built-in packs, etc.)
        RailwaysClientImpl.init();

        // Register key bindings collected during init with Fabric's keybinding API
        CRKeysImpl.registerAll();

        // Register particle providers (must be called client-side after particle types are registered)
        CRParticleTypesParticleEntryImpl.registerFactories();
    }
}
