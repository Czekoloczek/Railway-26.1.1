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

package com.railwayteam.railways.neoforge.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class BlocksAndBogiesIncompatibilityScreen extends Screen {
	private final Screen parent;

	private static final Component TITLE = Component.literal("Steam 'n' Rails Neoforge - Incompatibility Detected");
	private static final Component MESSAGE = Component.literal(
		"Incompatibility detected: Create: Blocks & Bogies is installed.\n\n" +
		"This mod is not compatible with Steam 'n' Rails Neoforge. With both installed, you may experience train issues or other crashes. If you are going to continue, we recommend making backups to avoid data loss.\n\n"
	);

	public BlocksAndBogiesIncompatibilityScreen(Screen parent) {
		super(TITLE);
		this.parent = parent;
	}

	@Override
	protected void init() {
		int buttonWidth = 200;
		int buttonHeight = 20;
		int x = (this.width - buttonWidth) / 2;
		int y = this.height / 2 + 35;

		addRenderableWidget(Button.builder(Component.literal("OK"), button -> {
			Minecraft.getInstance().setScreen(parent);
		}).bounds(x, y, buttonWidth, buttonHeight).build());

		addRenderableWidget(Button.builder(Component.literal("Quit Game"), button -> {
			Minecraft.getInstance().stop();
		}).bounds(x, y + buttonHeight + 6, buttonWidth, buttonHeight).build());
	}

	@Override
	public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
		// Don't render the parent screen; it makes this look double-layered.
		// Use the vanilla menu background (dirt/options background) instead.
		this.renderBackground(guiGraphics, mouseX, mouseY, partialTicks);
		// Darken slightly for text readability.
		guiGraphics.fillGradient(0, 0, this.width, this.height, 0xA0000000, 0xC0000000);
		RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);

		// Render buttons first to establish the same render state as vanilla UI.
		super.render(guiGraphics, mouseX, mouseY, partialTicks);

		int titleY = this.height / 2 - 55;
		guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, titleY, 0xFFFFFFFF);

		List<FormattedCharSequence> lines = this.font.split(MESSAGE, this.width - 40);
		int textY = titleY + 18;
		for (FormattedCharSequence line : lines) {
			guiGraphics.drawCenteredString(this.font, line, this.width / 2, textY, 0xFFDDDDDD);
			textY += this.font.lineHeight + 1;
		}
	}

	@Override
	public boolean shouldCloseOnEsc() {
		return true;
	}

	@Override
	public void onClose() {
		Minecraft.getInstance().setScreen(parent);
	}
}
