package club.someoneice.bee.api;

import net.minecraft.item.ItemStack;

public interface IFluidType {
    String getName();

    default String getTranslationName() {
        return "fluid." + this.getName();
    }

    float getUnitOfBottle();
    ItemStack getOutput();
    ItemStack[] getBottle();
    ItemStack getFluidInBottle(IFluidInventory inventory, ItemStack item);
    boolean canFilling(IFluidInventory inventory, ItemStack bottle);
}
