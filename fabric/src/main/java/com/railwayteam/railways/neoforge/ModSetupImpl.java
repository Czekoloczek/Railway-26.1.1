package com.railwayteam.railways.neoforge;

import com.railwayteam.railways.registry.neoforge.CRCreativeModeTabsImpl;

/**
 * Fabric-side replacement for NeoForge's ModSetupImpl.
 * Delegates creative tab setup to the Fabric CRCreativeModeTabsImpl.
 */
public class ModSetupImpl {

    public static void useBaseTab() {
        CRCreativeModeTabsImpl.useBaseTab();
    }

    public static void useTracksTab() {
        CRCreativeModeTabsImpl.useTracksTab();
    }

    public static void usePalettesTab() {
        CRCreativeModeTabsImpl.usePalettesTab();
    }
}
