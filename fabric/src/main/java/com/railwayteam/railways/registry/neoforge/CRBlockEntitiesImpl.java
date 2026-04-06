package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.content.fuel.psi.PortableFuelInterfaceBlockEntity;
import com.railwayteam.railways.content.fuel.tank.FuelTankBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

/**
 * Fabric implementation of CRBlockEntitiesImpl.
 *
 * Uses Registrate-Refabricated (same API as NeoForge Registrate) for block entity
 * registration. Requires registrate-refabricated on the Fabric classpath.
 *
 * TODO: If registrate-refabricated is not available for MC 26.1.1, rewrite
 *       using vanilla Fabric registry APIs (Registry.register + Fabric BE API).
 */
public class CRBlockEntitiesImpl {

    public static final BlockEntityEntry<FuelTankBlockEntity> FUEL_TANK =
            Railways.registrate()
                    .blockEntity("fuel_tank", FuelTankBlockEntity::new)
                    .validBlocks(CRBlocksImpl.FUEL_TANK)
                    // TODO: register renderer via Fabric API client entrypoint
                    .register();

    public static final BlockEntityEntry<PortableFuelInterfaceBlockEntity> PORTABLE_FUEL_INTERFACE =
            Railways.registrate()
                    .blockEntity("portable_fuel_interface", PortableFuelInterfaceBlockEntity::new)
                    .validBlocks(CRBlocksImpl.PORTABLE_FUEL_INTERFACE)
                    // TODO: register renderer via Fabric API client entrypoint
                    .register();

    public static void init() {
        // Registration triggered by Railways.registrate().register() in entrypoint.
    }
}
