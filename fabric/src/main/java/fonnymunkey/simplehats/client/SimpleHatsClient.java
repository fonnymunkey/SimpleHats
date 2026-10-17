package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hat.HatLayer;
import fonnymunkey.simplehats.client.hat.HatRenderState;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;

import net.minecraft.client.renderer.entity.player.AvatarRenderer;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

@Environment(EnvType.CLIENT)
public class SimpleHatsClient implements ClientModInitializer {
    public static final RenderStateDataKey<HatRenderState> HAT_RENDER_STATE_KEY = RenderStateDataKey.create(() -> "SimpleHats Render State");

    @Override
    public void onInitializeClient() {
//        for(Item hat : SimpleHatsCommon.MOD_REGISTRY.getHatList()) {
//            if(hat instanceof AccessoryRenderer) {
//                AccessoriesRendererRegistry.registerNoRenderer(hat);
//            }
//        }
//        AccessoriesRendererRegistry.registerNoRenderer(SimpleHatsCommon.MOD_REGISTRY.getHatSpecial());

        EntityRendererRegistry.register(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplayRenderer::new);
        ModelLayerRegistry.registerModelLayer(HatDisplayRenderer.HATDISPLAY_LOCATION, HatDisplayModel::getTexturedModelData);

        LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if (entityRenderer instanceof AvatarRenderer<?> playerEntityRenderer) {
                registrationHelper.register(new HatLayer<>(playerEntityRenderer));
            }
        });
    }
}