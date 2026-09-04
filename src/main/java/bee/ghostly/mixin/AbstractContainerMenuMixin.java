package bee.ghostly.mixin;

import bee.ghostly.util.GhostUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractContainerMenu.class)
public abstract class AbstractContainerMenuMixin {


    @Shadow
    protected abstract Slot addSlot(Slot slot);

    @Inject(at = @At(value = "HEAD"), method = "addStandardInventorySlots", cancellable = true)
    private void addStandardInventorySlots(Container container, int left, int top, CallbackInfo ci) {



        if (GhostUtil.isGhostClient(Minecraft.getInstance())) {
            this.addSlot(new Slot(container, 0, 8 + (4 * 18), top));
            ci.cancel();
        }

    }

}