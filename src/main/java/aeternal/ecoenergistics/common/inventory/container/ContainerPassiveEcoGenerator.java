package aeternal.ecoenergistics.common.inventory.container;

import mekanism.generators.common.inventory.container.ContainerPassiveGenerator;
import mekanism.common.tile.prefab.TileEntityElectricBlock;
import net.minecraft.entity.player.InventoryPlayer;

public abstract class ContainerPassiveEcoGenerator<TILE extends TileEntityElectricBlock> extends ContainerPassiveGenerator<TILE> {

    protected ContainerPassiveEcoGenerator(InventoryPlayer inventory, TILE tile) {
        super(inventory, tile);
    }
}
