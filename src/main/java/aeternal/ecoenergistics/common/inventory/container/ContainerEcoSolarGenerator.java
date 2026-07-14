package aeternal.ecoenergistics.common.inventory.container;

import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import net.minecraft.entity.player.InventoryPlayer;

public class ContainerEcoSolarGenerator extends ContainerPassiveEcoGenerator<TileEntityEcoSolarPanel> {

    public ContainerEcoSolarGenerator(InventoryPlayer inventory, TileEntityEcoSolarPanel generator) {
        super(inventory, generator);
    }
}
