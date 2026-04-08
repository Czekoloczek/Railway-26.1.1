package com.railwayteam.railways.util.neoforge;

import com.railwayteam.railways.content.fuel.tank.FuelTankBlockEntity;
import com.railwayteam.railways.registry.neoforge.CRBlocksImpl;
import com.railwayteam.railways.registry.neoforge.CRMountedStorageTypesImpl;
import com.simibubi.create.api.contraption.storage.fluid.MountedFluidStorageType;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

/**
 * Fabric implementation of AbstractionUtilsImpl.
 * Delegates to Fabric registry impl classes.
 */
public class AbstractionUtilsImpl {

    public static BlockEntry<?> getFluidTankBlockEntry() {
        return CRBlocksImpl.FUEL_TANK;
    }

    public static BlockEntry<?> getPortableFuelInterfaceBlockEntry() {
        return CRBlocksImpl.PORTABLE_FUEL_INTERFACE;
    }

    public static boolean portableFuelInterfaceBlockHasState(BlockState state) {
        return CRBlocksImpl.PORTABLE_FUEL_INTERFACE.has(state);
    }

    public static boolean isInstanceOfFuelTankBlockEntity(BlockEntity blockEntity) {
        return blockEntity instanceof FuelTankBlockEntity;
    }

    public static boolean isInstanceOfFuelTankMountedStorageType(MountedFluidStorageType<?> type) {
        return CRMountedStorageTypesImpl.FUEL_TANK.is(type);
    }
}
