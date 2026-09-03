package bee.ghostly.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.component.AttackRange;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AttackRange.class)
public abstract class AttackRangeMixin {



    @ModifyReturnValue(at = @At(value = "RETURN"), method = "getClosesetHit")
    private HitResult init(HitResult original, Entity attacker, float partial) {

        if (original instanceof EntityHitResult) {
            if (attacker instanceof Player player) {
                Vec3 eyeGaze = attacker.getHeadLookAngle();
                Vec3 missPosition = attacker.getEyePosition(partial).add(eyeGaze);
                return BlockHitResult.miss(missPosition, Direction.getApproximateNearest(eyeGaze), BlockPos.containing(missPosition));

            }
        }

        return original;
    }

}