package club.someoneice.bee.inventory.container;

import club.someoneice.bee.inventory.slot.SlotOutput;
import club.someoneice.bee.tile.TileCentrifuge;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;

public class ContainerCentrifuge extends Container {
    public SimpleInventory inventory;
    private World worldObj;
    private ChunkPosition pos;

    public ContainerCentrifuge(InventoryPlayer player, World world, int x, int y, int z, TileCentrifuge tile) {
        this.worldObj = world;
        this.pos = new ChunkPosition(x, y, z);
        inventory = tile.inventory;

        this.addSlotToContainer(new Slot(this.inventory, 0, 17, 81));

        this.addSlotToContainer(new Slot(this.inventory, 1, 47, 25));
        this.addSlotToContainer(new SlotOutput(this.inventory, 2, 47, 45));
        this.addSlotToContainer(new SlotOutput(this.inventory, 3, 135, 25));
        this.addSlotToContainer(new SlotOutput(this.inventory, 4, 135, 45));

        this.addSlotToContainer(new Slot(this.inventory, 5, 47, 81));
        this.addSlotToContainer(new SlotOutput(this.inventory, 6, 134, 81));


        for (int h = 0; h < 3; ++h) for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l + h * 9 + 9, 13 + l * 18, 124 + h * 18));
        for (int l = 0; l < 9; ++l) this.addSlotToContainer(new Slot(player, l, 13 + l * 18, 182));
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return !player.isDead;
    }

    public ItemStack transferStackInSlot(EntityPlayer player, int slotNumber) {
        ItemStack itemstack = null;
        Slot slot = (Slot)this.inventorySlots.get(slotNumber);

        if (slot == null || !slot.getHasStack()) return itemstack;

        ItemStack itemstack1 = slot.getStack();
        itemstack = itemstack1.copy();

        if (slotNumber < this.inventory.getSizeInventory()) {
            if (!this.mergeItemStack(itemstack1, this.inventory.getSizeInventory(), this.inventorySlots.size(), true))
                return null;
        } else if (!this.mergeItemStack(itemstack1, 2, this.inventory.getSizeInventory() - 1, false))
            return null;

        if (itemstack1.stackSize == 0) slot.putStack(null);
        else slot.onSlotChanged();

        return itemstack;
    }
}
