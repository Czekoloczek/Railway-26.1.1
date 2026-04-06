package com.railwayteam.railways.neoforge.asm;

import org.objectweb.asm.tree.ClassNode;

/**
 * Stub for ContainerLevelAccessASM on Fabric.
 * NeoForge uses ASM transformers to patch ContainerLevelAccess; on Fabric this is
 * handled via Mixin instead.
 * TODO: Implement ContainerLevelAccess patch via Mixin for Fabric.
 */
public class ContainerLevelAccessASM {
    public static void processNode(ClassNode targetClass) {
        // No-op on Fabric – use Mixin approach instead.
    }
}
