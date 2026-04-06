package com.railwayteam.railways.config.fabric;

import com.railwayteam.railways.config.CRConfigs;

/**
 * Fabric-specific config registration.
 *
 * On NeoForge, config registration is handled by CRConfigsImpl (via ModLoadingContext).
 * On Fabric, we have two options:
 *   a) Use Forge Config API Port (if available for MC 26.1.1) – provides NeoForge config
 *      compatibility layer on Fabric, allowing CRConfigs to work unchanged.
 *   b) Use Cloth Config API – requires rewriting config spec creation.
 *
 * This stub calls CRConfigs.registerCommon() which creates the config specs.
 * The specs use the NeoForge shim ModConfigSpec which returns default values only.
 *
 * TODO: Integrate with Forge Config API Port or Cloth Config for persistent config on Fabric.
 * TODO: Check https://modrinth.com/mod/forge-config-api-port for a MC 26.1.1 compatible version.
 */
public class CRConfigsFabric {

    public static void register() {
        // Initialize config specs (will use default values since no real NeoForge backing)
        CRConfigs.registerCommon();
    }
}
