package aeternal.ecoenergistics.common.tile;

import mekanism.generators.common.tile.TileEntityGenerator;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

public abstract class TileEntityEcoGenerator extends TileEntityGenerator {

    public TileEntityEcoGenerator(String soundPath, String name, double maxEnergy, double out) {
        super(soundPath, name, maxEnergy, out);
    }

    @Override
    public boolean sideIsConsumer(EnumFacing side) {
        return false;
    }

    @Override
    public boolean sideIsOutput(EnumFacing side) {
        return side == EnumFacing.DOWN;
    }

    public abstract boolean canOperate();

    @Nonnull
    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

    @Override
    public boolean renderUpdate() {
        return false;
    }

    @Override
    public boolean lightUpdate() {
        return false;
    }
}
