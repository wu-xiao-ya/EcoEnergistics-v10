package aeternal.ecoenergistics.common.inventory.container;

import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import mekanism.common.Mekanism;
import mekanism.common.util.SecurityUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ContainerEcoSolarGenerator extends ContainerPassiveEcoGenerator<TileEntityEcoSolarPanel> {

    private final TileEntityEcoSolarPanel generator;

    public ContainerEcoSolarGenerator(InventoryPlayer inventory, TileEntityEcoSolarPanel generator) {
        super(inventory, generator);
        this.generator = generator;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        boolean baseAllowed = super.canInteractWith(player);
        World world = generator.getWorld();
        BlockPos pos = generator.getPos();
        boolean loaded = world != null && world.isBlockLoaded(pos);
        boolean sameTile = loaded && world.getTileEntity(pos) == generator;
        boolean accessible = SecurityUtils.canAccess(player, generator);
        boolean inRange = player.getDistanceSq(
                pos.getX() + 0.5D,
                pos.getY() + 0.5D,
                pos.getZ() + 0.5D
        ) <= 64.0D;
        boolean allowed = sameTile && accessible && inRange;

        if (!allowed || !baseAllowed) {
            Mekanism.logger.warn(
                    "[Eco GUI] Container interaction check at {}: baseAllowed={}, invalid={}, loaded={}, sameTile={}, accessible={}, inRange={}, allowed={}",
                    pos, baseAllowed, generator.isInvalid(), loaded, sameTile, accessible, inRange, allowed
            );
        }
        return allowed;
    }
}
