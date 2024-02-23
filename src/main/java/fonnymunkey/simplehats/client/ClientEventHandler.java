package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

public class ClientEventHandler {

    @Mod.EventBusSubscriber(modid = SimpleHats.modId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientEventHandlerMod {
        @SubscribeEvent
        public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
            for(HatItem hat : ModRegistry.hatList) {
                if(hat instanceof HatItemDyeable hatDye) {
                    event.register((stack, color) -> ((HatItemDyeable)stack.getItem()).getColor(stack), hatDye);
                }
            }
        }

        @SubscribeEvent
        public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModRegistry.HATDISPLAYENTITY.get(), HatDisplayRenderer::new);
        }

        @SubscribeEvent
        public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(HatDisplayRenderer.HATDISPLAY_LOCATION, HatDisplayModel::createBodyLayer);
        }

        /*
        @SubscribeEvent
        public static void addPackFinder(AddPackFindersEvent event) {
            if(event.getPackType() == PackType.CLIENT_RESOURCES) {
                event.addRepositorySource(new HatRepositorySource(FMLPaths.CONFIGDIR.get()));
            }
        }
         */
    }
}