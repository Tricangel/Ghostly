package bee.ghostly.mixin;

import bee.ghostly.util.GhostUtil;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Equippable.class)
public abstract class EquippableMixin {

    @WrapMethod(method = "swapWithEquipmentSlot")
    private InteractionResult swapWithEquipmentSlot(ItemStack inHand, Player player, Operation<InteractionResult> original) {

        if (GhostUtil.isGhost(player)) {
            return InteractionResult.PASS;
        }

        return original.call(inHand, player);
    }
}