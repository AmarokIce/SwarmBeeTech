package club.someoneice.bee.asm;

import club.someoneice.bee.block.BlockOilPresser;
import club.someoneice.bee.block.BlockSeparator;
import club.someoneice.bee.tile.TileOilPresser;
import club.someoneice.bee.tile.TileSeparator;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.tclproject.mysteriumlib.asm.annotations.EnumReturnSetting;
import net.tclproject.mysteriumlib.asm.annotations.Fix;
import net.tclproject.mysteriumlib.asm.annotations.FixOrder;
import project.studio.manametalmod.core.FuelType;
import project.studio.manametalmod.core.RecipeOre;
import project.studio.manametalmod.utils.TileEntityCore;

import java.util.List;

public class TilePatch {
    public static BlockOilPresser oilPresser = new BlockOilPresser();
    public static BlockSeparator beeSeparator = new BlockSeparator();

    /* ASM Start */

    @Fix(allThatExtend = true, order = FixOrder.FIRST, returnedType = "net.minecraft.block.Block", returnSetting = EnumReturnSetting.ON_NOT_NULL)
    public static Block addTileEntity(TileEntityCore clazz, Material material, String name, List<RecipeOre> list, int speed, FuelType type) {
        if (name.equals("juicing")) {
            GameRegistry.registerTileEntity(TileOilPresser.class, "tile_oil_presser");
            GameRegistry.registerBlock(oilPresser, "BlockTileEntityBase_" + name);

            return oilPresser;
        }

        if (name.equals("bee_separate")) {
            GameRegistry.registerTileEntity(TileSeparator.class, "tile_separator");
            GameRegistry.registerBlock(beeSeparator, "BlockTileEntityBase_" + name);

            return beeSeparator;
        }

        return null;
    }
}
