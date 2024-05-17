package club.someoneice.bee.core;

import club.someoneice.pineapplepsychic.util.ObjectUtil;
import club.someoneice.togocup.tags.ItemStackTag;
import club.someoneice.togocup.tags.TagsManager;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import project.studio.manametalmod.produce.beekeeping.BeekeepingCore;

import java.util.Arrays;

public class Tags {
    public static final ItemStackTag SEED_TAG = creativeItemStackTag("seeds", Items.wheat_seeds, Items.melon_seeds, Items.pumpkin_seeds, Items.potato, Items.carrot);
    public static final ItemStackTag TREE_TAG = creativeItemStackTag("treeSapling");

    private static final ItemStack[] honeycomb = new ItemStack[37];
    private static final ItemStack[] honeycombWithHoney = new ItemStack[37];

    public static final ItemStackTag HONEYCOMB_TAG;
    public static final ItemStackTag HONEYCOMB_HONEY_TAG;

    static {
        for (int i = 0; i < BeekeepingCore.beeCount * 2; i += 2) honeycomb[i / 2] = new ItemStack(BeekeepingCore.honeycomb, 1, i);
        for (int i = 1; i < BeekeepingCore.beeCount * 2; i += 2) honeycombWithHoney[(i - 1) / 2] = new ItemStack(BeekeepingCore.honeycomb, 1, i);

        HONEYCOMB_TAG = creativeItemStackTag("honeycomb", (Object[]) honeycomb);
        HONEYCOMB_HONEY_TAG = creativeItemStackTag("honeycombWithHoney", (Object[]) honeycombWithHoney);

        for(int i = 0; i <= 5; i ++) TREE_TAG.put(new ItemStack(Blocks.sapling, 1, i));

        SEED_TAG.addAll(OreDictionary.getOres("listAllseed"));
        TREE_TAG.addAll(OreDictionary.getOres("treeSapling"));
    }

    private static ItemStackTag creativeItemStackTag(String name, Object ... o) {
        return ObjectUtil.objectRun(TagsManager.manager().registerItemStackTag(name), tag -> {
            Arrays.stream(o).forEach(it -> {
                if (it instanceof Item)             tag.put(new ItemStack((Item) it));
                else if (it instanceof Block)       tag.put(new ItemStack((Block) it));
                else if (it instanceof ItemStack)   tag.put((ItemStack) it);
            });
        });
    }
}
