package aeternal.ecoenergistics.client.render.item;

import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelAdvItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelCrystalItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelDiffractiveItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelHybridItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelInfinityItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelNeutronItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelNeutroniumItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelPerfectHybridItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelPhotonicItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelProtonicItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelQuantumItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelSingularItem;
import aeternal.ecoenergistics.client.render.item.panelsolar.RenderSolarPanelSpectralItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationAdvItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationCrystalItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationDiffractiveItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationHybridItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationInfinityItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationNeutronItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationNeutroniumItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationPerfectHybridItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationPhotonicItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationProtonicItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationQuantumItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationSingularItem;
import aeternal.ecoenergistics.client.render.item.stationsolar.RenderSolarStationSpectralItem;
import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator.EcoGeneratorType;
import aeternal.ecoenergistics.common.config.EcoConfig;
import mekanism.client.render.item.ItemLayerWrapper;
import mekanism.client.render.item.SubTypeItemRenderer;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.Map;

@SideOnly(Side.CLIENT)
public class RenderEcoGeneratorItem extends SubTypeItemRenderer<EcoGeneratorType> {

    public static Map<EcoGeneratorType, ItemLayerWrapper> modelMap = new EnumMap<>(EcoGeneratorType.class);

    @Override
    protected boolean earlyExit() {
        return true;
    }


    @Override
    protected void renderBlockSpecific(@Nonnull ItemStack stack, ItemCameraTransforms.TransformType transformType) {
        EcoGeneratorType type = EcoGeneratorType.get(stack);
        if (type != null) {
            if (type == EcoGeneratorType.SOLAR_PANEL_ADVANCED) {
                RenderSolarPanelAdvItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_HYBRID) {
                RenderSolarPanelHybridItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_PERFECTHYBRID) {
                RenderSolarPanelPerfectHybridItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_QUANTUM) {
                RenderSolarPanelQuantumItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_SPECTRAL) {
                RenderSolarPanelSpectralItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_PROTONIC) {
                RenderSolarPanelProtonicItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_SINGULAR) {
                RenderSolarPanelSingularItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_DIFFRACTIVE) {
                RenderSolarPanelDiffractiveItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_PHOTONIC) {
                RenderSolarPanelPhotonicItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_PANEL_NEUTRON) {
                RenderSolarPanelNeutronItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_ADVANCED) {
                RenderSolarStationAdvItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_HYBRID) {
                RenderSolarStationHybridItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_PERFECTHYBRID) {
                RenderSolarStationPerfectHybridItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_QUANTUM) {
                RenderSolarStationQuantumItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_SPECTRAL) {
                RenderSolarStationSpectralItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_PROTONIC) {
                RenderSolarStationProtonicItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_SINGULAR) {
                RenderSolarStationSingularItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_DIFFRACTIVE) {
                RenderSolarStationDiffractiveItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_PHOTONIC) {
                RenderSolarStationPhotonicItem.renderStack(stack, transformType);
            } else if (type == EcoGeneratorType.SOLAR_STATION_NEUTRON) {
                RenderSolarStationNeutronItem.renderStack(stack, transformType);
            }
            if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()){
                if (type == EcoGeneratorType.AVARITIA_SOLAR_PANEL_CRYSTAL) {
                    RenderSolarPanelCrystalItem.renderStack(stack, transformType);
                }else if (type == EcoGeneratorType.AVARITIA_SOLAR_PANEL_NEUTRON) {
                    RenderSolarPanelNeutroniumItem.renderStack(stack, transformType);
                }else if (type == EcoGeneratorType.AVARITIA_SOLAR_PANEL_INFINITY) {
                    RenderSolarPanelInfinityItem.renderStack(stack, transformType);
                }else if (type == EcoGeneratorType.AVARITIA_SOLAR_STATION_CRYSTAL) {
                    RenderSolarStationCrystalItem.renderStack(stack, transformType);
                }else if (type == EcoGeneratorType.AVARITIA_SOLAR_STATION_NEUTRON) {
                    RenderSolarStationNeutroniumItem.renderStack(stack, transformType);
                }else if (type == EcoGeneratorType.AVARITIA_SOLAR_STATION_INFINITY) {
                    RenderSolarStationInfinityItem.renderStack(stack, transformType);
                }
            }
        }
    }

    @Override
    protected void renderItemSpecific(@Nonnull ItemStack stack, ItemCameraTransforms.TransformType transformType) {
    }

    @Nullable
    @Override
    protected ItemLayerWrapper getModel(EcoGeneratorType type) {
        return modelMap.get(type);
    }

    @Nullable
    @Override
    protected EcoGeneratorType getType(@Nonnull ItemStack stack) {
        return EcoGeneratorType.get(stack);
    }
}

