package net.neoforged.neoforge.fluids.capability;

import net.neoforged.neoforge.fluids.FluidStack;

/**
 * Stub for NeoForge's IFluidHandler capability interface.
 * On Fabric, fluid handling uses the Fabric Transfer API.
 * TODO: Replace usages with Fabric Transfer API (FluidStorage / FluidVariant).
 */
public interface IFluidHandler {

    enum FluidAction {
        EXECUTE,
        SIMULATE;

        public boolean isSimulate() {
            return this == SIMULATE;
        }

        public boolean isExecute() {
            return this == EXECUTE;
        }
    }

    int getTanks();
    FluidStack getFluidInTank(int tank);
    int getTankCapacity(int tank);
    boolean isFluidValid(int tank, FluidStack stack);
    int fill(FluidStack resource, FluidAction action);
    FluidStack drain(FluidStack resource, FluidAction action);
    FluidStack drain(int maxDrain, FluidAction action);
}
