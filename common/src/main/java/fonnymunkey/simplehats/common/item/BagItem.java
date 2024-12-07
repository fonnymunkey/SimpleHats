package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class BagItem extends Item {

    private HatSeason hatSeason = HatSeason.NONE;
    private boolean seasonal = false;
    private Rarity rarity = Rarity.COMMON; //Don't let enchantment glint artificially change hat results
    private final List<HatItem> availableHatList = new ArrayList<>();
    private WeightedListInt availableHatListWeighted = null;

    public BagItem(Rarity rarity) {
        super(new Item.Properties().rarity(rarity));
        this.rarity = rarity;
    }

    public BagItem(HatEntry.HatSeason hatSeason) {
        super(new Item.Properties().rarity(Rarity.EPIC));
        this.hatSeason = hatSeason;
        this.seasonal = true;
        this.rarity = Rarity.EPIC;
    }

    public static SoundEvent getUnwrapFinishSound() {
        return SoundEvents.ARMOR_EQUIP_GENERIC.value();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack itemStack = player.getItemInHand(usedHand);
        player.playSound(getUnwrapFinishSound(), 1.0F, 1.0F + (level.getRandom().nextFloat() - level.getRandom().nextFloat()) * 0.4F);

        if(!level.isClientSide()) {
            if(!this.seasonal && HatSeason.getSeason() != HatSeason.NONE) {
                if(level.getRandom().nextFloat()*100.0F < SimpleHatsConfigAbstract.seasonalBagChance()) {
                    Item item = getSeasonalBag();
                    if(item != Items.AIR) player.spawnAtLocation(item);
                }
            }
            Item item = this.getBagResult(level, itemStack);
            if(item != Items.AIR) player.spawnAtLocation(item);
        }
        
        itemStack.shrink(1);
        return InteractionResultHolder.sidedSuccess(itemStack, level.isClientSide());
    }

    private static Item getSeasonalBag() {
        switch(HatSeason.getSeason()) {
            case EASTER -> { return SimpleHatsCommon.MOD_REGISTRY.getHatBagEaster(); }
            case SUMMER -> { return SimpleHatsCommon.MOD_REGISTRY.getHatBagSummer(); }
            case HALLOWEEN -> { return SimpleHatsCommon.MOD_REGISTRY.getHatBagHalloween(); }
            case FESTIVE -> { return SimpleHatsCommon.MOD_REGISTRY.getHatBagFestive(); }
        }
        Constants.LOG.error("Failed to get seasonal bag type.");
        return Items.AIR;
    }

    private Item getBagResult(Level level, ItemStack bagStack) {
        if(this.availableHatList.isEmpty()) {
            for(HatItem hat : SimpleHatsCommon.MOD_REGISTRY.getHatList()) {
                if((hat.getHatEntry().getHatRarity() == this.rarity || this.seasonal) &&
                        hat.getHatEntry().getHatWeight() != 0 &&
                        hat.getHatEntry().getHatSeason() == this.hatSeason) {
                    this.availableHatList.add(hat);
                }
            }
            if(this.availableHatList.isEmpty()) {
                Constants.LOG.error("Failed to populate " + this.getName(bagStack) + " loot list.");
                return Items.AIR;
            }
        }
        if(this.availableHatListWeighted == null) {
            try {
                SimpleWeightedRandomList.Builder<IntProvider> builder = generateSimpleWeightedList(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(0), this.availableHatList.get(0).getHatEntry().getHatWeight()), 1);
                this.availableHatListWeighted = new WeightedListInt(builder.build());
            }
            catch(Exception ex) {
                Constants.LOG.error("Failed to generate " + this.getName(bagStack) + " weighted loot table: " + ex);
                return Items.AIR;
            }
        }
        return this.availableHatList.get(this.availableHatListWeighted.sample(level.getRandom()));
    }

    //Nasty, nasty recursion
    private SimpleWeightedRandomList.Builder<IntProvider> generateSimpleWeightedList(SimpleWeightedRandomList.Builder<IntProvider> list, int i) {
        if(i<this.availableHatList.size()) {
            list.add(ConstantInt.of(i), this.availableHatList.get(i).getHatEntry().getHatWeight());
            i++;
            list = generateSimpleWeightedList(list, i);
        }
        return list;
    }
}