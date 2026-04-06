package com.railwayteam.railways.fabric;

import com.railwayteam.railways.RailwaysClient;
import com.railwayteam.railways.neoforge.RailwaysClientImpl;
import com.railwayteam.railways.registry.CRKeys;
import com.railwayteam.railways.registry.neoforge.CRKeysImpl;
import net.fabricmc.api.ClientModInitializer;

/**
 * Fabric client entry point for Steam 'n' Rails targeting MC 26.1.1 + Create Fly.
 */
public class RailwaysFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Initialize common client-side systems
        RailwaysClient.init();

        // Initialize Fabric-specific client systems
        RailwaysClientImpl.init();

        // Register key bindings collected during init
        CRKeysImpl.registerAll();
    }
}
