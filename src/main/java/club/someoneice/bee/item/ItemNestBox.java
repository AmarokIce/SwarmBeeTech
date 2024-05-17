package club.someoneice.bee.item;

import club.someoneice.bee.core.BeeTech;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.ManaMetalMod;

import java.util.List;

public class ItemNestBox extends Item {
    public ItemNestBox() {
        this.setMaxStackSize(16);
        this.setHasSubtypes(true);
        this.canRepair = false;
        this.setCreativeTab(ManaMetalMod.tab_beekeep);
        this.setUnlocalizedName("nest_box");
        this.setTextureName(BeeTech.MODID + ":nest_box");

        GameRegistry.registerItem(this, "nest_box", BeeTech.MODID);
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return stack.getItemDamage() == 1;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        return itemStack.getItemDamage() == 1 ? new ItemStack(itemStack.getItem()) : null;
    }

    @Override
    public void getSubItems(Item stack, CreativeTabs tab, List list) {
        list.add(new ItemStack(stack, 1, 0));
        list.add(new ItemStack(stack, 1, 1));
    }
}
