package club.someoneice.bee.core;

import club.someoneice.bee.inventory.container.ContainerCentrifuge;
import club.someoneice.bee.inventory.container.ContainerOilPresser;
import club.someoneice.bee.inventory.container.ContainerSeparator;
import club.someoneice.bee.inventory.gui.GuiCentrifuge;
import club.someoneice.bee.inventory.gui.GuiOilPresser;
import club.someoneice.bee.inventory.gui.GuiSeparator;
import club.someoneice.bee.tile.TileCentrifuge;
import club.someoneice.bee.tile.TileOilPresser;
import club.someoneice.bee.tile.TileSeparator;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == 0) return new GuiOilPresser((TileOilPresser) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
        if (ID == 1) return new GuiCentrifuge((TileCentrifuge) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);
        if (ID == 2) return new GuiSeparator((TileSeparator) world.getTileEntity(x, y, z), world, x, y, z, player.inventory);

        return null;
    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (id == 0) return new ContainerOilPresser(player.inventory, world, x, y, z, (TileOilPresser) world.getTileEntity(x, y, z));
        if (id == 1) return new ContainerCentrifuge(player.inventory, world, x, y, z, (TileCentrifuge) world.getTileEntity(x, y, z));
        if (id == 2) return new ContainerSeparator(player.inventory, world, x, y, z, (TileSeparator) world.getTileEntity(x, y, z));

        return null;
    }
}
