package fonnymunkey.simplehats.handlers;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.entity.HatDisplay;
import fonnymunkey.simplehats.item.BagItem;
import fonnymunkey.simplehats.item.HatDisplayItem;
import fonnymunkey.simplehats.item.HatItem;
import fonnymunkey.simplehats.item.HatItemDyeable;
import fonnymunkey.simplehats.recipe.HatDyeRecipe;
import fonnymunkey.simplehats.recipe.HatScrapRecipe;
import fonnymunkey.simplehats.recipe.HatVariantRecipe;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatSeason;
import fonnymunkey.simplehats.util.HatJson;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityEntryBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@Mod.EventBusSubscriber(modid = SimpleHats.MODID)
public class ModRegistry {

    public static final CreativeTabs HAT_TAB = new CreativeTabs(SimpleHats.MODID) {
        @Override
        @SideOnly(Side.CLIENT)
        public ItemStack createIcon() {
            return new ItemStack(HATICON);
        }
    };

    public static final List<HatItem> HAT_LIST = new ArrayList<>();
    public static final List<Item> ITEM_LIST = new ArrayList<>();

    public static final Item HATBAG_COMMON = new BagItem("hatbag_common", EnumRarity.COMMON, HatSeason.NONE);
    public static final Item HATBAG_UNCOMMON = new BagItem("hatbag_uncommon", EnumRarity.UNCOMMON, HatSeason.NONE);
    public static final Item HATBAG_RARE = new BagItem("hatbag_rare", EnumRarity.RARE, HatSeason.NONE);
    public static final Item HATBAG_EPIC = new BagItem("hatbag_epic", EnumRarity.EPIC, HatSeason.NONE);
    public static final Item HATBAG_EASTER = new BagItem("hatbag_easter", EnumRarity.EPIC, HatSeason.EASTER);
    public static final Item HATBAG_SUMMER = new BagItem("hatbag_summer", EnumRarity.EPIC, HatSeason.SUMMER);
    public static final Item HATBAG_HALLOWEEN = new BagItem("hatbag_halloween", EnumRarity.EPIC, HatSeason.HALLOWEEN);
    public static final Item HATBAG_FESTIVE = new BagItem("hatbag_festive", EnumRarity.EPIC, HatSeason.FESTIVE);
    public static final Item HATSCRAPS_COMMON = simpleItem("hatscraps_common", EnumRarity.COMMON);
    public static final Item HATSCRAPS_UNCOMMON = simpleItem("hatscraps_uncommon", EnumRarity.UNCOMMON);
    public static final Item HATSCRAPS_RARE = simpleItem("hatscraps_rare", EnumRarity.RARE);
    public static final Item HATSCRAPS_EASTER = simpleItem("hatscraps_easter", EnumRarity.EPIC);
    public static final Item HATSCRAPS_SUMMER = simpleItem("hatscraps_summer", EnumRarity.EPIC);
    public static final Item HATSCRAPS_HALLOWEEN = simpleItem("hatscraps_halloween", EnumRarity.EPIC);
    public static final Item HATSCRAPS_FESTIVE = simpleItem("hatscraps_festive", EnumRarity.EPIC);
    public static final Item HATICON = simpleItem("haticon", EnumRarity.COMMON);
    public static final Item HATDISPLAY = new HatDisplayItem();
    public static final HatItem HATSPECIAL = new HatItem(new HatEntry("special", EnumRarity.EPIC, 0));

    private static Item simpleItem(String name, EnumRarity rarity) {
        return new Item() {
            @Override
            public EnumRarity getRarity(ItemStack stack) {
                return rarity;
            }
        }.setRegistryName(SimpleHats.MODID, name).setTranslationKey(SimpleHats.MODID + "." + name).setCreativeTab(HAT_TAB);
    }

    @SubscribeEvent
    public static void registerItemEvent(RegistryEvent.Register<Item> event) {
        ITEM_LIST.add(HATBAG_COMMON);
        ITEM_LIST.add(HATBAG_UNCOMMON);
        ITEM_LIST.add(HATBAG_RARE);
        ITEM_LIST.add(HATBAG_EPIC);
        ITEM_LIST.add(HATBAG_EASTER);
        ITEM_LIST.add(HATBAG_SUMMER);
        ITEM_LIST.add(HATBAG_HALLOWEEN);
        ITEM_LIST.add(HATBAG_FESTIVE);
        ITEM_LIST.add(HATSCRAPS_COMMON);
        ITEM_LIST.add(HATSCRAPS_UNCOMMON);
        ITEM_LIST.add(HATSCRAPS_RARE);
        ITEM_LIST.add(HATSCRAPS_EASTER);
        ITEM_LIST.add(HATSCRAPS_SUMMER);
        ITEM_LIST.add(HATSCRAPS_HALLOWEEN);
        ITEM_LIST.add(HATSCRAPS_FESTIVE);
        ITEM_LIST.add(HATICON);
        ITEM_LIST.add(HATDISPLAY);
        ITEM_LIST.add(HATSPECIAL);
        for(HatEntry entry : HatJson.getHatList()) {
            HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry) : new HatItem(entry);
            HAT_LIST.add(hat);
            ITEM_LIST.add(hat);
        }
        SimpleHats.LOGGER.info("Generated " + HAT_LIST.size() + " hat items from hat entries.");
        event.getRegistry().registerAll(ITEM_LIST.toArray(new Item[0]));
    }

    @SubscribeEvent
    public static void registerRecipeEvent(RegistryEvent.Register<IRecipe> event) {
        event.getRegistry().registerAll(
                new HatScrapRecipe().setRegistryName(SimpleHats.MODID, "hatscraps"),
                new HatVariantRecipe().setRegistryName(SimpleHats.MODID, "hatvariants"),
                new HatDyeRecipe().setRegistryName(SimpleHats.MODID, "hatdyeing")
        );
    }

    @SubscribeEvent
    public static void registerEntityEvent(RegistryEvent.Register<EntityEntry> event) {
        event.getRegistry().register(EntityEntryBuilder.create()
                .entity(HatDisplay.class)
                .id(new ResourceLocation(SimpleHats.MODID, "hatdisplay"), 0)
                .name(SimpleHats.MODID + ".hatdisplay")
                .tracker(160, 3, true)
                .build());
    }
}
