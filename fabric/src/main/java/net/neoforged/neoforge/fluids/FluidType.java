package net.neoforged.neoforge.fluids;

import net.minecraft.resources.ResourceLocation;

/**
 * Stub for NeoForge's FluidType.
 * On Fabric, fluid properties are managed differently (no FluidType concept).
 * TODO: Remove or replace FluidType usages when porting fluid content.
 */
public class FluidType {
    public static final int BUCKET_VOLUME = 1000;

    private final String descriptionId;

    public FluidType(ResourceLocation id) {
        this.descriptionId = "block." + id.getNamespace() + "." + id.getPath();
    }

    public String getDescriptionId() {
        return descriptionId;
    }
}
