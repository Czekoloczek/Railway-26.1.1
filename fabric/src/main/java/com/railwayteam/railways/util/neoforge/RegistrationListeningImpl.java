package com.railwayteam.railways.util.neoforge;

import com.railwayteam.railways.util.RegistrationListening.Listener;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

/**
 * Fabric implementation of RegistrationListeningImpl.
 * NeoForge fires InterModEnqueueEvent after registration; on Fabric we use
 * ServerLifecycleEvents.SERVER_STARTED as an approximation, or fire synchronously.
 *
 * TODO: Find the exact Fabric equivalent lifecycle hook for post-registration callbacks.
 *       ServerLifecycleEvents.SERVER_STARTED fires too late for static registrations.
 *       Consider using FabricLoader post-launch or a dedicated Fabric mod lifecycle event.
 */
public class RegistrationListeningImpl {

    private static final Set<Listener<?>> listeners = new HashSet<>();

    public static <T> void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    /** Call this from the Fabric entrypoint after all registrations are done. */
    public static void fireListeners() {
        listeners.forEach(RegistrationListeningImpl::handle);
    }

    private static <T> void handle(Listener<T> listener) {
        ResourceLocation id = listener.id();
        T obj = listener.registry().get(id);
        if (obj != null)
            listener.onRegister(obj);
    }
}
