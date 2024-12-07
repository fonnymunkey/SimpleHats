package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hat.HatLayer;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;

@EventBusSubscriber(modid = Constants.MOD_ID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
	
	@SubscribeEvent
	public static void registerItemColor(RegisterColorHandlersEvent.Item event) {
		for(Item hat : SimpleHatsCommon.MOD_REGISTRY.getHatList()) {
			if(hat instanceof HatItemDyeable hatDye) {
				event.register((stack, color) -> ((HatItemDyeable)stack.getItem()).getColor(stack), hatDye);
			}
			if(hat instanceof AccessoryRenderer) {
				AccessoriesRendererRegistry.registerNoRenderer(hat);
			}
		}
		AccessoriesRendererRegistry.registerNoRenderer(SimpleHatsCommon.MOD_REGISTRY.getHatSpecial());
	}
	
	@SubscribeEvent
	public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
		event.registerEntityRenderer(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplayRenderer::new);
	}
	
	@SubscribeEvent
	public static void registerEntityLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(HatDisplayRenderer.HATDISPLAY_LOCATION, HatDisplayModel::getTexturedModelData);
	}
	
	@SubscribeEvent
	public static void addEntityLayers(EntityRenderersEvent.AddLayers event) {
		if(event.getSkin(PlayerSkin.Model.WIDE) instanceof PlayerRenderer playerRenderer) {
			playerRenderer.addLayer(new HatLayer<>(playerRenderer));
		}
		if(event.getSkin(PlayerSkin.Model.SLIM) instanceof PlayerRenderer playerRenderer) {
			playerRenderer.addLayer(new HatLayer<>(playerRenderer));
		}
	}
}