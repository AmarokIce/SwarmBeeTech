package club.someoneice.bee.inventory.gui;

import club.someoneice.bee.core.BeeTech;
import club.someoneice.bee.inventory.container.ContainerOilPresser;
import club.someoneice.bee.tile.TileOilPresser;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiOilPresser extends GuiContainer {
    private final ResourceLocation tex = new ResourceLocation(BeeTech.MODID ,"textures/gui/oil_presser.png");
    private final TileOilPresser tile;

    public GuiOilPresser(TileOilPresser tile, World world, int x, int y, int z, InventoryPlayer player) {
        super(new ContainerOilPresser(player, world, x, y, z, tile));
        this.tile = tile;
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
            this.drawTexturedModalRect(foo + 57, bar + 37 + (13 - process), 176, 13 - process, 13, process);
        }

        if (tile.workTick > 0) {
            int process = (int) (tile.workTick * (22 / 200.0D));
            this.drawTexturedModalRect(foo + 80, bar + 35, 176, 17, process, 16);
        }

        if (tile.fluidInventory.getFluidType() == null) return;

        if (tile.fluidInventory.getFluidRemainder() > 0.0f) {
            int process = (int) (tile.fluidInventory.getFluidRemainder() * (54 / tile.fluidInventory.getMaxFluid()));
            this.drawTexturedModalRect(foo + 105, bar + 16 + (54 - process), 208, 54 - process, 18, process);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float step) {
        super.drawScreen(mouseX, mouseY, step);

        if (tile.fluidInventory.getFluidType() == null) return;

        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;

        if ((mouseX > foo + 106 && mouseX < foo + 121) && (mouseY > bar + 17 && mouseY < bar + 68)) {
            List<String> list = Lists.newArrayList();
            list.add(I18n.format("info.bee_tech.gui.fluid_name") + " " + I18n.format(tile.fluidInventory.getFluidType().getTranslationName()));
            list.add(I18n.format("info.bee_tech.gui.fluid_less") + " " + (int) tile.fluidInventory.getFluidRemainder());
            this.drawHoveringText(list, mouseX, mouseY, this.fontRendererObj);
        }
    }

    @Override
    public void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
    }
}
