package fonnymunkey.simplehats.compat.curios;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.client.hat.HatRenderer;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;

import java.util.List;

public class CuriosHatRenderer implements ICurioRenderer {
	
	public static void register(List<? extends HatItem> hats) {
		for(HatItem hat : hats) {
			ICurioRenderer.register(hat, CuriosHatRenderer::new);
		}
	}
	
	@Override
	public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(ItemStack stack, SlotContext slotContext, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, S renderState, RenderLayerParent<S, M> renderLayerParent, EntityRendererProvider.Context context, float yRotation, float xRotation) {
		if(!HatRenderer.shouldRender(slotContext.entity())) return;
		HatRenderer.submitHat(HatRenderer.extractHat(stack, slotContext.entity()), renderState, renderLayerParent.getModel(), poseStack, submitNodeCollector, packedLight);
	}
}
