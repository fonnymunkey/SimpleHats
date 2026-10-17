package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class HatDisplayLayer extends RenderLayer<HatDisplayRenderState, HatDisplayModel> {

    public HatDisplayLayer(RenderLayerParent<HatDisplayRenderState, HatDisplayModel> renderer) {
        super(renderer);
    }

    @Override
    public void submit(PoseStack matrixStack, SubmitNodeCollector collector, int light, HatDisplayRenderState state, float yRot, float xRot) {
        if(!state.hatItem.isEmpty() && !state.isInvisible) {
            matrixStack.pushPose();
            
            matrixStack.scale(1.01F, 1.01F, 1.01F);
            matrixStack.translate(0D, 0.97D, 0.0D);
            
            matrixStack.scale(0.66F, 0.66F, 0.66F);
            matrixStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
            
            state.hatItem.submit(matrixStack, collector, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
            
            matrixStack.popPose();
        }
    }
}
