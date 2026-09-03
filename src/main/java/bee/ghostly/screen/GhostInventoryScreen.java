package bee.ghostly.screen;

import bee.ghostly.Ghostly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class GhostInventoryScreen extends AbstractContainerScreen<GhostInventoryMenu> {
    public static final Identifier INVENTORY = Ghostly.id("textures/gui/container/inventory.png");

    public GhostInventoryScreen(GhostInventoryMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    public GhostInventoryScreen(Player player) {
        this(new GhostInventoryMenu(player), player.getInventory(), Component.empty());
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }


    @Override
    public void extractBackground(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        int xo = this.leftPos;
        int yo = this.topPos;
        graphics.blit(RenderPipelines.GUI_TEXTURED, GhostInventoryScreen.INVENTORY, xo, yo - 8, 0.0F, 0.0F, this.imageWidth, this.imageHeight, 256, 256);
        extractEntityInInventoryFollowsMouse(graphics, xo + 26, yo + 8, xo + 75, yo + 70, 30, 0.0625F, mouseX, mouseY, this.minecraft.player);
    }

    public static void extractEntityInInventoryFollowsMouse(final GuiGraphicsExtractor graphics, final int x0, final int y0, final int x1, final int y1, final int size, final float offsetY, final float mouseX, final float mouseY, final LivingEntity entity) {
        float centerX = (float) (x0 + x1) / 2.0F;
        float centerY = (float) (y0 + y1) / 2.0F;
        float xAngle = (float) Math.atan((double) ((centerX - mouseX) / 40.0F));
        float yAngle = (float) Math.atan((double) ((centerY - mouseY) / 40.0F));
        Quaternionf rotation = (new Quaternionf()).rotateZ((float) Math.PI);
        Quaternionf xRotation = (new Quaternionf()).rotateX(yAngle * 20.0F * ((float) Math.PI / 180F));
        rotation.mul(xRotation);
        EntityRenderState renderState = extractRenderState(entity);
        if (renderState instanceof LivingEntityRenderState livingRenderState) {
            livingRenderState.bodyRot = 180.0F + xAngle * 20.0F;
            livingRenderState.yRot = xAngle * 20.0F;
            if (livingRenderState.pose != Pose.FALL_FLYING) {
                livingRenderState.xRot = -yAngle * 20.0F;
            } else {
                livingRenderState.xRot = 0.0F;
            }

            livingRenderState.boundingBoxWidth /= livingRenderState.scale;
            livingRenderState.boundingBoxHeight /= livingRenderState.scale;
            livingRenderState.scale = 1.0F;
        }

        Vector3f translation = new Vector3f(0.0F, renderState.boundingBoxHeight / 2.0F + offsetY, 0.0F);
        graphics.entity(renderState, (float) size, translation, rotation, xRotation, x0, y0, x1, y1);
    }

    private static EntityRenderState extractRenderState(final LivingEntity entity) {
        EntityRenderDispatcher entityRenderDispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        EntityRenderer<? super LivingEntity, ?> renderer = entityRenderDispatcher.getRenderer(entity);
        EntityRenderState renderState = renderer.createRenderState(entity, 1.0F);
        renderState.shadowPieces.clear();
        renderState.outlineColor = 0;
        return renderState;
    }
}
