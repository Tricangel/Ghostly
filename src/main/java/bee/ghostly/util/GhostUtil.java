package bee.ghostly.util;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class GhostUtil {

    public static boolean isGhost(Entity entity) {

        if (!(entity instanceof Player player)) return false;

        return true;

    }

}
