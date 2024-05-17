package club.someoneice.bee.inventory.gui;

import club.someoneice.bee.core.BeeTech;
import club.someoneice.bee.inventory.container.ContainerSeparator;
import club.someoneice.bee.tile.TileSeparator;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class GuiSeparator extends GuiContainer {
    private final ResourceLocation tex = new ResourceLocation(BeeTech.MODID ,"textures/gui/separator.png");
    private final TileSeparator tile;

    public GuiSeparator(TileSeparator tile, World world, int x, int y, int z, InventoryPlayer player) {
        super(new ContainerSeparator(player, world, x, y, z, tile));
        this.tile = tile;

        this.xSize = 185;
        this.ySize = 205;
    }

    @Override
    public void drawGuiContainerBackgroundLayer(float step, int mouseX, int mouseY) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(tex);
        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(foo, bar, 0, 0, this.xSize, this.ySize);

        if (tile.burnTick > 0) {
            int process = (int) (tile.burnTick * (13 / (double) tile.maxBurn));
            this.drawTexturedModalRect(foo + 42, bar + 49 + (13 - process), 192, 13 - process, 13, process);
        }

        if (tile.workTick > 0) {
            int process = (int) (tile.workTick * (41 / 200.0D));
            this.drawTexturedModalRect(foo + 63, bar + 50, 192, 16, process, 8);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }
}
