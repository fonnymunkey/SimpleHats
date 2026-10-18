package fonnymunkey.simplehats.item;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.handlers.ForgeConfigHandler;
import fonnymunkey.simplehats.handlers.ModRegistry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BagItem extends Item {

    private final HatSeason hatSeason;
    private final EnumRarity rarity;
    private final List<HatWeight> availableHats = new ArrayList<>();

    public BagItem(String name, EnumRarity rarity, HatSeason hatSeason) {
        this.rarity = rarity;
        this.hatSeason = hatSeason;
        this.setRegistryName(SimpleHats.MODID, name);
        this.setTranslationKey(SimpleHats.MODID + "." + name);
        this.setCreativeTab(ModRegistry.HAT_TAB);
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.rarity;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 1.0F + (world.rand.nextFloat() - world.rand.nextFloat()) * 0.4F);
        stack.shrink(1);

        if(!world.isRemote) {
            HatSeason season = HatSeason.getSeason();
            if(this.hatSeason == HatSeason.NONE && season != HatSeason.NONE && world.rand.nextFloat() < ForgeConfigHandler.server.seasonalBagChance) {
                Item bag = getSeasonalBag(season);
                if(bag != null) player.entityDropItem(new ItemStack(bag), 0.0F);
            }
            Item hat = this.getBagResult(world);
            if(hat != null) player.entityDropItem(new ItemStack(hat), 0.0F);
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, stack);
    }

    private static Item getSeasonalBag(HatSeason season) {
        switch(season) {
            case EASTER: return ModRegistry.HATBAG_EASTER;
            case SUMMER: return ModRegistry.HATBAG_SUMMER;
            case HALLOWEEN: return ModRegistry.HATBAG_HALLOWEEN;
            case FESTIVE: return ModRegistry.HATBAG_FESTIVE;
            default: return null;
        }
    }

    private Item getBagResult(World world) {
        if(this.availableHats.isEmpty()) {
            for(HatItem hat : ModRegistry.HAT_LIST) {
                if((hat.getHatEntry().getHatRarity() == this.rarity || this.hatSeason != HatSeason.NONE) &&
                        hat.getHatEntry().getHatWeight() != 0 &&
                        hat.getHatEntry().getHatSeason() == this.hatSeason) {
                    this.availableHats.add(new HatWeight(hat));
                }
            }
            if(this.availableHats.isEmpty()) {
                SimpleHats.LOGGER.error("Failed to populate " + this.getRegistryName() + " loot list.");
                return null;
            }
        }
        return WeightedRandom.getRandomItem(world.rand, this.availableHats).hat;
    }

    private static class HatWeight extends WeightedRandom.Item {
        private final HatItem hat;

        private HatWeight(HatItem hat) {
            super(hat.getHatEntry().getHatWeight());
            this.hat = hat;
        }
    }
}
