package net.neoforged.neoforge.fluids;

/**
 * Stub for NeoForge's IFluidTank interface.
 * TODO: Replace with Fabric Transfer API storage when porting fuel/tank content.
 */
public interface IFluidTank {
    FluidStack getFluid();
    int getFluidAmount();
    int getCapacity();
    boolean isFluidValid(FluidStack stack);
    int fill(FluidStack resource, IFluidHandler.FluidAction action);
    FluidStack drain(FluidStack resource, IFluidHandler.FluidAction action);
    FluidStack drain(int maxDrain, IFluidHandler.FluidAction action);
}
