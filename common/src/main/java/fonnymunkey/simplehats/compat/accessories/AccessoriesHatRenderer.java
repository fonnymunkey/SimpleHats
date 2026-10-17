package fonnymunkey.simplehats.compat.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.client.hat.HatRenderer;
import fonnymunkey.simplehats.common.item.HatItem;
import io.wispforest.accessories.api.AccessoriesStorageLookup;
import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.client.AccessoryRenderState;
import io.wispforest.accessories.api.client.renderers.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotPath;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.context.ContextKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class AccessoriesHatRenderer implements AccessoryRenderer {
	
	public static final ResourceLocation ID = ResourceLocation.tryBuild(Constants.MOD_ID, "hat");
	
	private static final ContextKey<ItemStackRenderState> HAT_STACK_STATE = new ContextKey<>(ResourceLocation.tryBuild(Constants.MOD_ID, "hat_stack_state"));
	
	public static void register(List<? extends HatItem> hats) {
		AccessoriesRendererRegistry.registerRenderer(ID, AccessoriesHatRenderer::new);
		for(HatItem hat : hats) {
			AccessoriesRendererRegistry.bindItemToRenderer(hat, ID);
		}
	}
	
	@Override
	public boolean shouldRender(ItemStack stack, SlotPath path, AccessoriesStorageLookup storageLookup, LivingEntity entity, LivingEntityRenderState entityState, boolean isRenderingEnabled) {
		if(!HatRenderer.shouldRender(entity)) return false;
		return AccessoryRenderer.super.shouldRender(stack, path, storageLookup, entity, entityState, isRenderingEnabled);
	}
	
	@Override
	public void extractRenderState(ItemStack stack, SlotPath path, AccessoriesStorageLookup storageLookup, LivingEntity entity, LivingEntityRenderState entityState, AccessoryRenderState accessoryState) {
		accessoryState.setStateData(HAT_STACK_STATE, HatRenderer.extractHat(stack, entity));
	}
	
	@Override
	public <S extends LivingEntityRenderState> void render(AccessoryRenderState accessoryState, S entityState, EntityModel<S> model, PoseStack poseStack, SubmitNodeCollector collector) {
		HatRenderer.submitHat(accessoryState.getStateData(HAT_STACK_STATE), entityState, model, poseStack, collector, entityState.lightCoords);
	}
}
