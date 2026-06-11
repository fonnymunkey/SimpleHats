package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class HatDisplayLayer<S extends HatDisplayRenderState, M extends EntityModel<S>> extends RenderLayer<S, M> {

    private final RenderLayerParent<S, M> renderLayerParent;

    public HatDisplayLayer(RenderLayerParent<S, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, S state, float yRot, float xRot) {
        if (!state.isInvisible) {
            poseStack.pushPose();

            poseStack.scale(1.01F, 1.01F, 1.01F);
            poseStack.translate(0D, 0.97D, 0.0D);

            poseStack.scale(0.66F, 0.66F, 0.66F);
            poseStack.mulPose(Axis.XP.rotationDegrees(180.0F));
            poseStack.mulPose(Axis.YP.rotationDegrees(180.0F));

            state.itemStackRenderState.submit(poseStack, submitNodeCollector, lightCoords, OverlayTexture.NO_OVERLAY, 0);

            poseStack.popPose();
        }
    }
}
