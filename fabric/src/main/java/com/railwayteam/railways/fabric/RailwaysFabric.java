package com.railwayteam.railways.fabric;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.ModSetup;
import com.railwayteam.railways.config.fabric.CRConfigsFabric;
import com.railwayteam.railways.multiloader.neoforge.FabricServerHolder;
import com.railwayteam.railways.neoforge.RailwaysImpl;
import com.railwayteam.railways.registry.neoforge.CRCreativeModeTabsImpl;
import com.railwayteam.railways.registry.neoforge.CREntityAttributesImpl;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric main entry point for Steam 'n' Rails targeting MC 26.1.1 + Create Fly.
 */
public class RailwaysFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // Initialize config before anything else
        CRConfigsFabric.register();

        // Register server lifecycle hooks (for PlayerSelectionImpl)
        FabricServerHolder.register();

        // Initialize common mod (runs all registry registration through Registrate)
        Railways.init();

        // Register creative mode tabs
        CRCreativeModeTabsImpl.register();

        // Register entity attributes
        CREntityAttributesImpl.registerAttributes();

        // Finalize Registrate (triggers all deferred registrations)
        RailwaysImpl.finalizeRegistrate();

        Railways.LOGGER.info("Steam 'n' Rails (Fabric) initialized for MC 26.1.1");
    }
}
