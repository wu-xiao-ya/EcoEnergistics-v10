package aeternal.ecoenergistics.common.recipes;


import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsItems;
import aeternal.ecoenergistics.common.Infusers;
import aeternal.ecoenergistics.common.config.EcoConfig;
import aeternal.ecoenergistics.common.enums.Ingot;
import mekanism.api.infuse.InfuseRegistry;
import mekanism.api.infuse.InfuseType;
import mekanism.common.MekanismItems;
import mekanism.common.block.states.BlockStateMachine;
import mekanism.common.config.MekanismConfig;
import mekanism.common.recipe.RecipeHandler;
import net.minecraft.item.ItemStack;

public class Infuser {
    public static void InitCustomInfuserRecipes() {
        if (MekanismConfig.current().general.machinesManager.isEnabled(BlockStateMachine.MachineType.METALLURGIC_INFUSER)) {
            InfuseType diamond = InfuseRegistry.get("DIAMOND");
            InfuseType obsidian = InfuseRegistry.get("OBSIDIAN");
            InfuseType redstone = InfuseRegistry.get("REDSTONE");

            ItemStack advancedAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 0);
            ItemStack hybridAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 1);
            ItemStack perfecthybridAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 2);
            ItemStack quantumAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 3);
            ItemStack spectralAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 4);
            ItemStack protonicAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 5);
            ItemStack singularAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 6);
            ItemStack diffractiveAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 7);
            ItemStack photonicAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 8);
            ItemStack neutronAlloy = new ItemStack(EcoEnergisticsItems.MoreAlloy, 1, 9);
            ItemStack activeGlowstoneIngot = new ItemStack(EcoEnergisticsItems.MoreIngot, 1, Ingot.ACTIVATEDGLOWSTONE.ordinal());
            ItemStack glowstoneIngot = new ItemStack(MekanismItems.Ingot, 1, 3);

            ItemStack lithiumDust = new ItemStack(MekanismItems.OtherDust, 1, 4);
            ItemStack refinedlithiumDust = new ItemStack(EcoEnergisticsItems.MoreDust, 1, 3);

            ItemStack hdperod = new ItemStack(MekanismItems.HDPE_ROD, 1);
            ItemStack steelrod = new ItemStack(EcoEnergisticsItems.MoreRod, 1, 0);

            addRecipe(Infusers.glowstone, 20, new ItemStack(MekanismItems.ReinforcedAlloy), advancedAlloy);
            addRecipe(diamond, 20, advancedAlloy, hybridAlloy);
            addRecipe(Infusers.lapis, 20, hybridAlloy, perfecthybridAlloy);
            addRecipe(Infusers.emerald, 20, perfecthybridAlloy, quantumAlloy);
            addRecipe(Infusers.gold, 30, quantumAlloy, spectralAlloy);
            addRecipe(obsidian, 20, spectralAlloy, protonicAlloy);
            addRecipe(Infusers.glowstone, 20, protonicAlloy, singularAlloy);
            addRecipe(redstone, 50, singularAlloy, diffractiveAlloy);
            addRecipe(Infusers.emerald, 50, diffractiveAlloy, photonicAlloy);
            addRecipe(obsidian, 50, photonicAlloy, neutronAlloy);

            addRecipe(Infusers.steel, 50, hdperod, steelrod);
            addRecipe(redstone, 15, glowstoneIngot, activeGlowstoneIngot);
            addRecipe(Infusers.iridium, 20, lithiumDust, refinedlithiumDust);

            if (EcoEnergistics.hooks.AvaritiaLoaded && EcoConfig.current().integration.AvaritiaEnable.val()){
                ItemStack crystalAlloy = new ItemStack(EcoEnergisticsItems.AlloyAvaritia, 1, 0);
                ItemStack neutroniumAlloy = new ItemStack(EcoEnergisticsItems.AlloyAvaritia, 1, 1);
                ItemStack infinityAlloy = new ItemStack(EcoEnergisticsItems.AlloyAvaritia, 1, 2);

                addRecipe(InfuseRegistry.get("CRYSTAL"), 80, neutronAlloy, crystalAlloy);
                addRecipe(InfuseRegistry.get("NEUTRONIUM"), 160, crystalAlloy, neutroniumAlloy);
                addRecipe(InfuseRegistry.get("INFINITY"), 10, neutroniumAlloy, infinityAlloy);
            }

        }
    }

    private static void addRecipe(InfuseType infuseType, int amount, ItemStack input, ItemStack output) {
        if (infuseType != null) {
            RecipeHandler.addMetallurgicInfuserRecipe(infuseType, amount, input, output);
        }
    }
}
