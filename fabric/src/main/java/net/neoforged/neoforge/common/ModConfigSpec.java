package net.neoforged.neoforge.common;

import net.neoforged.fml.config.IConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Function;

/**
 * Stub for NeoForge's ModConfigSpec used in CRConfigs.
 * On Fabric, the actual configuration is handled via Cloth Config or
 * Forge Config API Port (if available for MC 26.1.1).
 *
 * TODO: Integrate with Fabric-compatible config API for MC 26.1.1.
 */
public class ModConfigSpec implements IConfigSpec {

    /** Creates an empty spec – values will not be persisted on Fabric without proper integration. */
    public ModConfigSpec() {}

    public static class Builder {

        /**
         * Configure a config object and return (config, spec) pair.
         * On Fabric this is a stub; config values will use defaults.
         */
        public <T> Pair<T, ModConfigSpec> configure(Function<Builder, T> func) {
            T config = func.apply(this);
            return Pair.of(config, new ModConfigSpec());
        }

        // --- Common builder helpers referenced by ConfigBase subclasses ---

        public Builder comment(String... comments) { return this; }
        public Builder translation(String key) { return this; }
        public Builder worldRestart() { return this; }
        public Builder push(String path) { return this; }
        public Builder pop() { return this; }
        public Builder pop(int count) { return this; }

        public <T> ConfigValue<T> define(String path, T defaultValue) {
            return new ConfigValue<>(defaultValue);
        }

        public <T extends Enum<T>> EnumValue<T> defineEnum(String path, T defaultValue) {
            return new EnumValue<>(defaultValue);
        }

        public BooleanValue define(String path, boolean defaultValue) {
            return new BooleanValue(defaultValue);
        }

        public IntValue defineInRange(String path, int defaultValue, int min, int max) {
            return new IntValue(defaultValue);
        }

        public LongValue defineInRange(String path, long defaultValue, long min, long max) {
            return new LongValue(defaultValue);
        }

        public DoubleValue defineInRange(String path, double defaultValue, double min, double max) {
            return new DoubleValue(defaultValue);
        }
    }

    // --- Value holders -------------------------------------------------

    public static class ConfigValue<T> {
        private final T defaultValue;
        public ConfigValue(T defaultValue) { this.defaultValue = defaultValue; }
        public T get() { return defaultValue; }
        public void set(T value) { /* stub */ }
    }

    public static class EnumValue<T extends Enum<T>> extends ConfigValue<T> {
        public EnumValue(T defaultValue) { super(defaultValue); }
    }

    public static class BooleanValue extends ConfigValue<Boolean> {
        public BooleanValue(boolean defaultValue) { super(defaultValue); }
    }

    public static class IntValue extends ConfigValue<Integer> {
        public IntValue(int defaultValue) { super(defaultValue); }
    }

    public static class LongValue extends ConfigValue<Long> {
        public LongValue(long defaultValue) { super(defaultValue); }
    }

    public static class DoubleValue extends ConfigValue<Double> {
        public DoubleValue(double defaultValue) { super(defaultValue); }
    }
}
