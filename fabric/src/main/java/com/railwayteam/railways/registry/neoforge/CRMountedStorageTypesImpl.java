package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.content.fuel.tank.FuelTankMountedStorageType;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.tterrag.registrate.util.entry.RegistryEntry;

/**
 * Fabric implementation of CRMountedStorageTypesImpl.
 * Uses Registrate-Refabricated to register the MountedFluidStorageType.
 * TODO: Verify that Create Fly exposes mountedFluidStorage() on Fabric for MC 26.1.1.
 */
public class CRMountedStorageTypesImpl {

    public static RegistryEntry<MountedFluidStorageType<?>, FuelTankMountedStorageType> FUEL_TANK =
            Railways.registrate()
                    .mountedFluidStorage("fuel_tank", FuelTankMountedStorageType::new)
                    .register();

    public static void init() {
        // Registration triggered by Railways.registrate().register() in entrypoint.
    }
}
