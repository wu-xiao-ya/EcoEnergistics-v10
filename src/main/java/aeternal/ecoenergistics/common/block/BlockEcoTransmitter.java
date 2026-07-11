package aeternal.ecoenergistics.common.block;

import aeternal.ecoenergistics.client.render.particle.MekanismEcoParticleHelper;
import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsBlocks;
import aeternal.ecoenergistics.common.base.EcoITierItem;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoTransmitter;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoTransmitter.EcoTransmitterType;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoTransmitter.EcoTransmitterType.Size;
import aeternal.ecoenergistics.common.tier.MEETiers;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoMechanicalPipe;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoPressurizedTube;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoSidedPipe;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoUniversalCable;
import aeternal.ecoenergistics.common.util.EcoEnergisticsUtils;
import mekanism.api.IMekWrench;
import mekanism.common.Mekanism;
import mekanism.common.block.BlockTileDrops;
import mekanism.common.block.property.PropertyConnection;
import mekanism.common.integration.multipart.MultipartMekanism;
import mekanism.common.integration.wrenches.Wrenches;
import mekanism.common.tile.transmitter.TileEntitySidedPipe;
import mekanism.common.util.MekanismUtils;
import mekanism.common.util.MultipartUtils;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.obj.OBJModel;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;


public class BlockEcoTransmitter extends BlockTileDrops implements ITileEntityProvider {

    public static AxisAlignedBB[] smallSides = new AxisAlignedBB[7];
    public static AxisAlignedBB[] largeSides = new AxisAlignedBB[7];

    public static AxisAlignedBB smallDefault;
    public static AxisAlignedBB largeDefault;

    static {
        smallSides[0] = new AxisAlignedBB(0.3, 0.0, 0.3, 0.7, 0.3, 0.7);
        smallSides[1] = new AxisAlignedBB(0.3, 0.7, 0.3, 0.7, 1.0, 0.7);
        smallSides[2] = new AxisAlignedBB(0.3, 0.3, 0.0, 0.7, 0.7, 0.3);
        smallSides[3] = new AxisAlignedBB(0.3, 0.3, 0.7, 0.7, 0.7, 1.0);
        smallSides[4] = new AxisAlignedBB(0.0, 0.3, 0.3, 0.3, 0.7, 0.7);
        smallSides[5] = new AxisAlignedBB(0.7, 0.3, 0.3, 1.0, 0.7, 0.7);
        smallSides[6] = new AxisAlignedBB(0.3, 0.3, 0.3, 0.7, 0.7, 0.7);

        largeSides[0] = new AxisAlignedBB(0.25, 0.0, 0.25, 0.75, 0.25, 0.75);
        largeSides[1] = new AxisAlignedBB(0.25, 0.75, 0.25, 0.75, 1.0, 0.75);
        largeSides[2] = new AxisAlignedBB(0.25, 0.25, 0.0, 0.75, 0.75, 0.25);
        largeSides[3] = new AxisAlignedBB(0.25, 0.25, 0.75, 0.75, 0.75, 1.0);
        largeSides[4] = new AxisAlignedBB(0.0, 0.25, 0.25, 0.25, 0.75, 0.75);
        largeSides[5] = new AxisAlignedBB(0.75, 0.25, 0.25, 1.0, 0.75, 0.75);
        largeSides[6] = new AxisAlignedBB(0.25, 0.25, 0.25, 0.75, 0.75, 0.75);

        smallDefault = smallSides[6];
        largeDefault = largeSides[6];
    }

    public BlockEcoTransmitter() {
        super(Material.PISTON);
        setCreativeTab(EcoEnergistics.tabEcoEnergistics);
        setHardness(1F);
        setResistance(10F);
    }

    private static AxisAlignedBB getDefaultForTile(TileEntityEcoSidedPipe tile) {
        if (tile == null || tile.getTransmitterType().getSize() == EcoTransmitterType.Size.SMALL) {
            return smallDefault;
        }
        return largeDefault;
    }

