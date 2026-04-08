package net.neoforged.neoforge.common.conditions;

/**
 * Stub for NeoForge's ModLoadedCondition used in recipe data generation.
 * TODO: Replace with Fabric-compatible condition when porting data gen.
 */
public class ModLoadedCondition {
    private final String modId;

    public ModLoadedCondition(String modId) {
        this.modId = modId;
    }

    public String getModId() {
        return modId;
    }
}
