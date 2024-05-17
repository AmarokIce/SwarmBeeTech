package club.someoneice.bee.bean;

import club.someoneice.bee.api.IFluidInventory;
import club.someoneice.bee.api.IFluidType;
import net.minecraft.util.MathHelper;

public class SimpleFluidInventory implements IFluidInventory {
    private IFluidType fluidType;
    private final float maxFluid;
    private float fluid;

    public SimpleFluidInventory(float maxFluid) {
        this.maxFluid = maxFluid;
    }

    public float getMaxFluid() {
        return maxFluid;
    }

    @Override
    public void setFluidType(IFluidType fluidType) {
        this.fluidType = fluidType;
    }

    @Override
    public IFluidType getFluidType() {
        return this.fluidType;
    }

    @Override
    public float getFluidRemainder() {
        return fluid;
    }

    @Override
    public void setFluidRemainder(float value) {
        this.fluid = value;
    }

    @Override
    public void addFluidRemainder(float value) {
        this.fluid = MathHelper.clamp_float(this.fluid + value, 0, this.maxFluid);
    }
}
