package com.railwayteam.railways.registry.neoforge;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;

/**
 * Fabric implementation of CRCreativeModeTabsRegistrateDisplayItemsGeneratorImpl.
 * Uses the Fabric TabHolder wrappers instead of NeoForge DeferredHolder.
 */
public class CRCreativeModeTabsRegistrateDisplayItemsGeneratorImpl {

    public static boolean isInCreativeTab(RegistryEntry<?, ?> entry, ResourceKey<CreativeModeTab> tab) {
        CRCreativeModeTabsImpl.TabHolder<CreativeModeTab> holder = resolveHolder(tab);
        if (holder == null)
            return true;
        // TODO: CreateRegistrate.isInCreativeTab may not exist in Create Fly / Registrate-Refabricated.
        //       Verify and replace with equivalent Fabric API call for MC 26.1.1.
        return CreateRegistrate.isInCreativeTab(entry, holder);
    }

    private static CRCreativeModeTabsImpl.TabHolder<CreativeModeTab> resolveHolder(ResourceKey<CreativeModeTab> tab) {
        if (CRCreativeModeTabsImpl.MAIN_TAB_KEY.equals(tab))
            return CRCreativeModeTabsImpl.MAIN_TAB;
        if (CRCreativeModeTabsImpl.TRACKS_TAB_KEY.equals(tab))
            return CRCreativeModeTabsImpl.TRACKS_TAB;
        if (CRCreativeModeTabsImpl.PALETTES_TAB_KEY.equals(tab))
            return CRCreativeModeTabsImpl.PALETTES_TAB;
        return null;
    }
}
