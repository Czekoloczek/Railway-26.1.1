package com.railwayteam.railways.util.neoforge;

import com.railwayteam.railways.content.conductor.ConductorEntity;
import com.railwayteam.railways.neoforge.ConductorFakePlayerForge;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.BlockHitResult;

/**
 * Fabric implementation of EntityUtilsImpl.
 */
public class EntityUtilsImpl {

    public static CompoundTag getPersistentData(Entity entity) {
        return entity.getPersistentData();
    }

    public static void givePlayerItem(Player player, ItemStack stack) {
        if (!player.getInventory().add(stack) && !stack.isEmpty()) {
            player.drop(stack, false);
        }
    }

    public static ServerPlayer createConductorFakePlayer(ServerLevel level, ConductorEntity conductor) {
        return new ConductorFakePlayerForge(level, conductor);
    }

    public static double getReachDistance(Player player) {
        return player.blockInteractionRange();
    }

    /**
     * On Fabric there is no equivalent to NeoForge's RightClickBlock event that can cancel early.
     * Return true to indicate "use allowed" (vanilla behavior).
     * TODO: Implement cancellation via Fabric's UseBlockCallback if needed.
     */
    public static boolean handleUseEvent(Player player, InteractionHand hand, BlockHitResult hit) {
        return true;
    }
}
