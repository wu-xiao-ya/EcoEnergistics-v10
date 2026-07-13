package aeternal.ecoenergistics.common;

import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.inventory.container.ContainerEcoSolarGenerator;
import aeternal.ecoenergistics.common.tile.TileEntityEcoSolarPanel;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelAdvanced;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelCrystal;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelDiffractive;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelHybrid;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelInfinity;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelNeutron;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelNeutronium;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelPerfectHybrid;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelPhotonic;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelProtonic;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelQuantum;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelSingular;
import aeternal.ecoenergistics.common.tile.panelsolar.TileEntitySolarPanelSpectral;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationAdv;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationCrystal;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationDiffractive;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationHybrid;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationInfinity;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationNeutron;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationNeutronium;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationPerfectHybrid;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationPhotonic;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationProtonic;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationQuantum;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationSingular;
import aeternal.ecoenergistics.common.tile.stationsolar.TileEntitySolarStationSpectral;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoMechanicalPipe;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoPressurizedTube;
import aeternal.ecoenergistics.common.tile.transmitter.TileEntityEcoUniversalCable;
import mekanism.common.Mekanism;
import mekanism.common.base.IGuiProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class EcoEnergisticsCommonProxy implements IGuiProvider {

    private static void registerTileEntity(Class<? extends TileEntity> clazz, String name) {
        GameRegistry.registerTileEntity(clazz, new ResourceLocation(EcoEnergistics.MOD_ID, name));
    }


    public void registerTileEntities() {
        registerTileEntity(TileEntitySolarPanelAdvanced.class, "advanced_solar_panel");
        registerTileEntity(TileEntitySolarPanelHybrid.class, "hybrid_solar_panel");
        registerTileEntity(TileEntitySolarPanelPerfectHybrid.class, "perfecthybrid_solar_panel");
        registerTileEntity(TileEntitySolarPanelQuantum.class, "quantum_solar_panel");
        registerTileEntity(TileEntitySolarPanelSpectral.class, "spectral_solar_panel");
        registerTileEntity(TileEntitySolarPanelProtonic.class, "protonic_solar_panel");
        registerTileEntity(TileEntitySolarPanelSingular.class, "singular_solar_panel");
        registerTileEntity(TileEntitySolarPanelDiffractive.class, "diffractive_solar_panel");
        registerTileEntity(TileEntitySolarPanelPhotonic.class, "photonic_solar_panel");
        registerTileEntity(TileEntitySolarPanelNeutron.class, "neutron_solar_panel");

        registerTileEntity(TileEntitySolarStationAdv.class, "advanced_solar_station");
        registerTileEntity(TileEntitySolarStationHybrid.class, "hybrid_solar_station");
        registerTileEntity(TileEntitySolarStationPerfectHybrid.class, "perfecthybrid_solar_station");
        registerTileEntity(TileEntitySolarStationQuantum.class, "quantum_solar_station");
        registerTileEntity(TileEntitySolarStationSpectral.class, "spectral_solar_station");
        registerTileEntity(TileEntitySolarStationProtonic.class, "protonic_solar_station");
        registerTileEntity(TileEntitySolarStationSingular.class, "singular_solar_station");
        registerTileEntity(TileEntitySolarStationDiffractive.class, "diffractive_solar_station");
        registerTileEntity(TileEntitySolarStationPhotonic.class, "photonic_solar_station");
        registerTileEntity(TileEntitySolarStationNeutron.class, "neutron_solar_station");

        registerTileEntity(TileEntityEcoUniversalCable.class, "universal_cable");
        registerTileEntity(TileEntityEcoMechanicalPipe.class, "mechanical_pipe");
        registerTileEntity(TileEntityEcoPressurizedTube.class, "pressurized_tube");
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()) {
            registerTileEntity(TileEntitySolarPanelCrystal.class, "crystal_solar_panel");
            registerTileEntity(TileEntitySolarPanelNeutronium.class, "neutronium_solar_panel");
            registerTileEntity(TileEntitySolarPanelInfinity.class, "infinity_solar_panel");
            registerTileEntity(TileEntitySolarStationCrystal.class, "crystal_solar_station");
            registerTileEntity(TileEntitySolarStationNeutronium.class, "neutronium_solar_station");
            registerTileEntity(TileEntitySolarStationInfinity.class, "infinity_solar_station");
        }
    }

    public void registerTESRs() {
    }

    public void registerItemRenders() {
    }

    public void registerBlockRenders() {
    }

    public void preInit() {
    }

    public void loadConfiguration() {
        EcoConfig.local().generators.load(EcoEnergistics.configuration);
        EcoConfig.local().Storage.load(EcoEnergistics.configuration);
        EcoConfig.local().integration.load(EcoEnergistics.configurationIntegration);
        if (EcoEnergistics.configuration.hasChanged()) {
            EcoEnergistics.configuration.save();
        }
        if (EcoEnergistics.configurationIntegration.hasChanged()) {
            EcoEnergistics.configurationIntegration.save();
        }
    }

    public void init() {
        MinecraftForge.EVENT_BUS.register(EcoEnergistics.worldTickHandler);
    }

    @Override
    public Object getClientGui(int ID, EntityPlayer player, World world, BlockPos pos) {
        return null;
    }

    @Override
    public Container getServerGui(int ID, EntityPlayer player, World world, BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        Mekanism.logger.info(
                "[Eco GUI] Server GUI request: id={}, pos={}, tile={}, invalid={}",
                ID, pos, tileEntity == null ? "null" : tileEntity.getClass().getSimpleName(),
                tileEntity != null && tileEntity.isInvalid()
        );
        return switch (ID) {
            case 0 -> tileEntity instanceof TileEntityEcoSolarPanel
                    ? new ContainerEcoSolarGenerator(player.inventory, (TileEntityEcoSolarPanel) tileEntity)
                    : null;
            default -> null;
        };
    }

}
