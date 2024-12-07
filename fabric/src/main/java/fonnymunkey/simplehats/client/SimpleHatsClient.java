package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hat.HatLayer;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.world.item.Item;

@Environment(EnvType.CLIENT)
public class SimpleHatsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for(Item hat : SimpleHatsCommon.MOD_REGISTRY.getHatList()) {
            if(hat instanceof HatItemDyeable hatDye) {
                ColorProviderRegistry.ITEM.register((stack, color) -> ((HatItemDyeable)stack.getItem()).getColor(stack), hatDye);
            }
            if(hat instanceof AccessoryRenderer) {
                AccessoriesRendererRegistry.registerNoRenderer(hat);
            }
        }
        AccessoriesRendererRegistry.registerNoRenderer(SimpleHatsCommon.MOD_REGISTRY.getHatSpecial());

        EntityRendererRegistry.register(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplayRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HatDisplayRenderer.HATDISPLAY_LOCATION, HatDisplayModel::getTexturedModelData);
        
        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityRenderer instanceof PlayerRenderer playerEntityRenderer) {
                registrationHelper.register(new HatLayer<>(playerEntityRenderer));
            }
        });
    }
}