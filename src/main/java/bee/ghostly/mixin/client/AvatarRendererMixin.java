package bee.ghostly.mixin.client;

import bee.ghostly.util.GhostUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemEntity.class)
public abstract class AvatarRendererMixin {



	@ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/Level;isClientSide()Z"), method = "playerTouch")
	private boolean makeGray(boolean original, Player player) {

		if (GhostUtil.isGhost(player)) {
			return true;
		}

		return original;
	}
}