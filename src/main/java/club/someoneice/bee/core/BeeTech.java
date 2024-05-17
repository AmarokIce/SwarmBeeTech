package club.someoneice.bee.core;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import project.studio.manametalmod.ManaMetalAPI;
import project.studio.manametalmod.produce.beekeeping.BeekeepingCore;

@Mod(modid = BeeTech.MODID, useMetadata = true)
public class BeeTech {
    public static final String MODID = "beetech";
    public static final String NAME = "SwarmBeeTech";
    public static final String VERSION = "@VERSION@";

    public static final Logger LOG = LogManager.getLogger(NAME);

    @Mod.Instance(MODID)
    public static BeeTech INSTANCE;

    public boolean obfuscated;


    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        INSTANCE = this;
        NetworkRegistry.INSTANCE.registerGuiHandler(INSTANCE, new GuiHandler());
    }

    @Mod.EventHandler
    public void commonInit(FMLInitializationEvent event) {
        new BlockInit();

        RecipeInit.init();
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        /* Debug */
        this.obfuscated = !(Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

        for (int i = 0; i < ManaMetalAPI.FoodJuiceRecipeList.size(); i ++) {
            if (ManaMetalAPI.FoodJuiceRecipeList.get(i).getOut().getItem() == BeekeepingCore.Propolis) {
                ManaMetalAPI.FoodJuiceRecipeList.remove(i--);
            }
        }

        for (int i = 0; i < ManaMetalAPI.FoodGrindingRecipeList.size(); i ++) {
            ItemStack output = ManaMetalAPI.FoodGrindingRecipeList.get(i).getOut();
            if (output.getItem() == BeekeepingCore.beeswax
                    || output.getItem() == Item.getItemFromBlock(BeekeepingCore.moistureproofLog)
                    || output.getItem() == BeekeepingCore.honey) {
                ManaMetalAPI.FoodGrindingRecipeList.remove(i--);
            }
        }
        for (int i = 0; i < ManaMetalAPI.FoodFermentationRecipeList.size(); i ++) {
            ItemStack output = ManaMetalAPI.FoodFermentationRecipeList.get(i).getOut();
            if (output.getItem() == BeekeepingCore.Tungoil) {
                ManaMetalAPI.FoodFermentationRecipeList.remove(i--);
            }
        }
    }

    @Mod.EventHandler
    public void onServerStartingEvent(FMLServerStartingEvent event) {
    }

}
