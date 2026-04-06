package com.railwayteam.railways.multiloader.neoforge;

import com.mojang.brigadier.arguments.ArgumentType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.AbstractFurnaceBlockEntity;

/**
 * Fabric implementation of PlatformAbstractionHelperImpl.
 */
public class PlatformAbstractionHelperImpl {

    /**
     * Returns the fuel burn time for the given item in ticks.
     *
     * Fabric's {@code FuelRegistryEvents} API changed significantly between
     * 1.20.x and 1.21.x. As a cross-version safe fallback we read directly from
     * {@link AbstractFurnaceBlockEntity#getFuel()}, which returns the same data
     * that the vanilla furnace uses and includes any values registered via the
     * Fabric fuel extension events.
     */
    public static int getBurnTime(Item item) {
        return AbstractFurnaceBlockEntity.getFuel().getOrDefault(item, 0);
    }

    /**
     * Returns a Brigadier {@link ArgumentType} for enum values.
     *
     * NeoForge ships {@code net.neoforged.neoforge.common.util.EnumArgument};
     * the Porting Lib (used on 1.20.1 Fabric) shipped
     * {@code io.github.fabricators_of_create.porting_lib.command.EnumArgument}.
     * For MC 26.1.1 + Create Fly neither is confirmed available. This method
     * throws until a compatible enum argument type is identified.
     */
    public static <T extends Enum<T>> ArgumentType<T> enumArgument(Class<T> enumClass) {
        throw new UnsupportedOperationException(
                "enumArgument not yet implemented for Fabric 26.1.1 – "
                + "provide a compatible ArgumentType<" + enumClass.getSimpleName() + ">");
    }
}
