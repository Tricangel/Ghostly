package bee.ghostly.mixin;

import bee.ghostly.util.GhostUtil;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class InventoryMixin {

    @Shadow
    @Final
    private NonNullList<ItemStack> items;

    @Shadow
    @Final
    public Player player;

    @Inject(at = @At(value = "HEAD"), method = "getFreeSlot", cancellable = true)
    private void getFreeSlot(CallbackInfoReturnable<Integer> cir) {

        if (GhostUtil.isGhost(this.player)) {
            if (items.getFirst().isEmpty()) {
                cir.setReturnValue(0);
            } else cir.setReturnValue(-1);
        }

    }

    @Inject(at = @At(value = "HEAD"), method = "setSelectedSlot", cancellable = true)
    private void setSelectedSlot(int selected, CallbackInfo ci) {

        if (GhostUtil.isGhost(this.player)) {
            if (selected != 0) ci.cancel();
        }

    }

}