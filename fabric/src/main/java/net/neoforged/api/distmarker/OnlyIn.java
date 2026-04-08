package net.neoforged.api.distmarker;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Stub for NeoForge's @OnlyIn annotation used in common code.
 * On Fabric this annotation is retained for compilation only; it has no runtime effect.
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface OnlyIn {
    Dist value();
}
