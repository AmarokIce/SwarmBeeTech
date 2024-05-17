package club.someoneice.bee.core;

import club.someoneice.bee.api.IFluidType;
import club.someoneice.bee.bean.SimpleFluidType;
import com.google.common.collect.Maps;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import project.studio.manametalmod.produce.beekeeping.BeekeepingCore;
import project.studio.manametalmod.produce.cuisine.CuisineCore;

import java.util.Map;

public class FluidTypeInit {
    public static final Map<String, IFluidType> FLUID_TYPES = Maps.newHashMap();

    public static IFluidType HONEY = new SimpleFluidType("honey", new ItemStack(BeekeepingCore.honey_bottle), new ItemStack(Items.glass_bottle));
    public static IFluidType OIL = new SimpleFluidType("oil", new ItemStack(CuisineCore.vegetableOil), new ItemStack(Items.glass_bottle));
    public static IFluidType TONG_OIL = new SimpleFluidType("tung_oil", new ItemStack(BeekeepingCore.Tungoil), new ItemStack(Items.glass_bottle));
}
