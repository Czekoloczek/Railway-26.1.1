package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.simibubi.create.foundation.particle.ICustomParticleData;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.function.Supplier;

/**
 * Fabric implementation of CRParticleTypesParticleEntryImpl.
 * TODO: Implement proper particle provider registration for Fabric / MC 26.1.1.
 *       On Fabric, particle factories are registered via
 *       ClientParticleRegistrationCallback (or equivalent for 26.1.1).
 */
public class CRParticleTypesParticleEntryImpl {

    public static void register(String id, Supplier<ParticleType<?>> supplier) {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE,
                Railways.asResource(id), supplier.get());
    }

    /**
     * Register a particle factory (client-only).
     * On NeoForge this is done via RegisterParticleProvidersEvent; on Fabric via
     * ClientParticleRegistrationCallback or similar.
     * TODO: Wire this up properly in the Fabric client entrypoint.
     */
    @SuppressWarnings("unchecked")
    public static <T extends ParticleOptions> void registerFactory(
            ParticleType<T> type,
            Object event,
            ICustomParticleData<T> customParticleData) {
        // TODO: Use Fabric's particle provider registration API for MC 26.1.1.
        // On 1.20.1 this used: ParticleFactoryRegistry.getInstance().register(type, customParticleData.getFactory(sprite));
        Railways.LOGGER.warn("Particle factory registration not yet implemented on Fabric for: {}", type);
    }

    /**
     * Called from RailwaysClientImpl on NeoForge with the event bus.
     * On Fabric, particle registration is triggered differently.
     * No-op here; actual registration happens via register(id, supplier).
     */
    public static void register(Object bus) {
        // No-op on Fabric – registrations happen inline via register(id, supplier).
    }
}
