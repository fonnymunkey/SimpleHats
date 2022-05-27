package fonnymunkey.simplehats.common.init;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.HatJson;
import fonnymunkey.simplehats.common.item.BagItem;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatSeason;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;

public class ModItems {

    public static final CreativeModeTab TAB = new CreativeModeTab(SimpleHats.modId) {
        @Override
        @OnlyIn(Dist.CLIENT)
        public ItemStack makeIcon() {
            return new ItemStack(HATICON.get());
        }
    };

    public static List<HatItem> hatList = new ArrayList<HatItem>();

    public static void registerHats(RegistryEvent.Register<Item> event) {
        for(HatEntry entry : HatJson.getHatList()) {
            HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry) : new HatItem(entry);
            event.getRegistry().register(hat);
            hatList.add(hat);
        }
    }

    public static final DeferredRegister<Item> DEFREG = DeferredRegister.create(Registry.ITEM_REGISTRY, SimpleHats.modId);

    public static final RegistryObject<BagItem> HATBAG_COMMON = DEFREG.register("hatbag_common", () -> new BagItem(Rarity.COMMON));
    public static final RegistryObject<BagItem> HATBAG_UNCOMMON = DEFREG.register("hatbag_uncommon", () -> new BagItem(Rarity.UNCOMMON));
    public static final RegistryObject<BagItem> HATBAG_RARE = DEFREG.register("hatbag_rare", () -> new BagItem(Rarity.RARE));
    public static final RegistryObject<BagItem> HATBAG_EPIC = DEFREG.register("hatbag_epic", () -> new BagItem(Rarity.EPIC));

    public static final RegistryObject<BagItem> HATBAG_EASTER = DEFREG.register("hatbag_easter", () -> new BagItem(HatSeason.EASTER));
    public static final RegistryObject<BagItem> HATBAG_SUMMER = DEFREG.register("hatbag_summer", () -> new BagItem(HatSeason.SUMMER));
    public static final RegistryObject<BagItem> HATBAG_HALLOWEEN = DEFREG.register("hatbag_halloween", () -> new BagItem(HatSeason.HALLOWEEN));
    public static final RegistryObject<BagItem> HATBAG_FESTIVE = DEFREG.register("hatbag_festive", () -> new BagItem(HatSeason.FESTIVE));

    public static final RegistryObject<Item> HATSCRAPS_COMMON = DEFREG.register("hatscraps_common", () -> new Item(new Item.Properties().rarity(Rarity.COMMON).tab(TAB)));
    public static final RegistryObject<Item> HATSCRAPS_UNCOMMON = DEFREG.register("hatscraps_uncommon", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).tab(TAB)));
    public static final RegistryObject<Item> HATSCRAPS_RARE = DEFREG.register("hatscraps_rare", () -> new Item(new Item.Properties().rarity(Rarity.RARE).tab(TAB)));

    public static final RegistryObject<Item> HATSCRAPS_EASTER = DEFREG.register("hatscraps_easter", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(TAB)));
    public static final RegistryObject<Item> HATSCRAPS_SUMMER = DEFREG.register("hatscraps_summer", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(TAB)));
    public static final RegistryObject<Item> HATSCRAPS_HALLOWEEN = DEFREG.register("hatscraps_halloween", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(TAB)));
    public static final RegistryObject<Item> HATSCRAPS_FESTIVE = DEFREG.register("hatscraps_festive", () -> new Item(new Item.Properties().rarity(Rarity.EPIC).tab(TAB)));

    public static final RegistryObject<Item> HATICON = DEFREG.register("haticon", () -> new Item(new Item.Properties().tab(TAB)));
}
