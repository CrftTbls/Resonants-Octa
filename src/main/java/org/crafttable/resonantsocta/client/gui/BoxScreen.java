package org.crafttable.resonantsocta.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.crafttable.resonantsocta.inventory.BoxMenu;

public class BoxScreen extends AbstractContainerScreen<BoxMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("minecraft",
            "textures/gui/container/generic_54.png");

    public BoxScreen(BoxMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.imageWidth = 176;
        this.imageHeight = 114 + (menu.boxSlotCount / 9) * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void init() {
        super.init();
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        // 背景の描画 (スロット数に合わせて高さを調整)
        // 簡易的にコンテナのテクスチャを切り貼りするか、独自のテクスチャを用意する必要がありますが、
        // 今回は標準のチェストGUIの上下を組み合わせて描画します。

        // 上部 (タイトル部分)
        guiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, 18);

        // スロット部分
        int rows = (menu.boxSlotCount / 9);
        for (int i = 0; i < rows; i++) {
            guiGraphics.blit(TEXTURE, x, y + 18 + i * 18, 0, 18, imageWidth, 18);
        }

        // インベントリ部分
        guiGraphics.blit(TEXTURE, x, y + 18 + rows * 18, 0, 126, imageWidth, 96);
    }
}
