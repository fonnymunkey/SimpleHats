package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.client.hat.HatRenderState;

import net.minecraft.resources.Identifier;
import net.minecraft.util.context.ContextKey;

public class SimpleHatsClient {
    public static final ContextKey<HatRenderState> HAT_RENDER_STATE_KEY = new ContextKey<>(Identifier.fromNamespaceAndPath(Constants.MOD_ID, "hat_render_state"));
}
