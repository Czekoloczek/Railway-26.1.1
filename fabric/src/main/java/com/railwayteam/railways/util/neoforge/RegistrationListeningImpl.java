package com.railwayteam.railways.util.neoforge;

import com.railwayteam.railways.util.RegistrationListening.Listener;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;

/**
 * Fabric implementation of RegistrationListeningImpl.
 *
 * On NeoForge this fires via {@code InterModEnqueueEvent}; on Fabric we call
 * {@link #fireListeners()} explicitly from the {@code ModInitializer} entrypoint
 * after all registrations are complete.
 */
public class RegistrationListeningImpl {

    private static final Set<Listener<?>> listeners = new HashSet<>();

    public static <T> void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    /** Must be called from the Fabric entrypoint after all registrations are done. */
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
