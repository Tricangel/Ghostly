package bee.ghostly.registry;


import bee.ghostly.Ghostly;
import com.mojang.serialization.Codec;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;

public class SlopAttachments {


    public static final AttachmentType<Boolean> GHOST = createPersistentDeath("ghost", Codec.BOOL);


    public static <A> AttachmentType<A> createPersistentDeath(String name, Codec<A> codec) {
        return AttachmentRegistry.create(Ghostly.id(name), builder -> builder.copyOnDeath().persistent(codec));
    }
    public static void init() {}

}
