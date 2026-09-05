package bee.ghostly.client.screen;

import bee.ghostly.registry.GhostlyMenuTypes;
import net.minecraft.resources.Identifier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class GhostInventoryMenu extends AbstractContainerMenu {
    private Container inventory;
    private Player owner;

    public GhostInventoryMenu(Player player) {
        super(GhostlyMenuTypes.GHOST, player.inventoryMenu.containerId);
        this.inventory = player.getInventory();
        this.owner = player;
        this.addSlot(new Slot(inventory, 0, 8 + (4 * 18), 80));
        this.addSlot(new Slot(inventory, 40, 77, 62) {
            @Override
            public void setByPlayer(final ItemStack itemStack, final ItemStack previous) {
                owner.onEquipItem(EquipmentSlot.OFFHAND, previous, itemStack);
                super.setByPlayer(itemStack, previous);
            }

            @Override
            public Identifier getNoItemIcon() {
                return InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD;
            }
        });
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
