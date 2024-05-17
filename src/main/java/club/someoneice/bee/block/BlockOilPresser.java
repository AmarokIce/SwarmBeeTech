package club.someoneice.bee.block;

import club.someoneice.bee.core.BeeTech;
import club.someoneice.bee.tile.TileOilPresser;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import project.studio.manametalmod.MMM;
import project.studio.manametalmod.ManaMetalMod;

public class BlockOilPresser extends BlockContainer {
    private IIcon[] icons = new IIcon[3];

    public BlockOilPresser() {
        super(Material.iron);
        this.setBlockName("juicing");

        this.setCreativeTab(ManaMetalMod.tab_Machine);
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(world, x, y, z, player, side, hitX, hitY, hitZ);
        TileEntity tileEntity = world.getTileEntity(x, y, z);
        if (!(tileEntity instanceof TileOilPresser)) return false;
        TileOilPresser tile = (TileOilPresser) tileEntity;
        if (!world.isRemote) {
            ((EntityPlayerMP) player).playerNetServerHandler.sendPacket(tile.getDescriptionPacket());
            player.openGui(BeeTech.INSTANCE, 0, world, x, y, z);
        }
        return true;
    }

    private static final int[] faceOf = new int[] { 2, 5, 3, 4 };
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack item) {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, faceOf[MathHelper.clamp_int(l, 0, 3)], 2);
    }

    @Override
    public void registerBlockIcons(IIconRegister register) {
        for(int i = 0; i < 3; ++i)
            this.icons[i] = register.registerIcon(MMM.getTextureName("juicing_" + (i + 1)));
    }

    @Override
    public IIcon getIcon(int side, int meta) {
        ForgeDirection fd = ForgeDirection.getOrientation(side);
        if (fd == ForgeDirection.UP || fd == ForgeDirection.DOWN) return this.icons[0];
        if (meta == side) return this.icons[2];
        return this.icons[1];
    }

    @Override
    public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_) {
        return new TileOilPresser();
    }
}
