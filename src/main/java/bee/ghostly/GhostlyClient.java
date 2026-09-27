package bee.ghostly;

import bee.ghostly.client.overlay.ContainerOverlay;
import bee.ghostly.networking.SyncGhostPacket;
import bee.ghostly.registry.SlopAttachments;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.world.entity.Entity;

public class GhostlyClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        HudElementRegistry.addFirst(Ghostly.id("container_overlay"), new ContainerOverlay());

        ClientPlayNetworking.registerGlobalReceiver(SyncGhostPacket.TYPE, (packet, context) -> {
            boolean isGhost = packet.isGhost();
            ClientLevel level = context.client().level;
            if (level == null) {
                Ghostly.LOGGER.error("what have you done");
                return;
            }
            Entity entity = level.getEntity(packet.id());

            if (entity == context.player()) {

                context.player().setAttached(SlopAttachments.GHOST, isGhost);

            }


        });

    }
}
