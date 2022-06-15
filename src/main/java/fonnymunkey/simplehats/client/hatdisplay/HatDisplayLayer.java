package fonnymunkey.simplehats.client.hatdisplay;

import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;

public class HatDisplayLayer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {

    private final FeatureRendererContext<T, M> renderLayerParent;

    public HatDisplayLayer(FeatureRendererContext<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider renderTypeBuffer, int light, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(livingEntity instanceof HatDisplay display) {
            matrixStack.push();
            ItemStack stack = display.getEquippedStack(null);
            if(!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                TrinketRendererRegistry.getRenderer(stack.getItem()).ifPresent(
                        renderer -> renderer
                                .render(stack,
                                        null,
                                        this.getContextModel(),
                                        matrixStack,
                                        renderTypeBuffer,
                                        light,
                                        livingEntity,
                                        limbSwing,
                                        limbSwingAmount,
                                        partialTicks,
                                        ageInTicks,
                                        netHeadYaw,
                                        headPitch));
            }
            matrixStack.pop();
        }
    }
}
