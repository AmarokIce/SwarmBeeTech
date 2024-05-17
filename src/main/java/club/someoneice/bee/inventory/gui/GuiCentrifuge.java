package club.someoneice.bee.inventory.gui;

import club.someoneice.bee.core.BeeTech;
import club.someoneice.bee.inventory.container.ContainerCentrifuge;
import club.someoneice.bee.tile.TileCentrifuge;
import com.google.common.collect.Lists;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class GuiCentrifuge extends GuiContainer {
    private final ResourceLocation tex = new ResourceLocation(BeeTech.MODID ,"textures/gui/container.png");
    private final TileCentrifuge tile;

    public GuiCentrifuge(TileCentrifuge tile, World world, int x, int y, int z, InventoryPlayer player) {
        super(new ContainerCentrifuge(player, world, x, y, z, tile));
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
            this.drawTexturedModalRect(foo + 18, bar + 65 + (13 - process), 192, 13 - process, 13, process);
        }

        if (tile.workTick > 0) {
            int process = (int) (tile.workTick * (41 / 200.0D));
            this.drawTexturedModalRect(foo + 79, bar + 28, 192, 16, process, 8);
        }

        if (tile.fluidInventory.getFluidType() == null) return;

        if (tile.fluidInventory.getFluidRemainder() > 0.0f) {
            int process = (int) (tile.fluidInventory.getFluidRemainder() * (61 / tile.fluidInventory.getMaxFluid()));
            this.drawTexturedModalRect(foo + 67, bar + 80, 192, 32, process, 18);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float step) {
        super.drawScreen(mouseX, mouseY, step);

        if (tile.fluidInventory.getFluidType() == null) return;

        int foo = (this.width - this.xSize) / 2;
        int bar = (this.height - this.ySize) / 2;

        if ((mouseX > foo + 67 && mouseX < foo + 129) && (mouseY > bar + 80 && mouseY < bar + 97)) {
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
