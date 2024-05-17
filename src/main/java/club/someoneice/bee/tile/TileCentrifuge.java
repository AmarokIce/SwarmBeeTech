package club.someoneice.bee.tile;

import club.someoneice.bee.api.IFluidType;
import club.someoneice.bee.bean.SimpleFluidInventory;
import club.someoneice.bee.core.RecipeInit;
import club.someoneice.bee.core.recipe.RecipeCentrifuge;
import club.someoneice.pineapplepsychic.api.NBTTag;
import club.someoneice.pineapplepsychic.core.TileEntityBase;
import club.someoneice.pineapplepsychic.inventory.SimpleInventory;
import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;

public class TileCentrifuge extends TileEntityBase implements ISidedInventory {
    @NBTTag
    public SimpleInventory inventory = new SimpleInventory(7);
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

        if (this.inventory.getStackInSlot(1) == null) {
            workTick = 0;
            return;
        }

        RecipeCentrifuge recipe = RecipeInit.CENTRIFUGES_RECIPES.stream()
                .filter(it -> it.match(inventory.getStackInSlot(1))).findFirst().orElse(null);

        if (recipe == null) {
            this.workTick = 0;
            return;
        }

        ItemStack itemOutput0 = inventory.getStackInSlot(3);
        ItemStack itemOutput1 = inventory.getStackInSlot(4);

        ItemStack itemReturn = inventory.getStackInSlot(2);

        if (fluidInventory.getFluidType() != null && fluidInventory.getFluidType() != recipe.outputFluid) return;
        if ((itemOutput0 != null && recipe.output[0] != null) && !Util.itemStackEquals(itemOutput0, recipe.output[0])) return;
        if ((itemOutput1 != null && recipe.output[1] != null) && !Util.itemStackEquals(itemOutput1, recipe.output[1])) return;
        if ((itemReturn != null && recipe.returnItem != null) && !Util.itemStackEquals(itemReturn, recipe.returnItem)) return;

        setBurn();
        if (burnTick == 0) {
            this.maxBurn = 0;
            this.workTick = 0;
            return;
        }

        if (++workTick < 200) return;
        workTick = 0;

        inventory.decrStackSize(1, 1);

        if (recipe.output[0] != null && worldObj.rand.nextDouble() < recipe.outputOdds[0]) {
            if (itemOutput0 != null) itemOutput0.splitStack(-recipe.output[0].stackSize);
            else this.inventory.setInventorySlotContents(3, recipe.output[0].copy());
        }
        if (recipe.output[1] != null && worldObj.rand.nextDouble() < recipe.outputOdds[1]) {
            if (itemOutput1 != null) itemOutput1.splitStack(-recipe.output[1].stackSize);
            else this.inventory.setInventorySlotContents(4, recipe.output[1].copy());
        }
        if (recipe.returnItem != null) {
            if (itemReturn != null) itemReturn.splitStack(-recipe.returnItem.stackSize);
            else this.inventory.setInventorySlotContents(2, recipe.returnItem.copy());
        }

        fluidInventory.setFluidType(recipe.outputFluid);
        fluidInventory.addFluidRemainder(recipe.outputValue);
    }

    private void filling() {
        IFluidType fluid = this.fluidInventory.getFluidType();
        if (fluid == null) return;
        if (!fluid.canFilling(this.fluidInventory, this.inventory.getStackInSlot(5))) return;
        ItemStack outputSlot = this.inventory.getStackInSlot(6);
        if (outputSlot != null && !Util.itemStackEquals(fluid.getOutput(), outputSlot)) return;
        ItemStack output = fluid.getFluidInBottle(this.fluidInventory, this.inventory.decrStackSize(5, 1)).copy();
        if (outputSlot == null) this.inventory.setInventorySlotContents(6, output);
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
        return new int[] { 0, 1, 2, 3, 4 };
    }

    @Override
    public boolean canInsertItem(int slot, ItemStack item, int side) {
        return !this.canExtractItem(slot, item, side);
    }

    @Override
    public boolean canExtractItem(int slot, ItemStack item, int side) {
        return (slot >= 2 && slot <= 4) || slot == 6;
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
