package club.someoneice.bee.core;

import club.someoneice.bee.asm.TilePatch;
import club.someoneice.bee.core.recipe.RecipeCentrifuge;
import club.someoneice.bee.core.recipe.RecipeOilPresser;
import club.someoneice.pineapplepsychic.util.Util;
import club.someoneice.togocup.tags.Ingredient;
import com.google.common.collect.Lists;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import project.studio.manametalmod.ManaMetalAPI;
import project.studio.manametalmod.ManaMetalMod;
import project.studio.manametalmod.core.ItemStackOre;
import project.studio.manametalmod.core.RecipeOre;
import project.studio.manametalmod.newmc.NewMinecraftCore;
import project.studio.manametalmod.produce.beekeeping.BeekeepingCore;

import java.util.ArrayList;
import java.util.List;

public class RecipeInit {
    public static final List<RecipeOilPresser> OIL_PRESS_RECIPES = Lists.newArrayList();
    public static final List<RecipeCentrifuge> CENTRIFUGES_RECIPES = Lists.newArrayList();

    static void init() {
        OIL_PRESS_RECIPES.add(new RecipeOilPresser(new Ingredient(Tags.SEED_TAG), new Ingredient(Tags.SEED_TAG), FluidTypeInit.OIL, 20));
        OIL_PRESS_RECIPES.add(new RecipeOilPresser(new Ingredient(Tags.TREE_TAG), new Ingredient(Tags.TREE_TAG), FluidTypeInit.TONG_OIL, 10));

        CENTRIFUGES_RECIPES.add(new RecipeCentrifuge(new Ingredient(Tags.HONEYCOMB_TAG), null, new ItemStack(BeekeepingCore.beeswax), null, 0.5, 0.0, FluidTypeInit.HONEY, 20));
        CENTRIFUGES_RECIPES.add(new RecipeCentrifuge(new Ingredient(Tags.HONEYCOMB_HONEY_TAG), null, new ItemStack(BeekeepingCore.beeswax), null, 0.5, 0.0, FluidTypeInit.HONEY, 100));

        removeRecipe(new ItemStack(BeekeepingCore.moistureproofPlank));
        removeRecipe(new ItemStack(BeekeepingCore.Royaljelly));
        removeRecipe(new ItemStack(TilePatch.beeSeparator));

        GameRegistry.addRecipe(new ItemStack(TilePatch.beeSeparator), "MMM", "RAR", "MRM", 'M', BeekeepingCore.moistureproofPlank, 'R', ManaMetalMod.ironGear, 'A', BeekeepingCore.Pollen);
        GameRegistry.addRecipe(new ItemStack(BlockInit.CENTRIFUGE), "MMM", "RAR", "MRM", 'M', BeekeepingCore.moistureproofPlank, 'R', ManaMetalMod.ironGear, 'A', Items.bucket);

        for (int i = 0; i < 16; i ++) {
            ItemStack obj = new ItemStack(NewMinecraftCore.BlockCandles, 1, i);
            removeRecipe(obj);
            GameRegistry.addRecipe(obj, "DTD", "SBS", "SBS", 'D', new ItemStack(Items.dye, 4, 15 - i), 'T', Items.string, 'S', BeekeepingCore.beeswax, 'B', Items.glass_bottle);
        }

        ManaMetalAPI.FoodFermentationRecipeList.add(new RecipeOre(new ItemStackOre("plankWood"), new ItemStackOre(BeekeepingCore.Tungoil), new ItemStack(BeekeepingCore.moistureproofPlank)));
    }

    public static void removeRecipe(ItemStack item) {
        ArrayList<IRecipe> recipes = (ArrayList<IRecipe>) CraftingManager.getInstance().getRecipeList();
        int i = 0;
        while (i < recipes.size()) {
            IRecipe tmpRecipe = recipes.get(i);
            ItemStack recipeResult = tmpRecipe.getRecipeOutput();
            if (Util.itemStackEquals(item, recipeResult)) recipes.remove(i--);
            i++;
        }
    }
}
