package aeternal.ecoenergistics.client.gui;

import aeternal.ecoenergistics.common.inventory.container.ContainerEcoSolarGenerator;
import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import mekanism.client.gui.GuiMekanismTile;
import mekanism.client.gui.element.GuiInnerScreen;
import mekanism.client.gui.element.bar.GuiVerticalPowerBar;
import mekanism.client.gui.element.tab.GuiEnergyTab;
import mekanism.client.gui.element.slot.GuiSlot;
import mekanism.client.gui.element.slot.SlotType;
import mekanism.client.gui.element.GuiTexturedElement;
import mekanism.common.inventory.container.slot.SlotOverlay;
import mekanism.client.gui.element.tab.GuiRedstoneControlTab;
import mekanism.client.gui.element.tab.GuiSecurityTab;
import mekanism.common.util.LangUtils;
import mekanism.common.util.MekanismUtils;
import mekanism.generators.client.gui.element.GuiStateTexture;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiEcoSolarGenerator extends GuiMekanismTile<TileEntityEcoSolarPanel, ContainerEcoSolarGenerator> {

    public GuiEcoSolarGenerator(InventoryPlayer inventory, TileEntityEcoSolarPanel tile) {
        super(tile, new ContainerEcoSolarGenerator(inventory, tile));
    }

    @Override
    protected void addGuiElements() {
        super.addGuiElements();
        addButton(new GuiInnerScreen(this, 40, 23, 96, 40, this::getScreenText));
        addButton(new GuiEnergyTab(this, (GuiTexturedElement.IInfoHandler) () -> getEnergyTabText(tileEntity.getProduction())));
        addButton(new GuiRedstoneControlTab<>(this, tileEntity));
        addButton(new GuiSecurityTab<>(this, tileEntity));
        addButton(new GuiSlot(SlotType.POWER, this, 142, 34).with(SlotOverlay.POWER));
        addButton(new GuiVerticalPowerBar(this, tileEntity.getMainEnergyContainer(), 164, 15));
        addButton(new GuiStateTexture(this, 18, 35, tileEntity::canSeeSun,
                new ResourceLocation("mekanismgenerators", "gui/sees_sun.png"),
                new ResourceLocation("mekanismgenerators", "gui/no_sun.png")));
    }

    @Override
    protected void drawForegroundText(int mouseX, int mouseY) {
        drawTitleText(new TextComponentString(tileEntity.func_70005_c_()), 6);
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
