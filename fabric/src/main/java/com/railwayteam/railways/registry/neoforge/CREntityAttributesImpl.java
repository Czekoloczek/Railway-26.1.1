package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.content.conductor.ConductorEntity;
import com.railwayteam.railways.registry.CREntities;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

/**
 * Fabric implementation of CREntityAttributesImpl.
 * Uses Fabric's FabricDefaultAttributeRegistry instead of NeoForge's event.
 */
public class CREntityAttributesImpl {

    public static void registerAttributes() {
        FabricDefaultAttributeRegistry.register(
                CREntities.CONDUCTOR.get(),
                ConductorEntity.createAttributes());
    }
}
