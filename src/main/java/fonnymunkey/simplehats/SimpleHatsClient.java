package fonnymunkey.simplehats;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayModel;
import fonnymunkey.simplehats.client.hatdisplay.HatDisplayRenderer;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.item.Item;

@Environment(EnvType.CLIENT)
public class SimpleHatsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        for(Item hat : ModRegistry.hatList) {
            if(hat instanceof HatItemDyeable hatDye) {
                ColorProviderRegistry.ITEM.register((stack, color) -> ((HatItemDyeable)stack.getItem()).getColor(stack), hatDye);
            }
            if(hat instanceof TrinketRenderer renderer) {
                TrinketRendererRegistry.registerRenderer(hat, renderer);
            }
        }
        TrinketRendererRegistry.registerRenderer((Item)ModRegistry.HATSPECIAL, (TrinketRenderer)ModRegistry.HATSPECIAL);

        /*
        if(SimpleHats.config.common.allowUpdates) {
            UUIDHandler.checkResourceUpdates();
        }
        */

        EntityRendererRegistry.register(ModRegistry.HATDISPLAYENTITY, HatDisplayRenderer::new);
        EntityModelLayerRegistry.registerModelLayer(HatDisplayRenderer.HATDISPLAY_LOCATION, HatDisplayModel::getTexturedModelData);
    }
}
