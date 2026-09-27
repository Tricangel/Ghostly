package bee.ghostly.client.overlay;

import bee.ghostly.client.screen.GhostInventoryScreen;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;

public class ContainerOverlay implements HudElement {

    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphicsExtractor, DeltaTracker deltaTracker) {
        Minecraft minecraft = Minecraft.getInstance();
        if (!(minecraft.screen instanceof AbstractContainerScreen<?> screen)) {
            return;
        }


        int xo = screen.width;
        int yo = screen.height;
        guiGraphicsExtractor.blit(RenderPipelines.GUI_TEXTURED, GhostInventoryScreen.INVENTORY, xo, yo - 8, 0.0F, 0.0F, 256, 256, 256, 256);
    }
}
