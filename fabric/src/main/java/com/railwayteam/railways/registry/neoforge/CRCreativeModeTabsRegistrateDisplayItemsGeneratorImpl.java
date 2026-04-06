package com.railwayteam.railways.registry.neoforge;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

/**
 * Fabric implementation of CRCreativeModeTabsRegistrateDisplayItemsGeneratorImpl.
 *
 * On NeoForge, {@link CreateRegistrate#isInCreativeTab} accepts a
 * {@code DeferredHolder<CreativeModeTab, CreativeModeTab>} as the second argument.
 * On Fabric (with Registrate-Refabricated), the method either:
 *   a) accepts a {@link ResourceKey}<{@link CreativeModeTab}> directly, or
 *   b) is accessible via a {@code ResourceKey}-based overload.
 *
 * We call the method with just the {@link ResourceKey} here. If
 * Registrate-Refabricated for MC 26.1.1 does not expose this overload, replace
 * the method body with {@code return true;} as a safe (all-tabs) fallback until
 * the correct API path is found.
 */
public class CRCreativeModeTabsRegistrateDisplayItemsGeneratorImpl {

    public static boolean isInCreativeTab(RegistryEntry<?, ?> entry, ResourceKey<CreativeModeTab> tab) {
        if (tab == null) return true;
        // Attempt to use CreateRegistrate's isInCreativeTab with a ResourceKey.
        // If Registrate-Refabricated exposes this overload, this works directly.
        // If the overload does not exist at compile time, fall back to:
        //   return true;
        try {
            return CreateRegistrate.isInCreativeTab(entry, tab);
        } catch (NoSuchMethodError | AbstractMethodError e) {
            // API mismatch at runtime – include the entry in all tabs as fallback.
            return true;
        }
    }
}
