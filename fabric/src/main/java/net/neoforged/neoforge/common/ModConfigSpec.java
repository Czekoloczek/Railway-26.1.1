package net.neoforged.neoforge.common;

import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.toml.TomlFormat;
import com.electronwill.nightconfig.toml.TomlWriter;
import net.neoforged.fml.config.IConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * TOML-backed replacement for NeoForge's ModConfigSpec.
 *
 * <p>This implementation uses NightConfig to read and write TOML files so that
 * config values persist across game sessions on the Fabric side. The API surface
 * mirrors NeoForge's {@code ModConfigSpec.Builder} so that the shared
 * {@code CRConfigs.registerCommon()} code compiles and runs unchanged.
 *
 * <p>Usage lifecycle:
 * <ol>
 *   <li>{@link Builder#configure} builds the spec and creates all {@link ConfigValue} instances.</li>
 *   <li>{@link #loadFromFile(Path)} reads an existing TOML file and populates every value.</li>
 *   <li>{@link #saveToFile(Path)} writes the current values back to disk.</li>
 * </ol>
 */
public class ModConfigSpec implements IConfigSpec {

    /** dot-separated path → mutable config value */
    final Map<String, ConfigValue<?>> registry = new LinkedHashMap<>();

    ModConfigSpec() {}

    // -----------------------------------------------------------------------
    // File I/O
    // -----------------------------------------------------------------------

    /**
     * Load config values from {@code file}. Creates the file with defaults if
     * it does not exist. Always safe to call even when the file is absent.
     */
    public void loadFromFile(Path file) {
        if (!Files.exists(file)) {
            saveToFile(file); // write defaults
            return;
        }
        try {
            CommentedFileConfig loaded = CommentedFileConfig.of(file, TomlFormat.instance());
            loaded.load();
            for (Map.Entry<String, ConfigValue<?>> entry : registry.entrySet()) {
                Object raw = loaded.getRaw(entry.getKey());
                if (raw != null) {
                    entry.getValue().setRaw(raw);
                }
            }
            loaded.close();
        } catch (Exception e) {
            // If the file is corrupt / unreadable, fall back to defaults silently.
        }
    }

    /**
     * Write all current values (or defaults if unmodified) to {@code file}.
     * Creates parent directories as needed.
     */
    public void saveToFile(Path file) {
        try {
            Files.createDirectories(file.getParent());
            CommentedConfig out = CommentedConfig.inMemory();
            for (Map.Entry<String, ConfigValue<?>> entry : registry.entrySet()) {
                out.set(entry.getKey(), entry.getValue().get());
            }
            StringWriter sw = new StringWriter();
            new TomlWriter().write(out, sw);
            Files.writeString(file, sw.toString());
        } catch (IOException e) {
            // Non-fatal – values remain at their in-memory state.
        }
    }

    // -----------------------------------------------------------------------
    // Builder
    // -----------------------------------------------------------------------

    public static class Builder {

        private final ArrayDeque<String> pathStack = new ArrayDeque<>();
        private final ModConfigSpec spec = new ModConfigSpec();

        /**
         * Build a {@code (config, spec)} pair. The function receives {@code this}
         * builder, constructs a config object (registering all values in the process),
         * and returns it.
         */
        public <T> Pair<T, ModConfigSpec> configure(Function<Builder, T> func) {
            T config = func.apply(this);
            return Pair.of(config, spec);
        }

        // --- Path management ---

        public Builder push(String path) {
            pathStack.addLast(path);
            return this;
        }

        public Builder pop() {
            if (!pathStack.isEmpty()) pathStack.removeLast();
            return this;
        }

        public Builder pop(int count) {
            for (int i = 0; i < count; i++) pop();
            return this;
        }

        // --- Annotation methods (no-op on Fabric) ---

        public Builder comment(String... comments) { return this; }
        public Builder translation(String key) { return this; }
        public Builder worldRestart() { return this; }

        // --- Value registration ---

        private String fullPath(String key) {
            if (pathStack.isEmpty()) return key;
            return String.join(".", pathStack) + "." + key;
        }

        private <V extends ConfigValue<?>> V register(String key, V value) {
            spec.registry.put(fullPath(key), value);
            return value;
        }

        @SuppressWarnings("unchecked")
        public <T> ConfigValue<T> define(String path, T defaultValue) {
            return register(path, new ConfigValue<>(defaultValue));
        }

        @SuppressWarnings("unchecked")
        public <T extends Enum<T>> EnumValue<T> defineEnum(String path, T defaultValue) {
            return register(path, new EnumValue<>(defaultValue));
        }

        public BooleanValue define(String path, boolean defaultValue) {
            return register(path, new BooleanValue(defaultValue));
        }

        public IntValue defineInRange(String path, int defaultValue, int min, int max) {
            return register(path, new IntValue(defaultValue, min, max));
        }

        public LongValue defineInRange(String path, long defaultValue, long min, long max) {
            return register(path, new LongValue(defaultValue, min, max));
        }

        public DoubleValue defineInRange(String path, double defaultValue, double min, double max) {
            return register(path, new DoubleValue(defaultValue, min, max));
        }
    }

    // -----------------------------------------------------------------------
    // Value types
    // -----------------------------------------------------------------------

    public static class ConfigValue<T> {

        protected T currentValue;
        final T defaultValue;

        ConfigValue(T defaultValue) {
            this.defaultValue = defaultValue;
            this.currentValue = defaultValue;
        }

        public T get() {
            return currentValue;
        }

        public void set(T value) {
            this.currentValue = value;
        }

        @SuppressWarnings("unchecked")
        void setRaw(Object raw) {
            try {
                set((T) raw);
            } catch (ClassCastException ignored) {
                // keep current / default value
            }
        }
    }

    public static class EnumValue<T extends Enum<T>> extends ConfigValue<T> {

        private final Class<T> enumClass;

        @SuppressWarnings("unchecked")
        EnumValue(T defaultValue) {
            super(defaultValue);
            this.enumClass = (Class<T>) defaultValue.getClass();
        }

        @Override
        void setRaw(Object raw) {
            if (raw instanceof String s) {
                try {
                    set(Enum.valueOf(enumClass, s));
                    return;
                } catch (IllegalArgumentException ignored) {
                    // try case-insensitive
                    for (T constant : enumClass.getEnumConstants()) {
                        if (constant.name().equalsIgnoreCase(s)) {
                            set(constant);
                            return;
                        }
                    }
                }
            }
            // Not a recognised string – keep default.
        }
    }

    public static class BooleanValue extends ConfigValue<Boolean> {
        BooleanValue(boolean defaultValue) { super(defaultValue); }

        @Override
        void setRaw(Object raw) {
            if (raw instanceof Boolean b) set(b);
            else if (raw instanceof String s) set(Boolean.parseBoolean(s));
        }
    }

    public static class IntValue extends ConfigValue<Integer> {
        private final int min, max;
        IntValue(int defaultValue, int min, int max) { super(defaultValue); this.min = min; this.max = max; }

        @Override
        void setRaw(Object raw) {
            int v;
            if (raw instanceof Number n) v = n.intValue();
            else if (raw instanceof String s) { try { v = Integer.parseInt(s); } catch (NumberFormatException e) { return; } }
            else return;
            set(Math.max(min, Math.min(max, v)));
        }
    }

    public static class LongValue extends ConfigValue<Long> {
        private final long min, max;
        LongValue(long defaultValue, long min, long max) { super(defaultValue); this.min = min; this.max = max; }

        @Override
        void setRaw(Object raw) {
            long v;
            if (raw instanceof Number n) v = n.longValue();
            else if (raw instanceof String s) { try { v = Long.parseLong(s); } catch (NumberFormatException e) { return; } }
            else return;
            set(Math.max(min, Math.min(max, v)));
        }
    }

    public static class DoubleValue extends ConfigValue<Double> {
        private final double min, max;
        DoubleValue(double defaultValue, double min, double max) { super(defaultValue); this.min = min; this.max = max; }

        @Override
        void setRaw(Object raw) {
            double v;
            if (raw instanceof Number n) v = n.doubleValue();
            else if (raw instanceof String s) { try { v = Double.parseDouble(s); } catch (NumberFormatException e) { return; } }
            else return;
            set(Math.max(min, Math.min(max, v)));
        }
    }
}
