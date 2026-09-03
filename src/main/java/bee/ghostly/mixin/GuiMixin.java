package bee.ghostly.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Gui.class)
public abstract class GuiMixin {

    @WrapMethod(method = "extractSlot")
    private void init(GuiGraphicsExtractor graphics, int x, int y, DeltaTracker deltaTracker, Player player, ItemStack itemStack, int seed, Operation<Void> original) {

        original.call(graphics, graphics.guiWidth() / 2, y, deltaTracker, player, itemStack, seed);

    }

    @WrapMethod(method = "extractPlayerHealth")
    private void cancelHealth(GuiGraphicsExtractor graphics, Operation<Void> original) {

    }

    @WrapMethod(method = "extractFood")
    private void cancelfood(GuiGraphicsExtractor graphics, Player player, int yLineBase, int xRight, Operation<Void> original) {

    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"), method = "extractHotbarAndDecorations")
    private boolean cancelxp(boolean original) {

        return false;
    }

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/multiplayer/MultiPlayerGameMode;hasExperience()Z"), method = "nextContextualInfoState")
    private boolean cancelxpthesecondtime(boolean original) {

        return false;
    }
}