    private static void setDefaultForTile(TileEntityEcoSidedPipe tile, AxisAlignedBB box) {
        if (tile == null) {
            return;
        }
        if (box == null) {
            throw new IllegalStateException("box should not be null");
        }
        if (tile.getTransmitterType().getSize() == Size.SMALL) {
            smallDefault = box;
        } else {
            largeDefault = box;
        }
    }

    private static TileEntityEcoSidedPipe getTileEntitySidedPipe(IBlockAccess world, BlockPos pos) {
        TileEntity tileEntity = MekanismUtils.getTileEntitySafe(world, pos);
        TileEntityEcoSidedPipe sidedPipe = null;
        if (tileEntity instanceof TileEntityEcoSidedPipe) {
            sidedPipe = (TileEntityEcoSidedPipe) tileEntity;
        } else if (Mekanism.hooks.MCMPLoaded) {
            TileEntity childEntity = MultipartMekanism.unwrapTileEntity(world);
            if (childEntity instanceof TileEntityEcoSidedPipe) {
                sidedPipe = (TileEntityEcoSidedPipe) childEntity;
            }
        }
        return sidedPipe;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getActualState(@Nonnull IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(worldIn, pos);
        if (tile != null) {
            state = state.withProperty(BlockStateEcoTransmitter.tierProperty, tile.getBaseTier());
        }
        return state;
    }

    @Nonnull
    @Override
    @Deprecated
    public IBlockState getStateFromMeta(int meta) {
        EcoTransmitterType type = EcoTransmitterType.get(meta);
        return getDefaultState().withProperty(BlockStateEcoTransmitter.typeProperty, type);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        EcoTransmitterType type = state.getValue(BlockStateEcoTransmitter.typeProperty);
        return type.ordinal();
    }

    @Nonnull
    @Override
    public BlockStateContainer createBlockState() {
        return new BlockStateEcoTransmitter(this);
    }

    @SideOnly(Side.CLIENT)
    @Nonnull
    @Override
    public IBlockState getExtendedState(@Nonnull IBlockState state, IBlockAccess w, BlockPos pos) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(w, pos);
        if (tile != null) {
            return tile.getExtendedState(state);
        }
        TileEntitySidedPipe.ConnectionType[] typeArray = new TileEntitySidedPipe.ConnectionType[]{TileEntitySidedPipe.ConnectionType.NORMAL, TileEntitySidedPipe.ConnectionType.NORMAL, TileEntitySidedPipe.ConnectionType.NORMAL,
                TileEntitySidedPipe.ConnectionType.NORMAL, TileEntitySidedPipe.ConnectionType.NORMAL, TileEntitySidedPipe.ConnectionType.NORMAL};
        PropertyConnection connectionProp = new PropertyConnection((byte) 0, (byte) 0, typeArray, true);
        return ((IExtendedBlockState) state).withProperty(OBJModel.OBJProperty.INSTANCE, new OBJModel.OBJState(Collections.emptyList(), true)).withProperty(PropertyConnection.INSTANCE, connectionProp);
    }

