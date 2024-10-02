package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DataPool;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.util.math.intprovider.IntProvider;
import net.minecraft.util.math.intprovider.WeightedListIntProvider;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;

public class BagItem extends Item {

    private HatSeason hatSeason = HatSeason.NONE;
    private boolean seasonal = false;
    private Rarity rarity = Rarity.COMMON; //Don't let enchantment glint artificially change hat results
    private List<HatItem> availableHatList = new ArrayList<>();
    private WeightedListIntProvider availableHatListWeighted = null;

    public BagItem(Rarity rarity) {
        super(new Item.Settings()
                .rarity(rarity));
                //.group(SimpleHats.HAT_TAB));
        this.rarity = rarity;
    }

    public BagItem(HatEntry.HatSeason hatSeason) {
        super(new Item.Settings()
                .rarity(Rarity.EPIC));
                //.group(SimpleHats.HAT_TAB));
        this.hatSeason = hatSeason;
        this.seasonal = true;
        this.rarity = Rarity.EPIC;
    }

    public static SoundEvent getUnwrapFinishSound() { return SoundEvents.ITEM_ARMOR_EQUIP_GENERIC.value(); }

    @Override
    public TypedActionResult<ItemStack> use(World level, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        player.playSound(getUnwrapFinishSound(), 1.0F, 1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.4F);
        itemStack.decrement(1);

        if(!level.isClient()) {
            if(!this.seasonal && HatSeason.getSeason() != HatSeason.NONE) {
                if(level.getRandom().nextFloat()*100.0F < SimpleHats.config.common.seasonalBagChance) {
                    player.dropItem(getSeasonalBag());
                }
            }
            player.dropItem(this.getBagResult(level));
        }
        return TypedActionResult.success(itemStack, level.isClient());
    }

    private static Item getSeasonalBag() {
        switch(HatSeason.getSeason()) {
            case EASTER -> { return ModRegistry.HATBAG_EASTER; }
            case SUMMER -> { return ModRegistry.HATBAG_SUMMER; }
            case HALLOWEEN -> { return ModRegistry.HATBAG_HALLOWEEN; }
            case FESTIVE -> { return ModRegistry.HATBAG_FESTIVE; }
        }
        SimpleHats.logger.log(org.apache.logging.log4j.Level.ERROR, "Failed to get seasonal bag type.");
        return Items.AIR;
    }

    private ItemConvertible getBagResult(World level) {
        if(this.availableHatList.size() == 0) {
            for(HatItem hat : ModRegistry.hatList) {
                if((hat.getHatEntry().getHatRarity() == this.rarity || this.seasonal) &&
                        hat.getHatEntry().getHatWeight() != 0 &&
                        hat.getHatEntry().getHatSeason() == this.hatSeason) {
                    this.availableHatList.add(hat);
                }
            }
            if(this.availableHatList.size() == 0) {
                SimpleHats.logger.log(org.apache.logging.log4j.Level.ERROR, "Failed to populate " + this.getName() + " loot list.");
                return Items.AIR;
            }
        }
        if(this.availableHatListWeighted == null) {
            try {
                DataPool.Builder<IntProvider> tempListBuilder = generateSimpleWeightedList(DataPool.<IntProvider>builder().add(ConstantIntProvider.create(0), this.availableHatList.get(0).getHatEntry().getHatWeight()), 1);
                this.availableHatListWeighted = new WeightedListIntProvider(tempListBuilder.build());
            }
            catch(Exception ex) {
                SimpleHats.logger.log(org.apache.logging.log4j.Level.ERROR, "Failed to generate " + this.getName() + " weighted loot table: " + ex);
                return Items.AIR;
            }
        }
        return this.availableHatList.get(this.availableHatListWeighted.get(level.random));
    }

    //Nasty, nasty recursion
    private DataPool.Builder<IntProvider> generateSimpleWeightedList(DataPool.Builder<IntProvider> list, int i) {
        if(i<this.availableHatList.size()) {
            list.add(ConstantIntProvider.create(i), this.availableHatList.get(i).getHatEntry().getHatWeight());
            i++;
            list = generateSimpleWeightedList(list, i);
        }
        return list;
    }
}

