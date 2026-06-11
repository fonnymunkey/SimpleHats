package fonnymunkey.simplehats.platform.services;

import fonnymunkey.simplehats.client.hat.HatRenderState;

import net.minecraft.client.renderer.entity.state.EntityRenderState;

public interface IClientPlatformHelper {
    HatRenderState getHatState(EntityRenderState state);
}
