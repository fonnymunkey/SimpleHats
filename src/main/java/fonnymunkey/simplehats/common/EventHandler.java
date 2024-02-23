package fonnymunkey.simplehats.common;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

                    if(hat instanceof HatItemDyeable) CauldronInteraction.WATER.f_303346_().put((HatItemDyeable)hat, CauldronInteraction.DYED_ITEM);
                }
                SimpleHats.logger.log(Level.INFO, "Generated " + ModRegistry.hatList.size() + " hat items from hat entries.");
            }
        }

        @SubscribeEvent
        public static void populateCreativeTab(BuildCreativeModeTabContentsEvent event) {
            if(event.getTab() == ModRegistry.HAT_TAB.get()) {
                for(HatItem hat : ModRegistry.hatList) {
                    event.accept(hat);
                }
            }
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