package aeternal.ecoenergistics.common;

import aeternal.ecoenergistics.common.block.BlockBasic;
import aeternal.ecoenergistics.common.block.BlockEcoGenerator;
import aeternal.ecoenergistics.common.block.BlockEcoTransmitter;
import aeternal.ecoenergistics.common.block.BlockOre;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoGenerator;
import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.item.ItemBlockBasic;
import aeternal.ecoenergistics.common.item.ItemBlockEcoGenerator;
import aeternal.ecoenergistics.common.item.ItemBlockEcoTransmitter;
import aeternal.ecoenergistics.common.item.ItemBlockOre;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

@GameRegistry.ObjectHolder(EcoEnergistics.MOD_ID)
public class EcoEnergisticsBlocks {

    public static final Block EcoGenerator = BlockEcoGenerator.getGeneratorBlock(BlockStateEcoGenerator.EcoGeneratorBlock.GENERATOR_BLOCK_ECO);
    public static final Block EcoGeneratorAdd = BlockEcoGenerator.getGeneratorBlock(BlockStateEcoGenerator.EcoGeneratorBlock.GENERATOR_BLOCK_ECO2);
    public static final Block AvaritiaGenerator = BlockEcoGenerator.getGeneratorBlock(BlockStateEcoGenerator.EcoGeneratorBlock.GENERATOR_BLOCK_AVARITIA);
    public static Block EcoTransmitter = new BlockEcoTransmitter();
    public static Block EcoOreBlock = new BlockOre();
    public static Block EcoBlockBasic = new BlockBasic();

    public static void registerBlocks(IForgeRegistry<Block> registry) {
        registry.register(init(EcoGenerator, "EcoGenerator"));
        registry.register(init(EcoGeneratorAdd, "EcoGeneratorAdd"));
        registry.register(init(EcoTransmitter, "EcoTransmitter"));
        registry.register(init(EcoOreBlock, "OreBlock"));
        registry.register(init(EcoBlockBasic, "BlockBasic"));
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()) {
            registry.register(init(AvaritiaGenerator, "AvaritiaGenerator"));
        }
    }

    public static void registerItemBlocks(IForgeRegistry<Item> registry) {
        registry.register(EcoEnergisticsItems.init(new ItemBlockEcoGenerator(EcoGenerator), "EcoGenerator"));
        registry.register(EcoEnergisticsItems.init(new ItemBlockEcoGenerator(EcoGeneratorAdd), "EcoGeneratorAdd"));
        registry.register(EcoEnergisticsItems.init(new ItemBlockEcoTransmitter(EcoTransmitter), "EcoTransmitter"));
        registry.register(EcoEnergisticsItems.init(new ItemBlockOre(EcoOreBlock), "OreBlock"));
        registry.register(EcoEnergisticsItems.init(new ItemBlockBasic(EcoBlockBasic), "BlockBasic"));
        if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()) {
            registry.register(EcoEnergisticsItems.init(new ItemBlockEcoGenerator(AvaritiaGenerator), "AvaritiaGenerator"));
        }

    }

    public static Block init(Block block, String name) {
        return block.setTranslationKey(name).setRegistryName(new ResourceLocation(EcoEnergistics.MOD_ID, name));
    }
}
