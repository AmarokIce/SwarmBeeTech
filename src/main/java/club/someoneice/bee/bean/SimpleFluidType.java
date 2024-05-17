package club.someoneice.bee.bean;

import club.someoneice.bee.api.IFluidInventory;
import club.someoneice.bee.api.IFluidType;
import club.someoneice.bee.core.FluidTypeInit;
import club.someoneice.pineapplepsychic.util.Util;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class SimpleFluidType implements IFluidType {
    private final String name;
    private final ItemStack[] bottles;
    private final ItemStack itemOutput;
    private final float unitOfBottle;

    public SimpleFluidType(String name, ItemStack item, ItemStack ... bottles) {
        this.name = name;
        this.bottles = bottles;
        this.itemOutput = item;
        this.unitOfBottle = 300f;

        FluidTypeInit.FLUID_TYPES.put(name, this);
    }

    public SimpleFluidType(String name, float unitOfBottle, ItemStack item, ItemStack ... bottles) {
        this.name = name;
        this.bottles = bottles;
        this.itemOutput = item;
        this.unitOfBottle = unitOfBottle;

        FluidTypeInit.FLUID_TYPES.put(name, this);
    }

    @Override
    public float getUnitOfBottle() {
        return this.unitOfBottle;
    }

    @Override
    public ItemStack getOutput() {
        return this.itemOutput.copy();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean canFilling(IFluidInventory inventory, ItemStack bottle) {
        return Arrays.stream(bottles).anyMatch(it -> Util.itemStackEquals(bottle, it))
                && inventory.getFluidRemainder() >= this.unitOfBottle;
    }

    @Override
    public ItemStack getFluidInBottle(IFluidInventory inventory, ItemStack item) {
        inventory.addFluidRemainder(-this.unitOfBottle);
        item.splitStack(1);
        return itemOutput.copy();
    }

    @Override
    public ItemStack[] getBottle() {
        return this.bottles;
    }
}
