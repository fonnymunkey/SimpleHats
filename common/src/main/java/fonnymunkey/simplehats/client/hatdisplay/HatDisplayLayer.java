package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HatDisplayLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final RenderLayerParent<T, M> renderLayerParent;

    public HatDisplayLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(livingEntity instanceof HatDisplay display) {
            ItemStack stack = display.getItemBySlot(null);
            if(!stack.isEmpty() && stack.getItem() instanceof HatItem && !livingEntity.isInvisible()) {
                matrixStack.pushPose();
                
                matrixStack.scale(1.01F, 1.01F, 1.01F);
                matrixStack.translate(0D, 0.97D, 0.0D);
                
                matrixStack.scale(0.66F, 0.66F, 0.66F);
                matrixStack.mulPose(Axis.XP.rotationDegrees(180.0F));
                matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F));
                
                Minecraft.getInstance().getEntityRenderDispatcher().getItemInHandRenderer().renderItem(livingEntity, stack, ItemDisplayContext.HEAD, false, matrixStack, renderTypeBuffer, light);
                
                matrixStack.popPose();
            }
        }
    }
}
