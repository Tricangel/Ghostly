package bee.ghostly.registry;

import bee.ghostly.Ghostly;
import bee.ghostly.screen.GhostInventoryMenu;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class GhostlyMenuTypes {

    public static final MenuType<GhostInventoryMenu> GHOST = register("ghost", GhostInventoryMenu::new);


    private static <T extends AbstractContainerMenu> MenuType<T> register(final String name, final MenuType.MenuSupplier<T> constructor, final FeatureFlag... flags) {
        return Registry.register(BuiltInRegistries.MENU, Ghostly.id(name), new MenuType(constructor, FeatureFlags.REGISTRY.subset(flags)));
    }

    public static void init() {
    }

}
