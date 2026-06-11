package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hat.HatLayer;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.world.entity.player.PlayerModelType;

@EventBusSubscriber(modid = Constants.MOD_ID, value = Dist.CLIENT)
public class ClientEventHandler {
	
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
		if(event.getPlayerRenderer(PlayerModelType.WIDE) instanceof AvatarRenderer<AbstractClientPlayer> playerRenderer) {
			playerRenderer.addLayer(new HatLayer<>(playerRenderer));
		}
		if(event.getPlayerRenderer(PlayerModelType.SLIM) instanceof AvatarRenderer<AbstractClientPlayer> playerRenderer) {
			playerRenderer.addLayer(new HatLayer<>(playerRenderer));
		}
	}
}