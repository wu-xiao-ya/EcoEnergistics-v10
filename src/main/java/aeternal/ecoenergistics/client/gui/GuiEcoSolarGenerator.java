package aeternal.ecoenergistics.client.gui;

import aeternal.ecoenergistics.common.inventory.container.ContainerEcoSolarGenerator;
import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import mekanism.client.SpecialColors;
import mekanism.client.gui.GuiMekanism;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.GuiSideHolder;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.tab.GuiRedstoneControlTab;
import mekanism.client.gui.element.tab.GuiSecurityTab;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.common.base.IRedstoneControl;
import mekanism.common.inventory.warning.WarningTracker;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
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
// Keep Mekanism's GUI framework without matching third-party handlers that target every GuiMekanismTile.
public class GuiEcoSolarGenerator extends GuiMekanism<ContainerEcoSolarGenerator> {

    private final TileEntityEcoSolarPanel tileEntity;

    public GuiEcoSolarGenerator(InventoryPlayer inventory, TileEntityEcoSolarPanel tile) {
        super(new ContainerEcoSolarGenerator(inventory, tile));
        tileEntity = tile;
        dynamicSlots = true;
    }

    public TileEntityEcoSolarPanel getTileEntity() {
        return tileEntity;
    }

    @Override
    protected void addGuiElements() {
        addButton(GuiSideHolder.create(this, -26, 6, 98, true, true, SpecialColors.TAB_ARMOR_SLOTS));
        super.addGuiElements();
        addRedstoneControlTab();
        addButton(new GuiSecurityTab<>(this, tileEntity));
        addButton(new GuiInnerScreen(this, 40, 23, 96, 40, this::getScreenText));
        addButton(new GuiEnergyTab(this, (GuiTexturedElement.IInfoHandler) () -> getEnergyTabText(tileEntity.getProduction())));
        addButton(new GuiVerticalPowerBar(this, tileEntity.getMainEnergyContainer(), 164, 15));
        addButton(new GuiStateTexture(this, 18, 35, tileEntity::canSeeSun,
                new ResourceLocation("mekanismgenerators", "gui/sees_sun.png"),
                new ResourceLocation("mekanismgenerators", "gui/no_sun.png")));
    }

    private void addRedstoneControlTab() {
        addButton(new GuiRedstoneControlTab<>(this, tileEntity)
                .warning(WarningTracker.WarningType.REDSTONE_SIGNAL_ABSENT,
                        () -> tileEntity.getControlType() == IRedstoneControl.RedstoneControl.HIGH
                                && !MekanismUtils.canFunction(tileEntity))
                .warning(WarningTracker.WarningType.REDSTONE_SIGNAL_PRESENT,
                        () -> tileEntity.getControlType() == IRedstoneControl.RedstoneControl.LOW
                                && !MekanismUtils.canFunction(tileEntity))
                .warning(WarningTracker.WarningType.REDSTONE_PULSE_REQUIRED,
                        () -> tileEntity.getControlType() == IRedstoneControl.RedstoneControl.PULSE
                                && !MekanismUtils.canFunction(tileEntity)));
    }

    @Override
    protected void drawForegroundText(int mouseX, int mouseY) {
        drawTitleText(new TextComponentString(tileEntity.getName()), 6);
        renderInventoryText();
        super.drawForegroundText(mouseX, mouseY);
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

    private List<ITextComponent> getEnergyTabText(double production) {
        return Arrays.asList(
                new TextComponentString(LangUtils.localize("gui.producing") + ": "
                        + MekanismUtils.getEnergyDisplay(production) + "/t"),
                new TextComponentString(LangUtils.localize("gui.maxOutput") + ": "
                        + MekanismUtils.getEnergyDisplay(tileEntity.getMaxOutput()) + "/t")
        );
    }

}
