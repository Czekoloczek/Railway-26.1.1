package com.railwayteam.railways.registry.neoforge;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

/**
 * Fabric implementation of CRCreativeModeTabsRegistrateDisplayItemsGeneratorImpl.
 *
 * Attempts to call {@link CreateRegistrate#isInCreativeTab(RegistryEntry, ResourceKey)}
 * if that overload exists. Falls back to {@code true} (include in all tabs) if
 * Registrate-Refabricated does not expose the overload at runtime.
 */
public class CRCreativeModeTabsRegistrateDisplayItemsGeneratorImpl {

    public static boolean isInCreativeTab(RegistryEntry<?, ?> entry, ResourceKey<CreativeModeTab> tab) {
        if (tab == null) return true;
        try {
            return CreateRegistrate.isInCreativeTab(entry, tab);
        } catch (NoSuchMethodError | AbstractMethodError e) {
            // API mismatch at runtime – include the entry in all tabs as fallback.
            return true;
        }
    }
}
