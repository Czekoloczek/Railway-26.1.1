package net.neoforged.neoforge.fluids.capability.templates;

import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

/**
 * Stub for NeoForge's FluidTank template implementation.
 * On Fabric, use Fabric Transfer API storage implementations instead.
 * TODO: Replace with Fabric Transfer API equivalent in fuel/tank content.
 */
public class FluidTank implements IFluidTank, IFluidHandler {

    protected FluidStack fluid = FluidStack.EMPTY;
    protected int capacity;

    public FluidTank(int capacity) {
        this.capacity = capacity;
    }

    @Override public FluidStack getFluid() { return fluid; }
    @Override public int getFluidAmount() { return fluid.getAmount(); }
    @Override public int getCapacity() { return capacity; }
    @Override public boolean isFluidValid(FluidStack stack) { return true; }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource.isEmpty()) return 0;
        int fillAmount = Math.min(capacity - fluid.getAmount(), resource.getAmount());
        if (action.isExecute()) {
            if (fluid.isEmpty()) {
                fluid = new FluidStack(resource.getFluid(), fillAmount);
            } else {
                fluid.setAmount(fluid.getAmount() + fillAmount);
            }
        }
        return fillAmount;
    }

    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource.isEmpty() || fluid.getFluid() != resource.getFluid()) return FluidStack.EMPTY;
        return drain(resource.getAmount(), action);
    }

    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (fluid.isEmpty()) return FluidStack.EMPTY;
        int drained = Math.min(fluid.getAmount(), maxDrain);
        FluidStack result = new FluidStack(fluid.getFluid(), drained);
        if (action.isExecute()) {
            fluid.setAmount(fluid.getAmount() - drained);
            if (fluid.getAmount() <= 0) fluid = FluidStack.EMPTY;
        }
        return result;
    }

    @Override public int getTanks() { return 1; }
    @Override public FluidStack getFluidInTank(int tank) { return fluid; }
    @Override public int getTankCapacity(int tank) { return capacity; }
    @Override public boolean isFluidValid(int tank, FluidStack stack) { return true; }

    public void setFluid(FluidStack stack) { this.fluid = stack; }
    public boolean isEmpty() { return fluid.isEmpty(); }
}
