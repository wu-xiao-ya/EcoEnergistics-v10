package aeternal.ecoenergistics.client;

import aeternal.ecoenergistics.client.gui.GuiEcoSolarGenerator;
import aeternal.ecoenergistics.client.render.EcoRenderer;
import aeternal.ecoenergistics.client.render.item.RenderEcoGeneratorItem;
import aeternal.ecoenergistics.client.render.panelsolar.*;
import aeternal.ecoenergistics.client.render.stationsolar.*;
import aeternal.ecoenergistics.client.render.transmitter.*;
import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsBlocks;
import aeternal.ecoenergistics.common.EcoEnergisticsCommonProxy;
import aeternal.ecoenergistics.common.EcoEnergisticsItems;
import aeternal.ecoenergistics.common.block.states.BlockStateBasic.*;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator.*;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoTransmitter.*;
import aeternal.ecoenergistics.common.block.states.BlockStateOre.*;
import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.item.ItemBlockEcoTransmitter;
import aeternal.ecoenergistics.common.tier.MEETiers;
import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import aeternal.ecoenergistics.common.tile.panelsolar.*;
import aeternal.ecoenergistics.common.tile.stationsolar.*;
import aeternal.ecoenergistics.common.tile.transmitter.*;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mekanism.common.Mekanism;
import mekanism.client.render.MekanismRenderer;
import mekanism.client.render.item.ItemLayerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.IRegistry;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class EcoEnergisticsClientProxy extends EcoEnergisticsCommonProxy {

    private static final IStateMapper generatorMapper = new GeneratorBlockStateMapper();
    private static final IStateMapper transmitterMapper = new TransmitterStateMapper();

    public static Map<String, ModelResourceLocation> transmitterResources = new Object2ObjectOpenHashMap<>();

    @Override
    public void registerTESRs() {
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelAdvanced.class, new RenderSolarPanelAdv());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelHybrid.class, new RenderSolarPanelHybrid());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelPerfectHybrid.class, new RenderSolarPanelPerfectHybrid());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelQuantum.class, new RenderSolarPanelQuantum());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelSpectral.class, new RenderSolarPanelSpectral());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelProtonic.class, new RenderSolarPanelProtonic());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelSingular.class, new RenderSolarPanelSingular());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelDiffractive.class, new RenderSolarPanelDiffractive());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelPhotonic.class, new RenderSolarPanelPhotonic());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelNeutron.class, new RenderSolarPanelNeutron());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationAdv.class, new RenderSolarStationAdv());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationHybrid.class, new RenderSolarStationHybrid());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationPerfectHybrid.class, new RenderSolarStationPerfectHybrid());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationQuantum.class, new RenderSolarStationQuantum());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationSpectral.class, new RenderSolarStationSpectral());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationProtonic.class, new RenderSolarStationProtonic());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationSingular.class, new RenderSolarStationSingular());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationDiffractive.class, new RenderSolarStationDiffractive());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationPhotonic.class, new RenderSolarStationPhotonic());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationNeutron.class, new RenderSolarStationNeutron());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEcoUniversalCable.class, new RenderEcoUniversalCable());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEcoPressurizedTube.class, new RenderEcoPressurizedTube());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityEcoMechanicalPipe.class, new RenderEcoMechanicalPipe());
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()){
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelCrystal.class, new RenderSolarPanelCrystal());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelNeutronium.class, new RenderSolarPanelNeutronium());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarPanelInfinity.class, new RenderSolarPanelInfinity());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationCrystal.class, new RenderSolarStationCrystal());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationNeutronium.class, new RenderSolarStationNeutronium());
            ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySolarStationInfinity.class, new RenderSolarStationInfinity());
        }
    }

    @Override
    public void registerItemRenders() {
        Item.getItemFromBlock(EcoEnergisticsBlocks.EcoGenerator).setTileEntityItemStackRenderer(new RenderEcoGeneratorItem());
        Item.getItemFromBlock(EcoEnergisticsBlocks.EcoGeneratorAdd).setTileEntityItemStackRenderer(new RenderEcoGeneratorItem());
        registerItemRender(EcoEnergisticsItems.MoreControlCircuit);
        registerItemRender(EcoEnergisticsItems.MoreAlloy);
        registerItemRender(EcoEnergisticsItems.MoreDust);
        registerItemRender(EcoEnergisticsItems.Dust);
        registerItemRender(EcoEnergisticsItems.MoreCompressed);
        registerItemRender(EcoEnergisticsItems.MoreRod);
        registerItemRender(EcoEnergisticsItems.MoreIngot);
        registerItemRender(EcoEnergisticsItems.MoreNugget);
        registerItemRender(EcoEnergisticsItems.MoreCrystal);
        registerItemRender(EcoEnergisticsItems.MoreClump);
        registerItemRender(EcoEnergisticsItems.MoreShard);
        registerItemRender(EcoEnergisticsItems.MoreDirtyDust);
        registerItemRender(EcoEnergisticsItems.MoreSolarCell);
        registerItemRender(EcoEnergisticsItems.EnergyTabletAdvanced);
        registerItemRender(EcoEnergisticsItems.EnergyTabletHybrid);
        registerItemRender(EcoEnergisticsItems.EnergyTabletPerfectHybrid);
        registerItemRender(EcoEnergisticsItems.EnergyTabletQuantum);
        registerItemRender(EcoEnergisticsItems.EnergyTabletSpectral);
        registerItemRender(EcoEnergisticsItems.EnergyTabletProtonic);
        registerItemRender(EcoEnergisticsItems.EnergyTabletSingular);
        registerItemRender(EcoEnergisticsItems.EnergyTabletDiffractive);
        registerItemRender(EcoEnergisticsItems.EnergyTabletPhotonic);
        registerItemRender(EcoEnergisticsItems.EnergyTabletNeutron);
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()){
            Item.getItemFromBlock(EcoEnergisticsBlocks.AvaritiaGenerator).setTileEntityItemStackRenderer(new RenderEcoGeneratorItem());
            registerItemRender(EcoEnergisticsItems.AlloyAvaritia);
            registerItemRender(EcoEnergisticsItems.DustAvaritia);
            registerItemRender(EcoEnergisticsItems.CompressedAvaritia);
            registerItemRender(EcoEnergisticsItems.SolarCellAvaritia);
            registerItemRender(EcoEnergisticsItems.ControlCircuitAvaritia);
        }
    }

    @Override
    public void registerBlockRenders() {
        ModelLoader.setCustomStateMapper(EcoEnergisticsBlocks.EcoGenerator, generatorMapper);
        ModelLoader.setCustomStateMapper(EcoEnergisticsBlocks.EcoGeneratorAdd, generatorMapper);
        ModelLoader.setCustomStateMapper(EcoEnergisticsBlocks.EcoTransmitter,transmitterMapper);
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()) {
            ModelLoader.setCustomStateMapper(EcoEnergisticsBlocks.AvaritiaGenerator, generatorMapper);
        }

        for (EcoGeneratorType type : EcoGeneratorType.values()) {
            if (!type.isValidMachine()) {
                continue;
            }
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(type.blockType.getBlock()), type.meta, getInventoryMRL(type.getName()));
        }

        for (EcoTransmitterType type : EcoTransmitterType.values()){
            List<ModelResourceLocation> modelsToAdd = new ArrayList<>();
            String resource = "mekanismecoenergistics:" + type.getName();
            MEETiers tierPointer = null;
            if (type.hasTiers()) {
                tierPointer =  MEETiers.values()[0];
                resource = "mekanismecoenergistics:" + type.getName() + "_" + tierPointer.getName();
            }
            while (true) {
                if (transmitterResources.get(resource) == null) {
                    String properties = "inventory";
                    ModelResourceLocation model = new ModelResourceLocation(resource, properties);
                    transmitterResources.put(resource, model);
                    modelsToAdd.add(model);
                    if (type.hasTiers()) {
                        if (tierPointer.ordinal() <MEETiers.values().length - 1) {
                            tierPointer =MEETiers.values()[tierPointer.ordinal() + 1];
                            resource = "mekanismecoenergistics:" + type.getName() + "_" + tierPointer.getName();
                            continue;
                        }
                    }
                }
                break;
            }
            ModelLoader.registerItemVariants(Item.getItemFromBlock(EcoEnergisticsBlocks.EcoTransmitter), modelsToAdd.toArray(new ModelResourceLocation[]{}));
        }

        ItemMeshDefinition transmitterMesher = stack -> {
            EcoTransmitterType type = EcoTransmitterType.get(stack.getItemDamage());
            if (type != null) {
                String resource = "mekanismecoenergistics:" + type.getName();
                if (type.hasTiers()) {
                    MEETiers tier = ((ItemBlockEcoTransmitter) stack.getItem()).getBaseTier(stack);
                    resource = "mekanismecoenergistics:" + type.getName() + "_" + tier.getName();
                }
                return transmitterResources.get(resource);
            }
            return null;
        };

        ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(EcoEnergisticsBlocks.EcoTransmitter), transmitterMesher);

        for (EnumOreType ore : EnumOreType.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(EcoEnergisticsBlocks.EcoOreBlock), ore.ordinal(), new ModelResourceLocation(new ResourceLocation(EcoEnergistics.MOD_ID, "OreBlock"), "type=" + ore.getName()));
        }
        for (EnumBasicType block : EnumBasicType.values()) {
            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(EcoEnergisticsBlocks.EcoBlockBasic), block.ordinal(), new ModelResourceLocation(new ResourceLocation(EcoEnergistics.MOD_ID, "BlockBasic"), "type=" + block.getName()));
        }
    }

    private ModelResourceLocation getInventoryMRL(String type) {
        return new ModelResourceLocation(new ResourceLocation(EcoEnergistics.MOD_ID, type), "inventory");
    }

    public void registerItemRender(Item item) {
        MekanismRenderer.registerItemRender(EcoEnergistics.MOD_ID, item);
    }

    @SubscribeEvent
    public void onModelBake(ModelBakeEvent event) {
        IRegistry<ModelResourceLocation, IBakedModel> modelRegistry = event.getModelRegistry();
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_ADVANCED);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_HYBRID);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_PERFECTHYBRID);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_QUANTUM);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_SPECTRAL);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_PROTONIC);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_SINGULAR);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_DIFFRACTIVE);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_PHOTONIC);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_PANEL_NEUTRON);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_ADVANCED);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_HYBRID);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_PERFECTHYBRID);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_QUANTUM);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_SPECTRAL);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_PROTONIC);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_SINGULAR);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_DIFFRACTIVE);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_PHOTONIC);
        solarPanelModelBake(modelRegistry, EcoGeneratorType.SOLAR_STATION_NEUTRON);
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()){
            solarPanelModelBake(modelRegistry, EcoGeneratorType.AVARITIA_SOLAR_PANEL_CRYSTAL);
            solarPanelModelBake(modelRegistry, EcoGeneratorType.AVARITIA_SOLAR_PANEL_NEUTRON);
            solarPanelModelBake(modelRegistry, EcoGeneratorType.AVARITIA_SOLAR_PANEL_INFINITY);
            solarPanelModelBake(modelRegistry, EcoGeneratorType.AVARITIA_SOLAR_STATION_CRYSTAL);
            solarPanelModelBake(modelRegistry, EcoGeneratorType.AVARITIA_SOLAR_STATION_NEUTRON);
            solarPanelModelBake(modelRegistry, EcoGeneratorType.AVARITIA_SOLAR_STATION_INFINITY);
        }
    }

    private void solarPanelModelBake(IRegistry<ModelResourceLocation, IBakedModel> modelRegistry, EcoGeneratorType type) {
        ModelResourceLocation modelResourceLocation = getInventoryMRL(type.getName());
        ItemLayerWrapper itemLayerWrapper = new ItemLayerWrapper(modelRegistry.getObject(modelResourceLocation));
        RenderEcoGeneratorItem.modelMap.put(type, itemLayerWrapper);
        modelRegistry.putObject(modelResourceLocation, itemLayerWrapper);
    }

    @Override
    public void preInit() {
        EcoRenderer.init();
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public GuiScreen getClientGui(int ID, EntityPlayer player, World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        Mekanism.logger.info(
                "[Eco GUI] Client GUI request: id={}, pos={}, tile={}, invalid={}",
                ID, pos, tileEntity == null ? "null" : tileEntity.getClass().getSimpleName(),
                tileEntity != null && tileEntity.isInvalid()
        );
        return switch (ID) {
            case 0 -> tileEntity instanceof TileEntityEcoSolarPanel
                    ? new GuiEcoSolarGenerator(player.inventory, (TileEntityEcoSolarPanel) tileEntity)
                    : null;
            default -> null;
        };
    }

    @SubscribeEvent
    public void onGuiOpen(GuiOpenEvent event) {
        GuiScreen current = Minecraft.getMinecraft().currentScreen;
        GuiScreen next = event.getGui();
        if (current instanceof GuiEcoSolarGenerator || next instanceof GuiEcoSolarGenerator) {
            Mekanism.logger.info(
                    "[Eco GUI] Screen transition: {} -> {}",
                    current == null ? "null" : current.getClass().getName(),
                    next == null ? "null" : next.getClass().getName()
            );
        }
    }

    @SubscribeEvent
    public void onStitch(TextureStitchEvent.Pre event) {
    }
}
