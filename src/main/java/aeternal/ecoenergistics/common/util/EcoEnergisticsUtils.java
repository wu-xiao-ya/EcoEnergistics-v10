package aeternal.ecoenergistics.common.util;

import aeternal.ecoenergistics.common.EcoEnergistics;
import aeternal.ecoenergistics.common.EcoEnergisticsBlocks;
import aeternal.ecoenergistics.common.block.states.BlockStateEcoTransmitter;
import aeternal.ecoenergistics.common.item.ItemBlockEcoTransmitter;
import aeternal.ecoenergistics.common.tier.MEETiers;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public class EcoEnergisticsUtils {

    public static ResourceLocation getResource(ResourceType type, String name) {
        return new ResourceLocation(EcoEnergistics.MOD_ID, (type.getPrefix() + name).toLowerCase(Locale.ROOT));
    }


    public static ItemStack getEcoTransmitter(BlockStateEcoTransmitter.EcoTransmitterType type, MEETiers tier, int amount) {
        ItemStack stack = new ItemStack(EcoEnergisticsBlocks.EcoTransmitter, amount, type.ordinal());
        ItemBlockEcoTransmitter itemTransmitter = (ItemBlockEcoTransmitter) stack.getItem();
        itemTransmitter.setBaseTier(stack, tier);
        return stack;
    }



    public enum ResourceType {
        GUI("gui"),
        GUI_ELEMENT("gui/elements"),
        SOUND("sound"),
        RENDER("render"),
        TEXTURE_BLOCKS("textures/blocks"),
        TEXTURE_ITEMS("textures/items"),
        MODEL("models"),
        INFUSE("infuse");

        private String prefix;

        ResourceType(String s) {
            prefix = s;
        }

        public String getPrefix() {
            return prefix + "/";
        }
    }
}
