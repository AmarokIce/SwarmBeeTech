package club.someoneice.bee.tile;

import club.someoneice.bee.api.IFluidType;
import club.someoneice.bee.bean.SimpleFluidInventory;
import club.someoneice.bee.core.RecipeInit;
import club.someoneice.bee.core.recipe.RecipeOilPresser;
import club.someoneice.pineapplepsychic.api.NBTTag;
import club.someoneice.pineapplepsychic.core.TileEntityBase;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;

public class TileOilPresser extends TileEntityBase implements ISidedInventory {
    @NBTTag public SimpleInventory inventory = new SimpleInventory(5);
    @NBTTag public int burnTick = 0;
    @NBTTag public int maxBurn  = 0;
    @NBTTag public int workTick = 0;
    public SimpleFluidInventory fluidInventory = new SimpleFluidInventory(4800f);

    @Override
    public void load(NBTTagCompound nbtTagCompound) {
        fluidInventory.readFromNBT(nbtTagCompound);
    }

    @Override
    public void save(NBTTagCompound nbtTagCompound) {
        fluidInventory.writeToNBT(nbtTagCompound);
    }

    @Override
    public void updateEntity() {
        work();
        filling();

        if (fluidInventory.getFluidRemainder() <= 0) fluidInventory.setFluidType(null);

        this.markDirty();
    }

    private void work() {
        if (burnTick > 0) burnTick--;

        if (!canCraft()) return;

        for (int i = 1; i <= 2; i ++) {
            if (this.inventory.getStackInSlot(i) == null) return;
        }

        RecipeOilPresser recipe = RecipeInit.OIL_PRESS_RECIPES.stream()
                .filter(it -> it.match(inventory.getStackInSlot(1), inventory.getStackInSlot(2))).findFirst().orElse(null);

        if (recipe == null) {
            this.workTick = 0;
            return;
        }

        if (fluidInventory.getFluidType() != null && fluidInventory.getFluidType() != recipe.output) return;

        setBurn();
        if (burnTick == 0) {
            this.maxBurn = 0;
            this.workTick = 0;
            return;
        }

        if (++workTick < 200) return;
        workTick = 0;

        inventory.decrStackSize(1, 1);
        inventory.decrStackSize(2, 1);

        fluidInventory.setFluidType(recipe.output);
        fluidInventory.addFluidRemainder(recipe.outputValue);
    }

    private void filling() {
        IFluidType fluid = this.fluidInventory.getFluidType();
        if (fluid == null) return;
        if (!fluid.canFilling(this.fluidInventory, this.inventory.getStackInSlot(3))) return;
        ItemStack outputSlot = this.inventory.getStackInSlot(4);
        if (outputSlot != null && !Util.itemStackEquals(fluid.getOutput(), outputSlot)) return;
        ItemStack output = fluid.getFluidInBottle(this.fluidInventory, this.inventory.decrStackSize(3, 1)).copy();
        if (outputSlot == null)
            this.inventory.setInventorySlotContents(4, output);
        else outputSlot.stackSize += output.stackSize;
    }

    private boolean canCraft() {
        return this.fluidInventory.getFluidRemainder() < this.fluidInventory.getMaxFluid();
    }

    private void setBurn() {
        if (burnTick == 0 && (burnTick = maxBurn = TileEntityFurnace.getItemBurnTime(inventory.getStackInSlot(0))) != 0)
            inventory.decrStackSize(0, 1);
    }

    @Override
    public boolean canUpdate() {
        return true;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int meta) {
        return new int[] { 0, 1, 2, 3, 4};
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        if (ForgeDirection.getOrientation(side) == ForgeDirection.UP)
            return slot >= 1 && slot <= 2;

        if (slot == 3) return this.fluidInventory.getFluidType() != null
                && Arrays.stream(this.fluidInventory.getFluidType().getBottle())
                .anyMatch(it -> Util.itemStackEquals(it, item));

        return slot == 0;
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return slot == 4;
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
