package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.content.fuel.psi.PortableFuelInterfaceBlockEntity;
import com.railwayteam.railways.content.fuel.tank.FuelTankBlockEntity;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

/**
 * Fabric implementation of CRBlockEntitiesImpl.
 *
 * Uses Registrate (provided transitively by Create Fly) for block entity registration.
 * Renderers are registered separately in the Fabric client entrypoint.
 */
public class CRBlockEntitiesImpl {

    public static final BlockEntityEntry<FuelTankBlockEntity> FUEL_TANK =
            Railways.registrate()
                    .blockEntity("fuel_tank", FuelTankBlockEntity::new)
                    .validBlocks(CRBlocksImpl.FUEL_TANK)
                    .register();

    public static final BlockEntityEntry<PortableFuelInterfaceBlockEntity> PORTABLE_FUEL_INTERFACE =
            Railways.registrate()
                    .blockEntity("portable_fuel_interface", PortableFuelInterfaceBlockEntity::new)
                    .validBlocks(CRBlocksImpl.PORTABLE_FUEL_INTERFACE)
                    .register();

    public static void init() {
        // Registration triggered by Railways.registrate().register() in entrypoint.
    }
}
