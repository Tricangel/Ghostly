package bee.ghostly.networking;

import bee.ghostly.Ghostly;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;

public record SyncGhostPacket(boolean isGhost, int id) implements CustomPacketPayload {
    public static final Identifier GHOST_SYNC_ID = Ghostly.id("ghost_sync");

    public static final CustomPacketPayload.Type<SyncGhostPacket> TYPE = new CustomPacketPayload.Type<>(GHOST_SYNC_ID);

    public static final StreamCodec<RegistryFriendlyByteBuf, SyncGhostPacket> CODEC = StreamCodec.composite(ByteBufCodecs.BOOL, SyncGhostPacket::isGhost, ByteBufCodecs.INT, SyncGhostPacket::id, SyncGhostPacket::new);

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
