package club.someoneice.bee.tile;

import club.someoneice.pineapplepsychic.api.NBTTag;
import club.someoneice.pineapplepsychic.core.TileEntityBase;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.MathHelper;
import project.studio.manametalmod.produce.beekeeping.BeekeepingCore;

import java.util.Arrays;
import java.util.Objects;

public class TileSeparator extends TileEntityBase implements ISidedInventory {
    @NBTTag
    public SimpleInventory inventory = new SimpleInventory(8);
    @NBTTag public int burnTick = 0;
    @NBTTag public int maxBurn  = 0;
    @NBTTag public int workTick = 0;

    @Override
    public void load(NBTTagCompound nbtTagCompound) {
    }

    @Override
    public void save(NBTTagCompound nbtTagCompound) {
    }

    @Override
    public void updateEntity() {
        work();

        this.markDirty();
    }

    private void work() {
        if (burnTick > 0) burnTick--;

        if (this.inventory.getStackInSlot(1) == null) {
            workTick = 0;
            return;
        }

        final ItemStack itemInput = this.inventory.getStackInSlot(1);
        if (itemInput == null || itemInput.getItem() != BeekeepingCore.honeycomb) {
            this.workTick = 0;
            return;
        }

        if (setBurn() == 0) {
            this.maxBurn = 0;
            this.workTick = 0;
            return;
        }

        if (++workTick < 200) return;
        workTick = 0;

        int itemId = itemInput.getItemDamage();
        ItemStack itemOutput = BeekeepingCore.beelist.get((itemId % 2 == 1 ? itemId / 2 : (itemId - 1) / 2)).out.copy();
        inventory.decrStackSize(1, 1);

        final ItemStack[] outputs = new ItemStack[6];

        itemOutput.stackSize += MathHelper.getRandomIntegerInRange(worldObj.rand, 0, 5);
        outputs[1] = itemOutput;
        if (worldObj.rand.nextDouble() < 0.6) outputs[2] = new ItemStack(BeekeepingCore.beeswax, 2, 0);
        if (worldObj.rand.nextDouble() < 0.2) outputs[2] = new ItemStack(BeekeepingCore.Royaljelly, 1, 0);
        outputs[2] = new ItemStack(BeekeepingCore.Propolis, MathHelper.getRandomIntegerInRange(worldObj.rand, 0, 3), 0);

        Arrays.stream(outputs).filter(Objects::nonNull).forEach(itemStack -> {
            for (int id = 2; id < 8; id ++) {
                if (itemStack.stackSize == 0) break;

                ItemStack item = this.inventory.getStackInSlot(id);
                if (item == null) {
                    this.inventory.setInventorySlotContents(id, itemStack.copy());
                    break;
                } else if (Util.itemStackEquals(item, itemStack)) {
                    int less = 64 - item.stackSize;
                    if (less == 0) continue;

                    boolean flag = itemStack.stackSize <= less;
                    item.stackSize = flag ? item.stackSize + itemStack.stackSize : 64;
                    itemStack.stackSize = flag ? 0 : itemStack.stackSize - less;
                    if (flag) break;
                }
            }
        });
    }

    private int setBurn() {
        if (burnTick == 0 && (burnTick = maxBurn = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0))) != 0)
            inventory.decrStackSize(0, 1);
        return this.burnTick;
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int meta) {
        return new int[] { 0, 1, 2, 3, 4 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return !this.canExtractItem(slot, item, side);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot >= 2;
    }

    @Override
    public int getSizeInventory() {
        return this.inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.inventory.getStackInSlot(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int size) {
        return this.inventory.decrStackSize(slot, size);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int slot) {
        return this.inventory.getStackInSlotOnClosing(slot);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack item) {
        this.inventory.setInventorySlotContents(slot, item);
    }

    @Override
    public String getInventoryName() {
        return this.inventory.getInventoryName();
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.inventory.hasCustomInventoryName();
    }

    @Override
    public int getInventoryStackLimit() {
        return this.inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory() {
        this.inventory.openInventory();
    }

    @Override
    public void closeInventory() {
        this.inventory.closeInventory();
    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack item) {
        return this.inventory.isItemValidForSlot(slot, item);
    }
}
