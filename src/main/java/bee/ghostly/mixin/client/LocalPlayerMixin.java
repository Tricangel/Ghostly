package bee.ghostly.mixin.client;

import bee.ghostly.util.GhostUtil;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin {

    @ModifyExpressionValue(at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/Vec3;distanceToSqr(Lnet/minecraft/world/phys/Vec3;)D"), method = "pick")
    private static double init(double original, Entity cameraEntity) {

        if (GhostUtil.isGhost(cameraEntity)) {
            return 1000;
        }

        return original;
    }
}