package fonnymunkey.simplehats.platform;

import fonnymunkey.simplehats.client.SimpleHatsClient;
import fonnymunkey.simplehats.client.hat.HatRenderState;
import fonnymunkey.simplehats.platform.services.IClientPlatformHelper;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

public class NeoForgeClientPlatformHelper implements IClientPlatformHelper {
    @Override
    public HatRenderState getHatState(EntityRenderState state) {
        return state.getRenderDataOrDefault(SimpleHatsClient.HAT_RENDER_STATE_KEY, new HatRenderState());
    }
}
