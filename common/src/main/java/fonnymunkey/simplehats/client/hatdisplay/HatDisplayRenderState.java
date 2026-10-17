package fonnymunkey.simplehats.client.hatdisplay;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;

public class HatDisplayRenderState extends LivingEntityRenderState {
    public ItemStackRenderState itemStackRenderState = new ItemStackRenderState();
    public float hurtAnim;
}
