package bee.ghostly.client.screen;

import bee.ghostly.registry.GhostlyMenuTypes;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GhostInventoryMenu extends AbstractContainerMenu {
    private Container inventory;

    public GhostInventoryMenu(Player player) {
        super(GhostlyMenuTypes.GHOST, player.inventoryMenu.containerId);
        this.inventory = player.getInventory();
        this.addSlot(new Slot(inventory, 0, 8 + (4 * 18), 80));
    }

    public GhostInventoryMenu(int i, Inventory inventory) {
        super(GhostlyMenuTypes.GHOST, i);
        this.inventory = inventory;
        this.addSlot(new Slot(inventory, 0, 8 + (4 * 18), 88));
    }


    @Override
    public ItemStack quickMoveStack(Player player, int slotIndex) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
