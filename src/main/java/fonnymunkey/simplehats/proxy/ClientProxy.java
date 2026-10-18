package fonnymunkey.simplehats.proxy;

import fonnymunkey.simplehats.client.HatDisplayRenderer;
import fonnymunkey.simplehats.client.HatLayer;
import fonnymunkey.simplehats.entity.HatDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        RenderingRegistry.registerEntityRenderingHandler(HatDisplay.class, HatDisplayRenderer::new);
    }

    @Override
    public void init() {
        for(RenderPlayer renderer : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            renderer.addLayer(new HatLayer(renderer));
        }
    }
}
