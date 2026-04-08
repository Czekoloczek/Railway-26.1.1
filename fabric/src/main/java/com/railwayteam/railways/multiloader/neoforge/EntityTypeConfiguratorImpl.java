package com.railwayteam.railways.multiloader.neoforge;

import com.railwayteam.railways.multiloader.EntityTypeConfigurator;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;

/**
 * Fabric implementation of EntityTypeConfiguratorImpl.
 * On Fabric 26.1.1, entity type builders use vanilla's EntityType.Builder.
 * TODO: Verify EntityType.Builder API surface for MC 26.1.1.
 */
public class EntityTypeConfiguratorImpl extends EntityTypeConfigurator {

    private final EntityType.Builder<?> builder;
    private float width = 0.6f;
    private float height = 1.8f;
    private boolean fireImmune = false;

    protected EntityTypeConfiguratorImpl(EntityType.Builder<?> builder) {
        this.builder = builder;
    }

    public static EntityTypeConfigurator of(Object builder) {
        if (builder instanceof EntityType.Builder<?> vanillaBuilder) {
            return new EntityTypeConfiguratorImpl(vanillaBuilder);
        }
        throw new IllegalArgumentException("builder must be a vanilla EntityType.Builder");
    }

    @Override
    public EntityTypeConfigurator size(float width, float height) {
        this.width = width;
        this.height = height;
        builder.sized(width, height);
        return this;
    }

    @Override
    public EntityTypeConfigurator fireImmune() {
        this.fireImmune = true;
        builder.fireImmune();
        return this;
    }
}
