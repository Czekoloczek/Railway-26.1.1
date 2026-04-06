package com.railwayteam.railways.util.neoforge;

import com.railwayteam.railways.content.fuel.tank.FuelTankBlockEntity;
import net.neoforged.neoforge.fluids.FluidStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;

/**
 * Fabric implementation of FluidUtilsImpl.
 * Uses the NeoForge FluidStack shim for compilation; on Fabric the actual
 * fluid system will use the Fabric Transfer API.
 * TODO: Replace FluidStack stub with Fabric Transfer API equivalents.
 */
public class FluidUtilsImpl {

    public static boolean canUseAsFuelStorage(BlockEntity be) {
        if (be instanceof FuelTankBlockEntity fuelTank)
            return fuelTank.isController();
        return false;
    }

    public static Fluid getFluid(Object o) {
        if (!(o instanceof FluidStack fluidStack))
            throw new IllegalArgumentException(
                    "FluidUtils#getFluid expected a FluidStack but got " + o.getClass().getName());
        return fluidStack.getFluid();
    }
}
