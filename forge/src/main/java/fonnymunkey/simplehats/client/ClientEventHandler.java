package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hat.HatLayer;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import fonnymunkey.simplehats.common.init.SimpleHatsConfig;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Constants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEventHandler {
	
	@SubscribeEvent
	public static void onInitializeClient(FMLClientSetupEvent event) {
		ModLoadingContext.get().registerExtensionPoint(
				ConfigScreenHandler.ConfigScreenFactory.class,
				() -> new ConfigScreenHandler.ConfigScreenFactory((minecraft, parent) -> AutoConfig.getConfigScreen(SimpleHatsConfig.class, parent).get()));
	}
	
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
		if(event.getSkin("default") instanceof PlayerRenderer playerRenderer) {
			playerRenderer.addLayer(new HatLayer<>(playerRenderer));
		}
		if(event.getSkin("slim") instanceof PlayerRenderer playerRenderer) {
			playerRenderer.addLayer(new HatLayer<>(playerRenderer));
		}
	}
}