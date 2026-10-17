package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class SimpleHatsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        SimpleHatsCommon.HAT_SLOT_COMPAT.registerHatRenderers(SimpleHatsCommon.MOD_REGISTRY.getHatList());

        EntityRendererRegistry.register(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), HatDisplayRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HatDisplayRenderer.HATDISPLAY_LOCATION, HatDisplayModel::getTexturedModelData);
    }
}
