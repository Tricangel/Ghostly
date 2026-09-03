package bee.ghostly.mixin;

import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
public abstract class PlayerMixin {

    @Shadow
    @Final
    private NonNullList<ItemStack> items;

    @Inject(at = @At(value = "HEAD"), method = "getFreeSlot", cancellable = true)
    private void init(CallbackInfoReturnable<Integer> cir) {

        if (items.getFirst().isEmpty()) {
            cir.setReturnValue(0);
        } else cir.setReturnValue(-1);

    }

}