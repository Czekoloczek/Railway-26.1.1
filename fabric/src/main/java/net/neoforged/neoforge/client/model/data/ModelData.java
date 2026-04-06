package net.neoforged.neoforge.client.model.data;

import java.util.HashMap;
import java.util.Map;

/**
 * Stub for NeoForge's ModelData used in block entity renderers.
 * On Fabric, Flywheel / Create Fly manage model data differently.
 * TODO: Replace with Fabric/Create Fly equivalent in relevant renderers.
 */
public class ModelData {

    public static final ModelData EMPTY = new ModelData();

    private final Map<ModelProperty<?>, Object> properties = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T get(ModelProperty<T> property) {
        return (T) properties.get(property);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final ModelData data = new ModelData();

        public <T> Builder with(ModelProperty<T> property, T value) {
            data.properties.put(property, value);
            return this;
        }

        public ModelData build() {
            return data;
        }
    }
}