    @Nonnull
    @Override
    @Deprecated
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess world, BlockPos pos) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(world, pos);
        if (tile != null && tile.getTransmitterType().getSize() == EcoTransmitterType.Size.SMALL) {
            return smallSides[6];
        }
        return largeSides[6];
    }

    @Override
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox,
                                      @Nonnull List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean b) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(world, pos);
        if (tile != null) {
            List<AxisAlignedBB> boxes = tile.getCollisionBoxes(entityBox.offset(-pos.getX(), -pos.getY(), -pos.getZ()));
            for (AxisAlignedBB box : boxes) {
                collidingBoxes.add(box.offset(pos));
            }
        }
    }

    @Nonnull
    @Override
    @Deprecated
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos) {
        return getDefaultForTile(getTileEntitySidedPipe(world, pos)).offset(pos);
    }

    @Override
    @Deprecated
    public RayTraceResult collisionRayTrace(IBlockState blockState, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull Vec3d start, @Nonnull Vec3d end) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(world, pos);
        if (tile == null) {
            return null;
        }
        List<AxisAlignedBB> boxes = tile.getCollisionBoxes();
        MultipartUtils.AdvancedRayTraceResult result = MultipartUtils.collisionRayTrace(pos, start, end, boxes);
        if (result != null && result.valid()) {
            setDefaultForTile(tile, result.bounds);
        }
        return result != null ? result.hit : null;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        if (stack.isEmpty()) {
            return false;
        }
        IMekWrench wrenchHandler = Wrenches.getHandler(stack);
        if (wrenchHandler != null) {
            RayTraceResult raytrace = new RayTraceResult(new Vec3d(hitX, hitY, hitZ), facing, pos);
            if (wrenchHandler.canUseWrench(player, hand, stack, raytrace) && player.isSneaking()) {
                if (!world.isRemote) {
                    MekanismUtils.dismantleBlock(this, state, world, pos);
                }
                return true;
            }
        }
        return false;
    }

    @Nonnull
    @Override
    protected ItemStack getDropItem(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
        ItemStack itemStack = ItemStack.EMPTY;
        TileEntityEcoSidedPipe tileEntity = getTileEntitySidedPipe(world, pos);
        if (tileEntity != null) {
            itemStack = new ItemStack(EcoEnergisticsBlocks.EcoTransmitter, 1, tileEntity.getTransmitterType().ordinal());
            if (!itemStack.hasTagCompound()) {
                itemStack.setTagCompound(new NBTTagCompound());
            }
            EcoITierItem tierItem = (EcoITierItem) itemStack.getItem();
            tierItem.setBaseTier(itemStack, tileEntity.getBaseTier());
        }
        return itemStack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean addHitEffects(IBlockState state, World world, RayTraceResult target, ParticleManager manager) {
        if (!target.typeOfHit.equals(RayTraceResult.Type.BLOCK)) {
            return super.addHitEffects(state, world, target, manager);
        }
        return MekanismEcoParticleHelper.addBlockHitEffects(world, target.getBlockPos(), target.sideHit, manager) || super.addHitEffects(state, world, target, manager);
    }

    @Override
    public void getSubBlocks(CreativeTabs creativetabs, NonNullList<ItemStack> list) {
        for (EcoTransmitterType type : EcoTransmitterType.values()) {
            if (type.hasTiers()) {
                for (MEETiers tier : MEETiers.values()) {
                    list.add(EcoEnergisticsUtils.getEcoTransmitter(type, tier, 1));
                }
            } else {
                list.add(EcoEnergisticsUtils.getEcoTransmitter(type, MEETiers.ADVANCED, 1));
            }
        }
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(world, pos);
        if (tile != null) {
            tile.onAdded();
        }
    }

    @Override
    @Deprecated
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos neighbor) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(world, pos);
        if (tile != null) {
            EnumFacing side = EnumFacing.getFacingFromVector(neighbor.getX() - pos.getX(), neighbor.getY() - pos.getY(), neighbor.getZ() - pos.getZ());
            tile.onNeighborBlockChange(side);
        }
    }

    @Override
    public void onNeighborChange(IBlockAccess world, BlockPos pos, BlockPos neighbor) {
        TileEntityEcoSidedPipe tile = getTileEntitySidedPipe(world, pos);
        if (tile != null) {
            EnumFacing side = EnumFacing.getFacingFromVector(neighbor.getX() - pos.getX(), neighbor.getY() - pos.getY(), neighbor.getZ() - pos.getZ());
            tile.onNeighborTileChange(side);
        }
    }

    @Override
    public boolean canRenderInLayer(IBlockState state, BlockRenderLayer layer) {
        return layer == BlockRenderLayer.CUTOUT;
    }

    @Nonnull
    @Override
    @Deprecated
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @Deprecated
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @Deprecated
    public boolean isFullBlock(IBlockState state) {
        return false;
    }

    @Nonnull
    @Override
    @Deprecated
    public BlockFaceShape getBlockFaceShape(IBlockAccess world, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        EcoTransmitterType type = EcoTransmitterType.get(meta);
        return switch (type) {
            case UNIVERSAL_CABLE -> new TileEntityEcoUniversalCable();
            case MECHANICAL_PIPE -> new TileEntityEcoMechanicalPipe();
            case PRESSURIZED_TUBE -> new TileEntityEcoPressurizedTube();
        };
    }
}
