package com.railwayteam.railways.fabric;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.config.fabric.CRConfigsFabric;
import com.railwayteam.railways.multiloader.neoforge.FabricServerHolder;
import com.railwayteam.railways.neoforge.RailwaysImpl;
import com.railwayteam.railways.registry.CRPackets;
import com.railwayteam.railways.registry.neoforge.CRCreativeModeTabsImpl;
import com.railwayteam.railways.registry.neoforge.CREntityAttributesImpl;
import com.railwayteam.railways.util.neoforge.RegistrationListeningImpl;
import net.fabricmc.api.ModInitializer;

/**
 * Fabric main entry point for Steam 'n' Rails targeting MC 26.1.1 + Create Fly.
 */
public class RailwaysFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        // 1. Config first (specs are created here; values are defaults until a config file backend is wired)
        CRConfigsFabric.register();

        // 2. Server lifecycle hooks (gives PlayerSelectionImpl access to the server instance)
        FabricServerHolder.register();

        // 3. Common mod init – runs all registry/block/item registration through Registrate
        Railways.init();

        // 4. Creative mode tabs (must follow Railways.init() so block entries are available)
        CRCreativeModeTabsImpl.register();

        // 5. Entity attributes
        CREntityAttributesImpl.registerAttributes();

        // 6. Platform-specific registrations (Create-Registrate callbacks, etc.)
        RailwaysImpl.platformBasedRegistration();

        // 7. Finalize Registrate (triggers all deferred registrations)
        RailwaysImpl.finalizeRegistrate();

        // 8. Network listeners (C2S)
        CRPackets.PACKETS.registerC2SListener();

        // 9. Post-registration callbacks (RegistrationListening listeners)
        RegistrationListeningImpl.fireListeners();

        Railways.LOGGER.info("Steam 'n' Rails (Fabric) initialized for MC 26.1.1");
    }
}
