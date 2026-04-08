package com.railwayteam.railways.registry.neoforge;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * Fabric implementation of CRKeysImpl.
 * Uses Fabric's KeyBindingHelper to register key mappings.
 */
public class CRKeysImpl {

    private static final List<KeyMapping> KEYBINDS = new ArrayList<>();

    public static void registerKeyBinding(KeyMapping keyMapping) {
        KEYBINDS.add(keyMapping);
    }

    /**
     * Register all accumulated key bindings with Fabric.
     * Call from the ClientModInitializer.
     */
    public static void registerAll() {
        for (KeyMapping keyMapping : KEYBINDS) {
            KeyBindingHelper.registerKeyBinding(keyMapping);
        }
    }

    /**
     * Compatibility stub – NeoForge calls this with an event; Fabric handles registration
     * differently (via registerAll() above). This no-ops on Fabric.
     */
    public static void onRegisterKeyMappings(Object event) {
        // No-op – Fabric key bindings are registered via KeyBindingHelper, not via events.
    }

    public static int getBoundCode(KeyMapping keyMapping) {
        return keyMapping.getKey().getValue();
    }
}
