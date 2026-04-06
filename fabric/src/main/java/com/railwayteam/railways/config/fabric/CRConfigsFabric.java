package com.railwayteam.railways.config.fabric;

import com.railwayteam.railways.Railways;
import com.railwayteam.railways.config.CRConfigs;
import net.createmod.catnip.config.ConfigBase;
import net.fabricmc.loader.api.FabricLoader;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;

import java.nio.file.Path;

/**
 * Fabric config registration.
 *
 * Calls {@link CRConfigs#registerCommon()} to create the config specs, then
 * loads each spec's TOML backing file from the Fabric config directory.
 * This gives the same persistent-config behaviour as Forge Config API Port
 * without requiring an external dependency.
 *
 * Config files are written to:
 *   <config-dir>/railways-client.toml
 *   <config-dir>/railways-common.toml
 *   <config-dir>/railways-server.toml
 */
public class CRConfigsFabric {

    public static void register() {
        CRConfigs.registerCommon();
        loadAllConfigs();
    }

    private static void loadAllConfigs() {
        Path configDir = FabricLoader.getInstance().getConfigDir();
        loadConfig(ModConfig.Type.CLIENT, configDir.resolve(Railways.MOD_ID + "-client.toml"));
        loadConfig(ModConfig.Type.COMMON, configDir.resolve(Railways.MOD_ID + "-common.toml"));
        loadConfig(ModConfig.Type.SERVER, configDir.resolve(Railways.MOD_ID + "-server.toml"));
    }

    private static void loadConfig(ModConfig.Type type, Path file) {
        ConfigBase config = CRConfigs.CONFIGS.get(type);
        if (config == null) return;
        if (!(config.specification instanceof ModConfigSpec spec)) return;

        spec.loadFromFile(file);
        config.onLoad();
        Railways.LOGGER.debug("[Config] Loaded {} config from {}", type.name().toLowerCase(), file);
    }
}
