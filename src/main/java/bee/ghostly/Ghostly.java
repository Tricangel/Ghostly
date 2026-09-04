package bee.ghostly;

import bee.ghostly.networking.SyncGhostPacket;
import bee.ghostly.registry.GhostlyMenuTypes;
import bee.ghostly.registry.SlopAttachments;
import bee.ghostly.util.GhostUtil;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Ghostly implements ModInitializer {
    public static final String MOD_ID = "ghostly";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        GhostlyMenuTypes.init();
        SlopAttachments.init();

        ServerPlayConnectionEvents.JOIN.register(((serverGamePacketListener, packetSender, minecraftServer) -> {

            GhostUtil.syncWithClient(serverGamePacketListener.getPlayer());

        }));

        ServerPlayerEvents.AFTER_RESPAWN.register((serverPlayer, serverPlayer1, b) -> {

            GhostUtil.setGhost(serverPlayer, true);
            GhostUtil.setGhost(serverPlayer1, true);

        });

        PayloadTypeRegistry.clientboundPlay().register(SyncGhostPacket.TYPE, SyncGhostPacket.CODEC);

    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
