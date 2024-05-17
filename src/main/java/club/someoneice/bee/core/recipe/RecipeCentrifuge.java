package club.someoneice.bee.core.recipe;

import club.someoneice.bee.api.IFluidType;
import club.someoneice.bee.core.W3Util;
import club.someoneice.togocup.tags.Ingredient;
import net.minecraft.item.ItemStack;

public class RecipeCentrifuge {
    public final Ingredient input;
    public final ItemStack returnItem;
    public final ItemStack[] output = new ItemStack[2];
    public final double[] outputOdds = new double[2];

    public final IFluidType outputFluid;
    public final float outputValue;

    public RecipeCentrifuge(Ingredient input, ItemStack returnItem, ItemStack output0, ItemStack output1, double outputO0, double outputO1, IFluidType outputFluid, float outputValue) {
        this.input = input;
        this.returnItem = returnItem;
        this.output[0] = output0;
        this.output[1] = output1;
        this.outputOdds[0] = outputO0;
        this.outputOdds[1] = outputO1;

        this.outputFluid = outputFluid;
        this.outputValue = outputValue;
    }

    public boolean match(ItemStack input) {
        return W3Util.init.compareIngredientContains(this.input, input);
    }
}
