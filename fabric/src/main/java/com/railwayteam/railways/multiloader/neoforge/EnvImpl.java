package com.railwayteam.railways.multiloader.neoforge;

import com.railwayteam.railways.multiloader.Env;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;

/**
 * Fabric implementation of EnvImpl.
 * Placed in the neoforge package so that common code calling
 * {@code EnvImpl.getCurrent()} resolves to this class on the Fabric build.
 */
public class EnvImpl {
    public static Env getCurrent() {
        return FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT
                ? Env.CLIENT
                : Env.SERVER;
    }
}
