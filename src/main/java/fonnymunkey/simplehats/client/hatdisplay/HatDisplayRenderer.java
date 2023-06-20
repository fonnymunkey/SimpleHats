package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class HatDisplayRenderer extends LivingEntityRenderer<HatDisplay, HatDisplayModel<HatDisplay>> {
    public static final ResourceLocation HATDISPLAY_TEXTURE = new ResourceLocation(SimpleHats.modId, "textures/entity/hatdisplay.png");
    public static final ModelLayerLocation HATDISPLAY_LOCATION = new ModelLayerLocation(HATDISPLAY_TEXTURE, "main");

    public HatDisplayRenderer(EntityRendererProvider.Context context) {
        super(context, new HatDisplayModel<>(context.bakeLayer(HATDISPLAY_LOCATION)), 0.0F);
        this.addLayer(new HatDisplayLayer<>(this));
    }

    public ResourceLocation getTextureLocation(HatDisplay entity) {
        return HATDISPLAY_TEXTURE;
    }

    protected void setupRotations(HatDisplay entityLiving, PoseStack matrixStack, float ageInTicks, float rotationYaw, float partialTicks) {
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        float f = (float)(entityLiving.level().getGameTime() - entityLiving.lastHit) + partialTicks;
        if(f < 5.0F) matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(f / 1.5F * (float)Math.PI) * 3.0F));
    }

    protected boolean shouldShowName(HatDisplay entity) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(entity);
        return d0 < 4096 && entity.hasCustomName();
    }
}