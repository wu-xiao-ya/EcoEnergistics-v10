package aeternal.ecoenergistics.common.block.states;

import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsBlocks;
import aeternal.ecoenergistics.common.block.BlockEcoGenerator;
import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.tile.panelsolar.PanelSloarConfig;
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
import aeternal.ecoenergistics.common.tile.stationsolar.StationSolarConfig;
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
import mekanism.common.base.IBlockType;
import mekanism.common.block.states.BlockStateFacing;
import mekanism.common.block.states.BlockStateUtils;
import mekanism.common.util.LangUtils;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMapperBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumFacing.Plane;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class BlockStateEcoGenerator extends ExtendedBlockState {

    public static final PropertyBool activeProperty = PropertyBool.create("active");

    public BlockStateEcoGenerator(BlockEcoGenerator block, PropertyEnum<?> typeProperty) {
        super(block, new IProperty[]{BlockStateFacing.facingProperty, typeProperty, activeProperty}, new IUnlistedProperty[]{});
    }

    public enum EcoGeneratorBlock {
        GENERATOR_BLOCK_ECO,
        GENERATOR_BLOCK_ECO2,
        GENERATOR_BLOCK_AVARITIA;

        PropertyEnum<EcoGeneratorType> generatorTypeProperty;

        public PropertyEnum<EcoGeneratorType> getProperty() {
            if (generatorTypeProperty == null) {
                generatorTypeProperty = PropertyEnum.create("type", EcoGeneratorType.class, input -> input != null && input.blockType == this && input.isValidMachine());
            }
            return generatorTypeProperty;
        }

        public Block getBlock() {
            return switch (this) {
                case GENERATOR_BLOCK_ECO -> EcoEnergisticsBlocks.EcoGenerator;
                case GENERATOR_BLOCK_ECO2 -> EcoEnergisticsBlocks.EcoGeneratorAdd;
                case GENERATOR_BLOCK_AVARITIA -> EcoEnergisticsBlocks.AvaritiaGenerator;
            };
        }
    }

    public enum EcoGeneratorType implements IStringSerializable, IBlockType {
        SOLAR_PANEL_ADVANCED(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 0, "SolarPanelAdvanced", 0, 192000, TileEntitySolarPanelAdvanced::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_HYBRID(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 1, "SolarPanelHybrid", 0, 384000, TileEntitySolarPanelHybrid::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_PERFECTHYBRID(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 2, "SolarPanelPerfectHybrid", 0, 768000, TileEntitySolarPanelPerfectHybrid::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_QUANTUM(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 3, "SolarPanelQuantum", 0, 1536000, TileEntitySolarPanelQuantum::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_SPECTRAL(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 4, "SolarPanelSpectral", 0, 3072000, TileEntitySolarPanelSpectral::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_PROTONIC(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 5, "SolarPanelProtonic", 0, 6144000, TileEntitySolarPanelProtonic::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_SINGULAR(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 6, "SolarPanelSingular", 0, 12288000, TileEntitySolarPanelSingular::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_DIFFRACTIVE(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 7, "SolarPanelDiffractive", 0, 24576000, TileEntitySolarPanelDiffractive::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_PHOTONIC(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 8, "SolarPanelPhotonic", 0, 49152000, TileEntitySolarPanelPhotonic::new, true, Plane.HORIZONTAL, false),
        SOLAR_PANEL_NEUTRON(EcoGeneratorBlock.GENERATOR_BLOCK_ECO, 9, "SolarPanelNeutron", 0, 58982000, TileEntitySolarPanelNeutron::new, true, Plane.HORIZONTAL, false),

        SOLAR_STATION_ADVANCED(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 0, "SolarStationAdvanced", 0, 800000, TileEntitySolarStationAdv::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_HYBRID(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 1, "SolarStationHybrid", 0, 2400000, TileEntitySolarStationHybrid::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_PERFECTHYBRID(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 2, "SolarStationPerfectHybrid", 0, 7200000, TileEntitySolarStationPerfectHybrid::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_QUANTUM(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 3, "SolarStationQuantum", 0, 21600000, TileEntitySolarStationQuantum::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_SPECTRAL(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 4, "SolarStationSpectral", 0, 6480000, TileEntitySolarStationSpectral::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_PROTONIC(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 5, "SolarStationProtonic", 0, 8640000, TileEntitySolarStationProtonic::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_SINGULAR(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 6, "SolarStationSingular", 0, 34560000, TileEntitySolarStationSingular::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_DIFFRACTIVE(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 7, "SolarStationDiffractive", 0, 138240000, TileEntitySolarStationDiffractive::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_PHOTONIC(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 8, "SolarStationPhotonic", 0, 552960000, TileEntitySolarStationPhotonic::new, true, Plane.HORIZONTAL, false, true),
        SOLAR_STATION_NEUTRON(EcoGeneratorBlock.GENERATOR_BLOCK_ECO2, 9, "SolarStationNeutron", 0, 663552000, TileEntitySolarStationNeutron::new, true, Plane.HORIZONTAL, false, true),

        AVARITIA_SOLAR_PANEL_CRYSTAL(EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA, 0, "SolarPanelCrystal", 0, 5320000, TileEntitySolarPanelCrystal::new, true, Plane.HORIZONTAL, false),
        AVARITIA_SOLAR_PANEL_NEUTRON(EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA, 1, "SolarPanelNeutronium", 0, 212800000, TileEntitySolarPanelNeutronium::new, true, Plane.HORIZONTAL, false),
        AVARITIA_SOLAR_PANEL_INFINITY(EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA, 2, "SolarPanelInfinity", 0, 1119888000, TileEntitySolarPanelInfinity::new, true, Plane.HORIZONTAL, false),
        AVARITIA_SOLAR_STATION_CRYSTAL(EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA, 3, "SolarStationCrystal", 0, 21280000, TileEntitySolarStationCrystal::new, true, Plane.HORIZONTAL, false, true),
        AVARITIA_SOLAR_STATION_NEUTRON(EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA, 4, "SolarStationNeutronium", 0, 851200000, TileEntitySolarStationNeutronium::new, true, Plane.HORIZONTAL, false, true),
        AVARITIA_SOLAR_STATION_INFINITY(EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA, 5, "SolarStationInfinity", 0, Integer.MAX_VALUE, TileEntitySolarStationInfinity::new, true, Plane.HORIZONTAL, false, true);

        public EcoGeneratorBlock blockType;
        public int meta;
        public String blockName;
        public int guiId;
        public double maxEnergy;
        public Supplier<TileEntity> tileEntitySupplier;
        public boolean hasModel;
        public Predicate<EnumFacing> facingPredicate;
        public boolean activable;
        public boolean hasRedstoneOutput;
        public boolean isStation;

        EcoGeneratorType(EcoGeneratorBlock block, int m, String name, int gui, double energy, Supplier<TileEntity> tileClass, boolean model, Predicate<EnumFacing> predicate,
                         boolean hasActiveTexture) {
            this(block, m, name, gui, energy, tileClass, model, predicate, hasActiveTexture, false, false);
        }

        EcoGeneratorType(EcoGeneratorBlock block, int m, String name, int gui, double energy, Supplier<TileEntity> tileClass, boolean model, Predicate<EnumFacing> predicate,
                         boolean hasActiveTexture, boolean isStation) {
            this(block, m, name, gui, energy, tileClass, model, predicate, hasActiveTexture, false, isStation);
        }

        EcoGeneratorType(EcoGeneratorBlock block, int m, String name, int gui, double energy, Supplier<TileEntity> tileClass, boolean model, Predicate<EnumFacing> predicate,
                         boolean hasActiveTexture, boolean hasRedstoneOutput, boolean isStation) {
            blockType = block;
            meta = m;
            blockName = name;
            guiId = gui;
            maxEnergy = energy;
            tileEntitySupplier = tileClass;
            hasModel = model;
            facingPredicate = predicate;
            activable = hasActiveTexture;
            this.hasRedstoneOutput = hasRedstoneOutput;
            this.isStation = isStation;
        }

        private static final List<EcoGeneratorType> VALID_MACHINES = new ArrayList<>();

        static {
            Arrays.stream(EcoGeneratorType.values()).filter(EcoGeneratorType::isValidMachine).forEach(VALID_MACHINES::add);
        }

        public static List<EcoGeneratorType> getValidMachines() {
            return VALID_MACHINES;
        }


        public boolean isValidMachine() {
            return true;
        }

        public static EcoGeneratorType get(IBlockState state) {
            if (state.getBlock() instanceof BlockEcoGenerator) {
                return state.getValue(((BlockEcoGenerator) state.getBlock()).getTypeProperty());
            }
            return null;
        }

        public static EcoGeneratorType get(Block block, int meta) {
            if (block instanceof BlockEcoGenerator) {
                return get(((BlockEcoGenerator) block).getGeneratorBlock(), meta);
            }
            return null;
        }

        public static EcoGeneratorType get(EcoGeneratorBlock block, int meta) {
            for (EcoGeneratorType type : values()) {
                if (type.meta == meta && type.blockType == block) {
                    return type;
                }
            }
            return null;
        }

        public static EcoGeneratorType get(ItemStack stack) {
            return get(Block.getBlockFromItem(stack.getItem()), stack.getItemDamage());
        }

        public double getOutput() {
            return switch (this) {
                case SOLAR_PANEL_ADVANCED -> PanelSloarConfig.AdvancedGen_OUT.getValue();
                case SOLAR_PANEL_HYBRID -> PanelSloarConfig.HybridGen_OUT.getValue();
                case SOLAR_PANEL_PERFECTHYBRID -> PanelSloarConfig.PerfectHybridGen_OUT.getValue();
                case SOLAR_PANEL_QUANTUM -> PanelSloarConfig.QuantumGen_OUT.getValue();
                case SOLAR_PANEL_SPECTRAL -> PanelSloarConfig.SpectralGen_OUT.getValue();
                case SOLAR_PANEL_PROTONIC -> PanelSloarConfig.ProtonicGen_OUT.getValue();
                case SOLAR_PANEL_SINGULAR -> PanelSloarConfig.SingularGen_OUT.getValue();
                case SOLAR_PANEL_DIFFRACTIVE -> PanelSloarConfig.DiffractiveGen_OUT.getValue();
                case SOLAR_PANEL_PHOTONIC -> PanelSloarConfig.PhotonicGen_OUT.getValue();
                case SOLAR_PANEL_NEUTRON -> PanelSloarConfig.NeutronGen_OUT.getValue();
                case SOLAR_STATION_ADVANCED ->StationSolarConfig.AdvancedGen_OUT.getValue();
                case SOLAR_STATION_HYBRID -> StationSolarConfig.HybridGen_OUT.getValue();
                case SOLAR_STATION_PERFECTHYBRID -> StationSolarConfig.PerfectHybridGen_OUT.getValue();
                case SOLAR_STATION_QUANTUM -> StationSolarConfig.QuantumGen_OUT.getValue();
                case SOLAR_STATION_SPECTRAL -> StationSolarConfig.SpectralGen_OUT.getValue();
                case SOLAR_STATION_PROTONIC -> StationSolarConfig.ProtonicGen_OUT.getValue();
                case SOLAR_STATION_SINGULAR -> StationSolarConfig.SingularGen_OUT.getValue();
                case SOLAR_STATION_DIFFRACTIVE -> StationSolarConfig.DiffractiveGen_OUT.getValue();
                case SOLAR_STATION_PHOTONIC -> StationSolarConfig.PhotonicGen_OUT.getValue();
                case SOLAR_STATION_NEUTRON -> StationSolarConfig.NeutronGen_OUT.getValue();
                case AVARITIA_SOLAR_PANEL_CRYSTAL -> PanelSloarConfig.CrystalGen_OUT.getValue();
                case AVARITIA_SOLAR_PANEL_NEUTRON -> PanelSloarConfig.NeutroniumGen_OUT.getValue();
                case AVARITIA_SOLAR_PANEL_INFINITY -> PanelSloarConfig.InfinityGen_OUT.getValue();
                case AVARITIA_SOLAR_STATION_CRYSTAL -> StationSolarConfig.CrystalGen_OUT.getValue();
                case AVARITIA_SOLAR_STATION_NEUTRON -> StationSolarConfig.NeutroniumGen_OUT.getValue();
                case AVARITIA_SOLAR_STATION_INFINITY -> StationSolarConfig.InfinityGen_OUT.getValue();
            };
        }

        @Override
        public String getBlockName() {
            return blockName;
        }

        @Override
        public boolean isEnabled() {
            return EcoConfig.current().generators.EcoMachinesManager.isEnabled(this);
        }

        public TileEntity create() {
            return this.tileEntitySupplier != null ? this.tileEntitySupplier.get() : null;
        }

        @Override
        public String getName() {
            return name().toLowerCase(Locale.ROOT);
        }

        public String getDescription() {
            return LangUtils.localize("tooltip." + blockName);
        }

        public ItemStack getStack() {
            return new ItemStack(blockType.getBlock(), 1, meta);
        }

        public boolean canRotateTo(EnumFacing side) {
            return facingPredicate.test(side);
        }

        public boolean hasRotations() {
            return !facingPredicate.equals(BlockStateUtils.NO_ROTATION);
        }

        public boolean hasActiveTexture() {
            return activable;
        }
    }


    public static class GeneratorBlockStateMapper extends StateMapperBase {

        @Nonnull
        @Override
        protected ModelResourceLocation getModelResourceLocation(@Nonnull IBlockState state) {
            BlockEcoGenerator block = (BlockEcoGenerator) state.getBlock();
            EcoGeneratorType type = state.getValue(block.getTypeProperty());
            StringBuilder builder = new StringBuilder();
            String nameOverride = null;

            if (type.hasActiveTexture()) {
                builder.append(activeProperty.getName());
                builder.append("=");
                builder.append(state.getValue(activeProperty));
            }

            if (type.hasRotations()) {
                EnumFacing facing = state.getValue(BlockStateFacing.facingProperty);
                if (!type.canRotateTo(facing)) {
                    facing = EnumFacing.NORTH;
                }
                if (builder.length() > 0) {
                    builder.append(",");
                }
                builder.append(BlockStateFacing.facingProperty.getName());
                builder.append("=");
                builder.append(facing.getName());
            }

            if (builder.length() == 0) {
                builder.append("normal");
            }
            ResourceLocation baseLocation = new ResourceLocation(EcoEnergistics.MOD_ID, nameOverride != null ? nameOverride : type.getName());

            return new ModelResourceLocation(baseLocation, builder.toString());
        }
    }
}
