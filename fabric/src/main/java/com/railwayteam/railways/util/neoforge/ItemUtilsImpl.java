package com.railwayteam.railways.util.neoforge;

import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Fabric implementation of ItemUtilsImpl.
 */
public class ItemUtilsImpl {
    public static boolean blocksEndermanView(ItemStack stack, Player wearer, EnderMan enderman) {
        return stack.isEnderMask(wearer, enderman);
    }
}
