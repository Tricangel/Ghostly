package bee.ghostly.mixin.client;

import bee.ghostly.Ghostly;
import bee.ghostly.util.GhostUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapMethod(method = "extractSlot")
    private void extractSlot(GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, Operation<Void> original) {
        if (GhostUtil.isGhostClient(minecraft)) {
            original.call(graphics, graphics.guiWidth() / 2 - 8, y, deltaTracker, player, itemStack, seed);
        }
        else original.call(graphics, x, y, deltaTracker, player, itemStack, seed);
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"), method = "extractHotbarAndDecorations")
    private boolean extractHotbarAndDecorations(boolean original) {
        if (GhostUtil.isGhostClient(minecraft)) return false;
        return original;
    }


    @WrapOperation(at = @At(value = "INVOKE", ordinal = 0, target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;blitSprite(Lcom/mojang/blaze3d/pipeline/RenderPipeline;Lnet/minecraft/resources/Identifier;IIII)V"), method = "extractItemHotbar")
    private void extractItemHotbar$blitSprite(GuiGraphicsExtractor instance, RenderPipeline renderPipeline, Identifier location, int x, int y, int width, int height, Operation<Void> original) {
        if (GhostUtil.isGhostClient(minecraft)) {

            original.call(instance, renderPipeline, Ghostly.id("hotbar"), x, y, width, height);

        }
        else original.call(instance, renderPipeline, location, x, y, width, height);
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;getSelectedSlot()I"), method = "extractItemHotbar")
    private int extractItemHotbar$getSelectedSlot(int original) {
        if (GhostUtil.isGhostClient(minecraft)) return 4;
        return original;
    }

    @Inject(at = @At("HEAD"), method = {"extractFood", "extractPlayerHealth"}, cancellable = true)
    private void extractFood(CallbackInfo ci) {
        if (GhostUtil.isGhostClient(minecraft)) ci.cancel();
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"), method = "nextContextualInfoState")
    private boolean nextContextualInfoState(boolean original) {
        if (GhostUtil.isGhostClient(minecraft)) return false;
        return original;
    }
}