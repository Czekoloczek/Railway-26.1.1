package com.railwayteam.railways.multiloader.neoforge;

import com.railwayteam.railways.multiloader.Loader;

/**
 * Fabric implementation of LoaderImpl.
 */
public class LoaderImpl {
    public static Loader getCurrent() {
        return Loader.FABRIC;
    }
}
