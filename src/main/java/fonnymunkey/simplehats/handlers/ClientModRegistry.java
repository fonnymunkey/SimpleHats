package fonnymunkey.simplehats.handlers;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.item.HatItem;
import fonnymunkey.simplehats.item.HatItemDyeable;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = SimpleHats.MODID, value = Side.CLIENT)
public class ClientModRegistry {

    @SubscribeEvent
    public static void modelRegisterEvent(ModelRegistryEvent event) {
        for(Item item : ModRegistry.ITEM_LIST) {
            ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
        }
    }

    @SubscribeEvent
    public static void itemColorEvent(ColorHandlerEvent.Item event) {
        for(HatItem hat : ModRegistry.HAT_LIST) {
            if(hat instanceof HatItemDyeable) {
                event.getItemColors().registerItemColorHandler((stack, tintIndex) -> tintIndex == 0 ? ((HatItemDyeable)stack.getItem()).getColor(stack) : -1, hat);
            }
        }
    }
}
