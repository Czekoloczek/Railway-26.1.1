package com.railwayteam.railways.neoforge.asm;

import org.objectweb.asm.tree.ClassNode;

/**
 * Stub for RollingModeEnumAdder on Fabric.
 * NeoForge uses ASM (via Mixin Extras / Chocohead's MM) to add enum members at runtime.
 * On Fabric, the same effect should be achieved via Chocohead's Mixin-MM or Fabric Mixin.
 * TODO: Implement RollingMode extension via Fabric's enum extension mechanism.
 */
public class RollingModeEnumAdder {
    public static void processRollingMode(ClassNode targetClass) {
        // No-op on Fabric – use Chocohead MM or equivalent Fabric approach instead.
    }
}
