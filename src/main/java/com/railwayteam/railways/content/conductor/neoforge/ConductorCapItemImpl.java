/*
 * Steam 'n' Rails
 * Copyright (c) 2022-2024 The Railways Team
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <https://www.gnu.org/licenses/>.
 */

package com.railwayteam.railways.content.conductor.neoforge;

import com.railwayteam.railways.content.conductor.ConductorCapItem;
import com.railwayteam.railways.content.conductor.ConductorCapModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.Model;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class ConductorCapItemImpl extends ConductorCapItem {
	protected ConductorCapItemImpl(Properties props, DyeColor color) {
		super(props, color);
	}

	public static ConductorCapItem create(Properties props, DyeColor color) {
		return new ConductorCapItemImpl(props, color);
	}

	@Override
	public void initializeClient(Consumer<IClientItemExtensions> consumer) {
		consumer.accept(new IClientItemExtensions() {
			@Nonnull
			@Override
			public Model getGenericArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlot armorSlot, HumanoidModel<?> _default) {
				return ConductorCapModel.of(itemStack, _default, entityLiving);
			}

			@Override
			public int getArmorLayerTintColor(ItemStack stack, LivingEntity entity, net.minecraft.world.item.ArmorMaterial.Layer layer, int layerIdx, int fallbackColor) {
				// In 1.21.x ArmorItem no longer supports getArmorTexture overrides.
				// We render the cap via an entity render layer (using textures/entity/caps/*) instead,
				// so make the vanilla armor layer invisible to prevent double-rendering.
				return (stack.getItem() instanceof ConductorCapItem) ? 0x00000000 : fallbackColor;
			}
		});
		super.initializeClient(consumer);
	}

	@Override
	public boolean isEnderMask(ItemStack stack, Player player, EnderMan endermanEntity) {
		return true;
	}
}
