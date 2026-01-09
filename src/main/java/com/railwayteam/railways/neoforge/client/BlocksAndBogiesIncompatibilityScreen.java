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
import com.railwayteam.railways.config.CRConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.narration.NarratedElementType;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.neoforged.fml.loading.FMLPaths;

import java.util.List;
import java.nio.file.Path;

public class BlocksAndBogiesIncompatibilityScreen extends Screen {
	private final Screen parent;
	private boolean dontShowAgain;
	private VanillaCheckbox dontShowAgainCheckbox;

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
		int gap = 10;
		int buttonHeight = 20;
		int buttonWidth = Math.min(200, (this.width - 40 - gap) / 2);
		buttonWidth = Math.max(120, buttonWidth);

		int gridWidth = buttonWidth * 2 + gap;
		int leftX = (this.width - gridWidth) / 2;
		int rightX = leftX + buttonWidth + gap;

		int titleY = this.height / 2 - 55;
		List<FormattedCharSequence> lines = this.font.split(MESSAGE, this.width - 40);
		int textBottomY = titleY + 18 + lines.size() * (this.font.lineHeight + 1);
		int row1Y = Math.max(this.height / 2 + 25, textBottomY + 12);
		int row2Y = row1Y + buttonHeight + 6;

		dontShowAgain = CRConfigs.client().hideBlocksAndBogiesIncompatibilityWarning.get();
		dontShowAgainCheckbox = addRenderableWidget(new VanillaCheckbox(
			leftX,
			row1Y,
			buttonWidth,
			buttonHeight,
			Component.literal("Don't show again"),
			dontShowAgain,
			selected -> {
				dontShowAgain = selected;
				CRConfigs.client().hideBlocksAndBogiesIncompatibilityWarning.set(selected);
			}
		));

		addRenderableWidget(Button.builder(Component.literal("Continue"), button -> {
			persistDontShowAgainIfRequested();
			Minecraft.getInstance().setScreen(parent);
		}).bounds(rightX, row1Y, buttonWidth, buttonHeight).build());

		addRenderableWidget(Button.builder(Component.literal("Open Mods Folder"), button -> {
			Path modsDir = FMLPaths.MODSDIR.get();
			modsDir.toFile().mkdirs();
			Util.getPlatform().openFile(modsDir.toFile());
		}).bounds(leftX, row2Y, buttonWidth, buttonHeight).build());

		addRenderableWidget(Button.builder(Component.literal("Quit Game"), button -> {
			Minecraft.getInstance().stop();
		}).bounds(rightX, row2Y, buttonWidth, buttonHeight).build());
	}

	private void persistDontShowAgainIfRequested() {
		CRConfigs.client().hideBlocksAndBogiesIncompatibilityWarning.set(dontShowAgain);
	}

	private static class VanillaCheckbox extends AbstractButton {
		private static final int BOX_SIZE = 18;
		private boolean selected;
		private final java.util.function.Consumer<Boolean> onValueChange;

		private VanillaCheckbox(
			int x,
			int y,
			int width,
			int height,
			Component message,
			boolean selected,
			java.util.function.Consumer<Boolean> onValueChange
		) {
			super(x, y, width, height, message);
			this.selected = selected;
			this.onValueChange = onValueChange;
		}

		@Override
		public void onPress() {
			selected = !selected;
			onValueChange.accept(selected);
		}

		@Override
		protected void updateWidgetNarration(NarrationElementOutput output) {
			output.add(NarratedElementType.TITLE, this.getMessage());
			output.add(NarratedElementType.USAGE, Component.literal("Press to toggle"));
		}

		@Override
		protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
			var font = Minecraft.getInstance().font;
			int x = getX();
			int y = getY();
			int boxX = x;
			int boxY = y + (this.height - BOX_SIZE) / 2;
			int border = this.isHoveredOrFocused() ? 0xFFFFFFFF : 0xFFB0B0B0;
			int fill = 0xFF1A1A1A;

			// Box
			guiGraphics.fill(boxX, boxY, boxX + BOX_SIZE, boxY + BOX_SIZE, border);
			guiGraphics.fill(boxX + 1, boxY + 1, boxX + BOX_SIZE - 1, boxY + BOX_SIZE - 1, fill);

			// Check mark
			if (selected) {
				// Solid checkmark (two thick line segments) so it doesn't look like a thin font glyph.
				int color = 0xFFFFFFFF;
				int thickness = 3;
				// Left leg
				drawThickLine(guiGraphics, boxX + 4, boxY + 10, boxX + 7, boxY + 13, thickness, color);
				// Right leg (longer), slightly higher to look more like vanilla
				drawThickLine(guiGraphics, boxX + 7, boxY + 13, boxX + 14, boxY + 6, thickness, color);
			}

			// Label
			int textX = boxX + BOX_SIZE + 6;
			int textY = y + (this.height - font.lineHeight) / 2;
			guiGraphics.drawString(font, getMessage(), textX, textY, 0xFFFFFFFF, false);
		}

		private static void drawThickLine(GuiGraphics g, int x0, int y0, int x1, int y1, int thickness, int color) {
			int dx = Math.abs(x1 - x0);
			int dy = Math.abs(y1 - y0);
			int sx = x0 < x1 ? 1 : -1;
			int sy = y0 < y1 ? 1 : -1;
			int err = dx - dy;
			int half = Math.max(0, thickness / 2);

			int x = x0;
			int y = y0;
			while (true) {
				g.fill(x - half, y - half, x + half + 1, y + half + 1, color);
				if (x == x1 && y == y1)
					break;
				int e2 = 2 * err;
				if (e2 > -dy) {
					err -= dy;
					x += sx;
				}
				if (e2 < dx) {
					err += dx;
					y += sy;
				}
			}
		}
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
		persistDontShowAgainIfRequested();
		Minecraft.getInstance().setScreen(parent);
	}
}
