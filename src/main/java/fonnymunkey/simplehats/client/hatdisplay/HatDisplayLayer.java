package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class HatDisplayLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private final RenderLayerParent<T, M> renderLayerParent;

    public HatDisplayLayer(RenderLayerParent<T, M> renderer) {
        super(renderer);
        this.renderLayerParent = renderer;
    }

    @Override
    public void render(PoseStack matrixStack, MultiBufferSource renderTypeBuffer, int light, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(livingEntity instanceof HatDisplay display) {
            matrixStack.pushPose();
            ItemStack stack = display.getItemBySlot(null);
            if(!stack.isEmpty() && stack.getItem() instanceof HatItem) {
                SlotContext slotContext = new SlotContext("head", livingEntity, 0, true,true);
                CuriosRendererRegistry.getRenderer(stack.getItem()).ifPresent(
                        renderer -> renderer
                                .render(stack, slotContext, matrixStack, renderLayerParent,
                                        renderTypeBuffer, light, limbSwing, limbSwingAmount, partialTicks,
                                        ageInTicks, netHeadYaw, headPitch));
            }
            matrixStack.popPose();
        }
    }
}
