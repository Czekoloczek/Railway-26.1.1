package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.registry.CRParticleTypes;
import com.simibubi.create.foundation.particle.ICustomParticleData;
import com.simibubi.create.foundation.particle.ICustomParticleDataWithSprite;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;

import java.util.function.Supplier;

/**
 * Fabric implementation of CRParticleTypesParticleEntryImpl.
 *
 * Particle types are registered via vanilla {@link Registry#register} inline.
 * Particle factories (client-only) are registered via Fabric's
 * {@link ParticleFactoryRegistry}, which must be called from the client
 * entrypoint.
 */
public class CRParticleTypesParticleEntryImpl {

    /**
     * Register a particle type by ID. Called at init time from CRParticleTypes.
     * Unlike NeoForge's DeferredRegister, we register inline here.
     */
    public static void register(String id, Supplier<ParticleType<?>> supplier) {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Railways.asResource(id), supplier.get());
    }

    /**
     * Register all particle types (server-side init).
     * On Fabric registration is already done inline via {@link #register(String, Supplier)};
     * this method just triggers {@link CRParticleTypes#init()} to run the enum constants.
     */
    public static void register(Object modEventBus) {
        CRParticleTypes.init();
    }

    /**
     * Register the particle factory for the given type (client-only).
     *
     * On NeoForge this receives a {@link RegisterParticleProvidersEvent}; on Fabric
     * we use {@link ParticleFactoryRegistry} instead, ignoring the {@code event}
     * parameter (which is the NeoForge shim stub on the Fabric build).
     */
    @SuppressWarnings("unchecked")
    public static <T extends ParticleOptions> void registerFactory(
            ParticleType<T> type,
            RegisterParticleProvidersEvent event,
            ICustomParticleData<T> customParticleData) {

        if (customParticleData instanceof ICustomParticleDataWithSprite<T> withSprite) {
            // Sprite-sheet particle: provide a factory that accepts a SpriteSet.
            var metaFactory = withSprite.getMetaFactory();
            ParticleFactoryRegistry.getInstance()
                    .register(type, (ParticleFactoryRegistry.PendingParticleFactory<T>)
                            spriteSet -> (typeData, world, x, y, z, xSpeed, ySpeed, zSpeed) ->
                                    metaFactory.create(spriteSet)
                                            .createParticle(typeData, world, x, y, z, xSpeed, ySpeed, zSpeed));
        } else {
            // Vanilla/simple factory.
            ParticleProvider<T> provider = customParticleData.getFactory();
            if (provider != null) {
                ParticleFactoryRegistry.getInstance().register(type, provider);
            }
        }
    }

    /**
     * Register all client-side particle factories. Call from the Fabric
     * {@code ClientModInitializer} after the particle types have been
     * registered server-side.
     */
    public static void registerFactories() {
        // RegisterParticleProvidersEvent is a no-op stub on Fabric – pass null.
        for (CRParticleTypes particle : CRParticleTypes.values()) {
            particle.entry.registerFactory(null);
        }
    }
}
