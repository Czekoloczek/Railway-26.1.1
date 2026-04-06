package net.neoforged.neoforge.fluids;

import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;

/**
 * Stub for NeoForge's FluidStack.
 * On Fabric, fluid amounts are represented by FluidVariant + long amount
 * via the Fabric Transfer API (net.fabricmc.fabric.api.transfer.v1.fluid.*).
 * TODO: Replace usages in fuel/tank code with Fabric Transfer API equivalents.
 */
public class FluidStack {

    public static final FluidStack EMPTY = new FluidStack(Fluids.EMPTY, 0);

    private final Fluid fluid;
    private int amount;

    public FluidStack(Fluid fluid, int amount) {
        this.fluid = fluid;
        this.amount = amount;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public boolean isEmpty() {
        return fluid == Fluids.EMPTY || amount <= 0;
    }

    public FluidStack copy() {
        return new FluidStack(fluid, amount);
    }

    public static FluidStack copy(FluidStack stack) {
        return stack == null ? EMPTY : stack.copy();
    }
}
