package com.railwayteam.railways.multiloader.neoforge;

import com.mojang.brigadier.arguments.ArgumentType;
import net.fabricmc.fabric.api.registry.FuelRegistryEvents;
import net.minecraft.world.item.Item;

/**
 * Fabric implementation of PlatformAbstractionHelperImpl.
 * TODO: Implement enumArgument using a Fabric-compatible enum argument type for MC 26.1.1.
 */
public class PlatformAbstractionHelperImpl {

    public static int getBurnTime(Item item) {
        // TODO: Use Fabric Fuel Registry Events API for MC 26.1.1.
        // FuelRegistryEvents was changed in recent Fabric API versions.
        return 0;
    }

    public static <T extends Enum<T>> ArgumentType<T> enumArgument(Class<T> enumClass) {
        // TODO: Provide a Fabric-compatible enum argument type.
        // On 1.20.1 this used io.github.fabricators_of_create.porting_lib.command.EnumArgument.
        // For MC 26.1.1, check Create Fly or Fabric API for an equivalent.
        throw new UnsupportedOperationException(
                "enumArgument not yet implemented for Fabric 26.1.1 – TODO");
    }
}
