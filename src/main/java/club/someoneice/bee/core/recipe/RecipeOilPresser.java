package club.someoneice.bee.core.recipe;

import club.someoneice.bee.api.IFluidType;
import club.someoneice.bee.core.W3Util;
import club.someoneice.togocup.tags.Ingredient;
import net.minecraft.item.ItemStack;

public class RecipeOilPresser {
    public final Ingredient input0;
    public final Ingredient input1;

    public final IFluidType output;
    public final float outputValue;

    public RecipeOilPresser(Ingredient input0, Ingredient input1, IFluidType output, float outputValue) {
        this.input0 = input0;
        this.input1 = input1;
        this.output = output;
        this.outputValue = outputValue;
    }

    public boolean match(ItemStack input0, ItemStack input1) {
        return W3Util.init.compareRecipe(new Ingredient[] { this.input0, this.input1 }, new ItemStack[] { input0, input1 });
    }
}
