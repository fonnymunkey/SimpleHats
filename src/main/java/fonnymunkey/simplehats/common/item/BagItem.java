package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModItems;
import fonnymunkey.simplehats.util.HatSeason;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.WeightedListInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.List;

public class BagItem extends Item {

    private HatSeason hatSeason = HatSeason.NONE;
    private boolean seasonal = false;
    private Rarity rarity = Rarity.COMMON; //Don't let enchantments artificially change hat results
    private List<HatItem> availableHatList = new ArrayList<>();
    private WeightedListInt availableHatListWeighted = null;

    public BagItem(Rarity rarity) {
        super(new Properties()
                .rarity(rarity)
                .tab(ModItems.TAB));
        this.rarity = rarity;
    }

    public BagItem(HatSeason hatSeason) {
        super(new Properties()
                .rarity(Rarity.EPIC)
                .tab(ModItems.TAB));
        this.hatSeason = hatSeason;
        this.seasonal = true;
        this.rarity = Rarity.EPIC;
    }

    @Override
    public int getUseDuration(ItemStack itemStack) {
        return 24;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack itemStack) {
        return UseAnim.NONE;
    }

    //TODO: Replace sounds
    public static SoundEvent getUnwrapSound() { return SoundEvents.AXE_SCRAPE; }
    //TODO: Replace sounds
    public static SoundEvent getUnwrapFinishSound() { return SoundEvents.ITEM_BREAK; }

    @Override
    public void onUsingTick(ItemStack stack, LivingEntity player, int count) {
        if(player.getUseItemRemainingTicks() % 4 == 0 && player.getUseItemRemainingTicks() <= player.getUseItem().getUseDuration() - 3) {
            player.playSound(this.getUnwrapSound(), 0.5F + 0.5F * (float)player.getRandom().nextInt(2), (player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.2F + 1.0F);
        }
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        level.playSound((Player)null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), this.getUnwrapFinishSound(), SoundSource.NEUTRAL, 1.0F, 1.0F + (level.random.nextFloat() - level.random.nextFloat()) * 0.4F);
        itemStack.shrink(1);

        if(!level.isClientSide) {
            if(!this.seasonal && HatSeason.getSeason() != HatSeason.NONE) {
                if(level.random.nextInt(0, 4) < 1) {
                    livingEntity.spawnAtLocation(this.getSeasonalBag());
                }
            }
            livingEntity.spawnAtLocation(this.getBagResult(level));
        }
        return itemStack;
    }

    private Item getSeasonalBag() {
        switch(this.hatSeason) {
            case EASTER -> { return ModItems.HATBAG_EASTER.get(); }
            case SUMMER -> { return ModItems.HATBAG_SUMMER.get(); }
            case HALLOWEEN -> { return ModItems.HATBAG_HALLOWEEN.get(); }
            case FESTIVE -> { return ModItems.HATBAG_FESTIVE.get(); }
        }
        SimpleHats.logger.log(org.apache.logging.log4j.Level.WARN, "Failed to get seasonal bag type");
        return Items.AIR;
    }

    private ItemLike getBagResult(Level level) {
        if (this.availableHatList.size() == 0) {
            for (HatItem hat : ModItems.hatList) {
                if ((hat.getHatEntry().getHatRarity() == this.rarity || this.seasonal) &&
                        hat.getHatEntry().getHatWeight() != 0 &&
                        hat.getHatEntry().getHatSeason() == this.hatSeason) {
                    this.availableHatList.add(hat);
                }
            }
            if(this.availableHatList.size() == 0) {
                SimpleHats.logger.log(org.apache.logging.log4j.Level.WARN, "Failed to populate bag hat list");
                return Items.AIR;
            }
            SimpleWeightedRandomList.Builder tempListBuilder = generateSimpleWeightedList(SimpleWeightedRandomList.<IntProvider>builder().add(ConstantInt.of(0), this.availableHatList.get(0).getHatEntry().getHatWeight()), 1);
            this.availableHatListWeighted = new WeightedListInt(tempListBuilder.build());
        }
        return this.availableHatList.get(this.availableHatListWeighted.sample(level.random));
    }

    //Nasty, nasty recursion
    private SimpleWeightedRandomList.Builder generateSimpleWeightedList(SimpleWeightedRandomList.Builder list, int i) {
        if(i<this.availableHatList.size()) {
            list.add(ConstantInt.of(i), this.availableHatList.get(i).getHatEntry().getHatWeight());
            i++;
            list = generateSimpleWeightedList(list, i);
        }
        return list;
    }
}

