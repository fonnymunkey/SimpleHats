package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;

public class HatDisplayRenderer extends LivingEntityRenderer<HatDisplay, HatDisplayRenderState, HatDisplayModel> {
    
    public static final ResourceLocation HATDISPLAY_TEXTURE = ResourceLocation.tryBuild(Constants.MOD_ID, "textures/entity/hatdisplay.png");
    public static final ModelLayerLocation HATDISPLAY_LOCATION = new ModelLayerLocation(HATDISPLAY_TEXTURE, "main");

    public HatDisplayRenderer(EntityRendererProvider.Context context) {
        super(context, new HatDisplayModel(context.bakeLayer(HATDISPLAY_LOCATION)), 0.0F);
        this.addLayer(new HatDisplayLayer(this));
    }

    @Override
    public ResourceLocation getTextureLocation(HatDisplayRenderState state) {
        return HATDISPLAY_TEXTURE;
    }
    
    @Override
    public HatDisplayRenderState createRenderState() {
        return new HatDisplayRenderState();
    }
    
    @Override
    public void extractRenderState(HatDisplay entity, HatDisplayRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);
        this.itemModelResolver.updateForLiving(state.hatItem, entity.getItemBySlot(EquipmentSlot.HEAD), ItemDisplayContext.HEAD, entity);
        state.yRot = Mth.rotLerp(partialTicks, entity.yRotO, entity.getYRot());
        state.wiggle = (float)(entity.level().getGameTime() - entity.lastHit) + partialTicks;
    }

    @Override
    protected void setupRotations(HatDisplayRenderState state, PoseStack matrixStack, float rotationYaw, float scale) {
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        if(state.wiggle < 5.0F) matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(state.wiggle / 1.5F * (float)Math.PI) * 3.0F));
    }

    @Override
    protected boolean shouldShowName(HatDisplay entity, double distanceToCameraSq) {
        return distanceToCameraSq < 4096 && entity.hasCustomName();
    }
}
