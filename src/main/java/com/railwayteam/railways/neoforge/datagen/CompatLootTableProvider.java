package com.railwayteam.railways.neoforge.datagen;

import java.util.HashMap;
import java.util.Map;

/**
 * Maps compat track prefixes to their mod IDs.
 * Used to identify which mod a compatibility track belongs to.
 */
public class CompatLootTableProvider {
    
    private static final Map<String, String> COMPAT_MODS = new HashMap<>();
    
    static {
        COMPAT_MODS.put("biomesoplenty", "biomesoplenty");
    }
    
    /**
     * Checks if a track name belongs to a compat mod and returns the mod ID if so.
     */
    public static String getCompatModForTrackName(String trackName) {
        // Check each compat mod prefix
        for (String modPrefix : COMPAT_MODS.keySet()) {
            if (trackName.startsWith(modPrefix + "_")) {
                return COMPAT_MODS.get(modPrefix);
            }
        }
        return null;
    }
}
