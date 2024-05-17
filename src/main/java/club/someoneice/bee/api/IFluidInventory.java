package club.someoneice.bee.api;

import club.someoneice.bee.core.FluidTypeInit;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

public interface IFluidInventory {
    IFluidType getFluidType();
    void setFluidType(IFluidType fluid);
    float getFluidRemainder();
    void setFluidRemainder(float value);
    void addFluidRemainder(float value);

    default String getNameOfInventory() {
        return "FluidInventory_" + this.getFluidType().getName();
    }

    @Nonnull
    default NBTTagCompound writeToNBT(@Nonnull NBTTagCompound nbt) {
        if (this.getFluidType() != null) {
            nbt.setString("fluidInventoryType", this.getFluidType().getName());
            nbt.setFloat("fluidInventoryRemainder", this.getFluidRemainder());
        }
        return nbt;
    }

    default void readFromNBT(@Nonnull NBTTagCompound nbt) {
        this.setFluidType(FluidTypeInit.FLUID_TYPES.getOrDefault(nbt.getString("fluidInventoryType"), null));
        this.setFluidRemainder(nbt.getFloat("fluidInventoryRemainder"));
    }
}
