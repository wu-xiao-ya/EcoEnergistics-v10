package aeternal.ecoenergistics.client.gui;

import aeternal.ecoenergistics.common.inventory.container.ContainerEcoSolarGenerator;
import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import mekanism.common.Mekanism;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.generators.client.gui.GuiGenerator;
import mekanism.generators.client.gui.element.GuiStateTexture;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiEcoSolarGenerator extends GuiGenerator<TileEntityEcoSolarPanel, ContainerEcoSolarGenerator> {

    private boolean firstFrameRendered;

    public GuiEcoSolarGenerator(InventoryPlayer inventory, TileEntityEcoSolarPanel tile) {
        super(tile, new ContainerEcoSolarGenerator(inventory, tile));
        Mekanism.logger.info("[Eco GUI] Constructed solar GUI for {} at {}", tile.getClass().getSimpleName(), tile.getPos());
    }

    @Override
    public void initGui() {
        super.initGui();
        Mekanism.logger.info("[Eco GUI] Initialized solar GUI at {} with {} buttons", tileEntity.getPos(), buttons.size());
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        if (!firstFrameRendered) {
            firstFrameRendered = true;
            Mekanism.logger.info("[Eco GUI] Rendered first solar GUI frame at {}", tileEntity.getPos());
        }
    }

    @Override
    public void onGuiClosed() {
        Mekanism.logger.info("[Eco GUI] Solar GUI closed at {}", tileEntity.getPos());
        super.onGuiClosed();
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addButton(new GuiInnerScreen(this, 40, 23, 96, 40, this::getScreenText));
        addButton(new GuiEnergyTab(this, (GuiTexturedElement.IInfoHandler) () -> getEnergyTabText(tileEntity.getProduction())));
        addButton(new GuiVerticalPowerBar(this, tileEntity.getMainEnergyContainer(), 164, 15));
        addButton(new GuiStateTexture(this, 18, 35, tileEntity::canSeeSun,
                new ResourceLocation("mekanismgenerators", "gui/sees_sun.png"),
                new ResourceLocation("mekanismgenerators", "gui/no_sun.png")));
    }

    private List<ITextComponent> getScreenText() {
        return Arrays.asList(
                new TextComponentString(MekanismUtils.getEnergyDisplay(tileEntity.getEnergy(), tileEntity.getMaxEnergy())),
                new TextComponentString(LangUtils.localize("gui.power") + ": "
                        + MekanismUtils.getEnergyDisplay(tileEntity.getProduction()) + "/t"),
                new TextComponentString(LangUtils.localize("gui.out") + ": "
                        + MekanismUtils.getEnergyDisplay(tileEntity.getMaxOutput()) + "/t")
        );
    }

}
