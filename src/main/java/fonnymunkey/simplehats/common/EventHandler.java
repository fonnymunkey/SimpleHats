package fonnymunkey.simplehats.common;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.Config;
import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.UUIDHandler;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.logging.log4j.Level;
import top.theillusivec4.curios.api.event.CurioEquipEvent;

public class EventHandler {

    @Mod.EventBusSubscriber(modid = SimpleHats.modId, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class EventHandlerMod {
        @SubscribeEvent
        public static void entityAttributeCreation(EntityAttributeCreationEvent event) {
            event.put(ModRegistry.HATDISPLAYENTITY.get(), HatDisplay.createAttributes().build());
        }

        @SubscribeEvent
        public static void reloadConfig(ModConfigEvent event) {
            if(event.getConfig().getSpec() == Config.CLIENT_SPEC) Config.cachedClient.refresh();
            else if(event.getConfig().getSpec() == Config.COMMON_SPEC) Config.cachedCommon.refresh();
            else if(event.getConfig().getSpec() == Config.SERVER_SPEC) Config.cachedServer.refresh();
        }

        @SubscribeEvent
        public static void registerHats(RegistryEvent.Register<Item> event) {
            for(HatEntry entry : HatJson.getHatList()) {
                HatItem hat = entry.getHatDyeSettings().getUseDye() ? new HatItemDyeable(entry) : new HatItem(entry);
                event.getRegistry().register(hat);
                ModRegistry.hatList.add(hat);
            }
            SimpleHats.logger.log(Level.INFO, "Generated " + ModRegistry.hatList.size() + " hat items from hat entries.");
        }
    }

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
}