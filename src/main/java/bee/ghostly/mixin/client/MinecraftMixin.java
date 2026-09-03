package bee.ghostly.mixin.client;

import bee.ghostly.screen.GhostInventoryScreen;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {

    @WrapOperation(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/Minecraft;setScreen(Lnet/minecraft/client/gui/screens/Screen;)V"), method = "handleKeybinds")
    private void init(Minecraft instance, Screen screen, Operation<Void> original) {

        instance.setScreen(new GhostInventoryScreen(instance.player));

    }
}