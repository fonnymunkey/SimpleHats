package fonnymunkey.simplehats.client;

import com.mojang.serialization.Decoder;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod.EventBusSubscriber(modid = SimpleHats.modId, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void registerItemColor(ColorHandlerEvent.Item event) {
        for(HatItem hat : ModRegistry.hatList) {
            if(hat instanceof HatItemDyeable hatDye) {
                event.getItemColors().register((stack, color) -> ((HatItemDyeable)stack.getItem()).getColor(stack), hatDye);
                CauldronInteraction.WATER.put(hatDye, CauldronInteraction.DYED_ITEM);
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

    @SubscribeEvent
    public static void addPackFinder(AddPackFindersEvent event) {
        if(event.getPackType() == PackType.CLIENT_RESOURCES) {
            event.addRepositorySource(new HatRepositorySource(FMLPaths.CONFIGDIR.get()));
        }
    }
}