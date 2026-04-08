package com.railwayteam.railways.registry.neoforge;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.registry.CRExtraRegistration;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.core.registries.Registries;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

/**
 * Fabric implementation of CRExtraRegistrationImpl.
 * Uses the same VarHandle reflection approach to access Create's Registrate.
 * TODO: Verify that Create Fly exposes Registrate in the same way for MC 26.1.1.
 */
public class CRExtraRegistrationImpl {

    private static final CreateRegistrate REGISTRATE;

    static {
        CreateRegistrate localRegistrate = null;
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            MethodHandles.Lookup privateLookup = MethodHandles.privateLookupIn(Create.class, lookup);
            VarHandle handle = privateLookup.findStaticVarHandle(Create.class, "REGISTRATE", CreateRegistrate.class);
            localRegistrate = (CreateRegistrate) handle.get();
        } catch (Exception e) {
            Railways.LOGGER.error(
                    "Failed to get Create Fly's Registrate instance – platform registrations skipped", e);
        }
        REGISTRATE = localRegistrate;
    }

    public static void platformSpecificRegistration() {
        if (REGISTRATE != null) {
            REGISTRATE.addRegisterCallback("copycat", Registries.BLOCK_ENTITY_TYPE, CRExtraRegistration::addVentAsCopycat);
            REGISTRATE.addRegisterCallback("track_signal", Registries.BLOCK, CRExtraRegistration::addSignalSource);
        }
    }
}
