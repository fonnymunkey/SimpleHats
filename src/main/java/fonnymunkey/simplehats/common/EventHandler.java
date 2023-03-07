package fonnymunkey.simplehats.common;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import org.apache.logging.log4j.Level;

public class EventHandler {

    @Mod.EventBusSubscriber(modid = SimpleHats.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandlerMod {
        @SubscribeEvent
        public static void entityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(ModRegistry.HATDISPLAYENTITY.get(), HatDisplay.createAttributes().build());
        }

        @SubscribeEvent
        public static void registerHats(RegisterEvent event) {
            if(event.getRegistryKey().equals(ForgeRegistries.Keys.ITEMS)) {
                for(HatEntry entry : HatJson.getHatList()) {
                    HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry) : new HatItem(entry);
                    event.register(ForgeRegistries.Keys.ITEMS, new ResourceLocation(SimpleHats.modId, entry.getHatName()), () -> hat);
                    ModRegistry.hatList.add(hat);
                }
                SimpleHats.logger.log(Level.INFO, "Generated " + ModRegistry.hatList.size() + " hat items from hat entries.");
            }
        }

        @SubscribeEvent
        public static void registerCreativeTab(CreativeModeTabEvent.Register event) {
            ModRegistry.HAT_TAB = event.registerCreativeModeTab(new ResourceLocation(SimpleHats.modId, SimpleHats.modId), builder -> builder
                    .icon(() -> new ItemStack(ModRegistry.HATICON.get()))
                    .title(Component.translatable("itemGroup.simplehats"))
                    .displayItems((features, output, operator) -> {
                        output.accept(ModRegistry.HATBAG_COMMON.get());
                        output.accept(ModRegistry.HATBAG_UNCOMMON.get());
                        output.accept(ModRegistry.HATBAG_RARE.get());
                        output.accept(ModRegistry.HATBAG_EPIC.get());
                        output.accept(ModRegistry.HATBAG_EASTER.get());
                        output.accept(ModRegistry.HATBAG_SUMMER.get());
                        output.accept(ModRegistry.HATBAG_HALLOWEEN.get());
                        output.accept(ModRegistry.HATBAG_FESTIVE.get());
                        output.accept(ModRegistry.HATSCRAPS_COMMON.get());
                        output.accept(ModRegistry.HATSCRAPS_UNCOMMON.get());
                        output.accept(ModRegistry.HATSCRAPS_RARE.get());
                        output.accept(ModRegistry.HATSCRAPS_EASTER.get());
                        output.accept(ModRegistry.HATSCRAPS_SUMMER.get());
                        output.accept(ModRegistry.HATSCRAPS_HALLOWEEN.get());
                        output.accept(ModRegistry.HATSCRAPS_FESTIVE.get());
                        output.accept(ModRegistry.HATICON.get());
                        output.accept(ModRegistry.HATDISPLAYITEM.get());

                        for (HatItem hat : ModRegistry.hatList) {
                            output.accept(hat);
                        }
                    })
            );
        }
    }

    /*
    @Mod.EventBusSubscriber(modid = SimpleHats.modId, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class EventHandlerForge {
        @SubscribeEvent
        public static void curioEquipEvent(CurioEquipEvent event) {
            if(event.getEntity().level.isClientSide() || event.isCanceled()) return;
            ItemStack stack = event.getStack();
            if(stack.getItem() == ModRegistry.HATSPECIAL.get() && event.getSlotContext().entity() instanceof Player player) {
                CompoundTag tag = stack.getOrCreateTag();
                tag.putInt("CustomModelData", UUIDHandler.getUUIDMap().getOrDefault(player.getStringUUID(), 0));
            }
        }
    }
     */
}