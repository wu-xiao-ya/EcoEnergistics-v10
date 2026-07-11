package aeternal.ecoenergistics.common;

import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.item.ItemAlloyAvaritia;
import aeternal.ecoenergistics.common.item.ItemClump;
import aeternal.ecoenergistics.common.item.ItemCompressed;
import aeternal.ecoenergistics.common.item.ItemCompressedAvaritia;
import aeternal.ecoenergistics.common.item.ItemControlCircuitAvaritia;
import aeternal.ecoenergistics.common.item.ItemCrystal;
import aeternal.ecoenergistics.common.item.ItemDirtyDust;
import aeternal.ecoenergistics.common.item.ItemDust;
import aeternal.ecoenergistics.common.item.ItemDustAvaritia;
import aeternal.ecoenergistics.common.item.ItemEcoEnergized;
import aeternal.ecoenergistics.common.item.ItemIngot;
import aeternal.ecoenergistics.common.item.ItemMoreAlloy;
import aeternal.ecoenergistics.common.item.ItemMoreControlCircuit;
import aeternal.ecoenergistics.common.item.ItemMoreDust;
import aeternal.ecoenergistics.common.item.ItemMoreSolarCells;
import aeternal.ecoenergistics.common.item.ItemNugget;
import aeternal.ecoenergistics.common.item.ItemRods;
import aeternal.ecoenergistics.common.item.ItemShard;
import aeternal.ecoenergistics.common.item.ItemSolarCellsAvaritia;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(EcoEnergistics.MOD_ID)
public class EcoEnergisticsItems {

    public static final Item MoreAlloy = new ItemMoreAlloy();
    public static final Item MoreDust = new ItemMoreDust();
    public static final Item Dust = new ItemDust();
    public static final Item MoreCompressed = new ItemCompressed();
    public static final Item MoreRod = new ItemRods();
    public static final Item MoreIngot = new ItemIngot();
    public static final Item MoreNugget = new ItemNugget();
    public static final Item MoreClump = new ItemClump();
    public static final Item MoreCrystal = new ItemCrystal();
    public static final Item MoreDirtyDust = new ItemDirtyDust();
    public static final Item MoreShard = new ItemShard();
    public static final Item MoreControlCircuit = new ItemMoreControlCircuit();
    public static final Item MoreSolarCell = new ItemMoreSolarCells();

    public static final ItemEcoEnergized EnergyTabletAdvanced = new ItemEcoEnergized(2000000);
    public static final ItemEcoEnergized EnergyTabletHybrid = new ItemEcoEnergized(4000000);
    public static final ItemEcoEnergized EnergyTabletPerfectHybrid = new ItemEcoEnergized(16000000);
    public static final ItemEcoEnergized EnergyTabletQuantum = new ItemEcoEnergized(32000000);
    public static final ItemEcoEnergized EnergyTabletSpectral = new ItemEcoEnergized(64000000);
    public static final ItemEcoEnergized EnergyTabletProtonic = new ItemEcoEnergized(128000000);
    public static final ItemEcoEnergized EnergyTabletSingular = new ItemEcoEnergized(256000000);
    public static final ItemEcoEnergized EnergyTabletDiffractive = new ItemEcoEnergized(512000000);
    public static final ItemEcoEnergized EnergyTabletPhotonic = new ItemEcoEnergized(1024000000);
    public static final ItemEcoEnergized EnergyTabletNeutron = new ItemEcoEnergized(2048000000);

    public static final Item AlloyAvaritia = new ItemAlloyAvaritia();
    public static final Item DustAvaritia = new ItemDustAvaritia();
    public static final Item CompressedAvaritia = new ItemCompressedAvaritia();
    public static final Item SolarCellAvaritia = new ItemSolarCellsAvaritia();
    public static final Item ControlCircuitAvaritia = new ItemControlCircuitAvaritia();

    public static void registerItems(IForgeRegistry<Item> registry) {
        registry.register(init(MoreControlCircuit, "MoreControlCircuit"));
        registry.register(init(MoreAlloy, "MoreAlloy"));
        registry.register(init(MoreDust, "MoreDust"));
        registry.register(init(Dust, "Dust"));
        registry.register(init(MoreCompressed, "MoreCompressed"));
        registry.register(init(MoreRod, "MoreRod"));
        registry.register(init(MoreIngot, "MoreIngot"));
        registry.register(init(MoreNugget, "MoreNugget"));
        registry.register(init(MoreCrystal, "MoreCrystal"));
        registry.register(init(MoreClump, "MoreClump"));
        registry.register(init(MoreShard, "MoreShard"));
        registry.register(init(MoreDirtyDust, "MoreDirtyDust"));
        registry.register(init(MoreSolarCell, "MoreSolarCell"));
        registry.register(init(EnergyTabletAdvanced, "EnergyTabletAdvanced"));
        registry.register(init(EnergyTabletHybrid, "EnergyTabletHybrid"));
        registry.register(init(EnergyTabletPerfectHybrid, "EnergyTabletPerfectHybrid"));
        registry.register(init(EnergyTabletQuantum, "EnergyTabletQuantum"));
        registry.register(init(EnergyTabletSpectral, "EnergyTabletSpectral"));
        registry.register(init(EnergyTabletProtonic, "EnergyTabletProtonic"));
        registry.register(init(EnergyTabletSingular, "EnergyTabletSingular"));
        registry.register(init(EnergyTabletDiffractive, "EnergyTabletDiffractive"));
        registry.register(init(EnergyTabletPhotonic, "EnergyTabletPhotonic"));
        registry.register(init(EnergyTabletNeutron, "EnergyTabletNeutron"));
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()) {
            registry.register(init(ControlCircuitAvaritia, "ControlCircuitAvaritia"));
            registry.register(init(AlloyAvaritia, "AlloyAvaritia"));
            registry.register(init(DustAvaritia, "DustAvaritia"));
            registry.register(init(CompressedAvaritia, "CompressedAvaritia"));
            registry.register(init(SolarCellAvaritia, "SolarCellAvaritia"));
        }

    }

    public static Item init(Item item, String name) {
        return item.setTranslationKey(name).setRegistryName(new ResourceLocation(EcoEnergistics.MOD_ID, name));
    }
}
