package net.neoforged.fml.config;

/**
 * Stub for NeoForge's ModConfig class.
 * On Fabric, config loading is handled differently; this stub exists only
 * to allow common config code to compile.
 *
 * TODO: replace CRConfigs to use Fabric-compatible config API.
 */
public class ModConfig {

    public enum Type {
        CLIENT,
        COMMON,
        SERVER
    }

    private final IConfigSpec spec;
    private final Type type;

    public ModConfig(Type type, IConfigSpec spec) {
        this.type = type;
        this.spec = spec;
    }

    public IConfigSpec getSpec() {
        return spec;
    }

    public Type getType() {
        return type;
    }
}
