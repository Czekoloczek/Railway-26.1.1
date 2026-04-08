package net.neoforged.fml.config;

/**
 * Stub for NeoForge's IConfigSpec interface.
 * On Fabric, config is handled via Cloth Config / Forge Config API Port.
 */
public interface IConfigSpec {
    default boolean isCorrect(Object instance) { return true; }
    default void correct(Object instance) {}
}
