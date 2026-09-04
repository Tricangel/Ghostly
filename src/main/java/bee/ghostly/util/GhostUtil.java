package bee.ghostly.util;

import bee.ghostly.client.renderstate.DeadRenderState;
import bee.ghostly.networking.SyncGhostPacket;
import bee.ghostly.registry.SlopAttachments;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class GhostUtil {

    public static boolean setGhost(Entity entity, boolean isGhost) {

        if (!(entity instanceof ServerPlayer player)) return false;

        player.setAttached(SlopAttachments.GHOST, isGhost);
        syncWithClient(player);
        return true;

    }

    public static void syncWithClient(Entity entity) {

        if (!(entity instanceof ServerPlayer serverPlayer)) return;

        SyncGhostPacket packet = new SyncGhostPacket(isGhost(serverPlayer), serverPlayer.getId());

        ServerPlayNetworking.send(serverPlayer, packet);

    }

    public static boolean isGhost(Entity entity) {

        if (!(entity instanceof Player player)) return false;

        return player.getAttachedOrSet(SlopAttachments.GHOST, false)    ;

    }

    @Environment(EnvType.CLIENT)
    public static boolean isGhostClient(Minecraft minecraft) {
        if (minecraft.player == null) return false;
        return isGhost(minecraft.player);
    }

    @Environment(EnvType.CLIENT)
    public static boolean isGhostClient(EntityRenderState state) {
        return state.getDataOrDefault(DeadRenderState.DEAD_RENDER_STATE, new DeadRenderState()).isDead;
    }

    @Environment(EnvType.CLIENT)
    public static void setGhostClient(EntityRenderState state, boolean isGhost) {
        DeadRenderState deadRenderState = new DeadRenderState();
        deadRenderState.isDead = isGhost;
        state.setData(DeadRenderState.DEAD_RENDER_STATE, deadRenderState);
    }

}
