package bee.ghostly.mixin;

import bee.ghostly.util.GhostUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerMixin {

    @Inject(at = @At(value = "HEAD"), method = "isPickable", cancellable = true)
    private void isPickable(CallbackInfoReturnable<Boolean> cir) {

        if ((Player) (Object) this instanceof Player player && GhostUtil.isGhost(player)) {
            cir.setReturnValue(false);
        }

    }

    @Inject(at = @At(value = "HEAD"), method = "isInvulnerableTo", cancellable = true)
    private void isInvulnerableTo(CallbackInfoReturnable<Boolean> cir) {

        if ((Player) (Object) this instanceof Player player && GhostUtil.isGhost(player)) {
            cir.setReturnValue(true);
        }

    }
}