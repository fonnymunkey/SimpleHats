package fonnymunkey.simplehats.client.hatdisplay;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
public class HatDisplayRenderer extends LivingEntityRenderer<HatDisplay, HatDisplayModel<HatDisplay>> {
    public static final Identifier HATDISPLAY_TEXTURE = Identifier.of(SimpleHats.modId, "textures/entity/hatdisplay.png");
    public static final EntityModelLayer HATDISPLAY_LOCATION = new EntityModelLayer(HATDISPLAY_TEXTURE, "main");

    public HatDisplayRenderer(EntityRendererFactory.Context context) {
        super(context, new HatDisplayModel<>(context.getPart(HATDISPLAY_LOCATION)), 0.0F);
        this.addFeature(new HatDisplayLayer<>(this));
    }

    public Identifier getTexture(HatDisplay entity) {
        return HATDISPLAY_TEXTURE;
    }

    @Override
    protected void setupTransforms(HatDisplay entityLiving, MatrixStack matrixStack, float animationProgress, float rotationYaw, float partialTicks, float scale) {
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F - rotationYaw));
        float f = (float)(entityLiving.getWorld().getTime() - entityLiving.lastHit) + partialTicks;
        if(f < 5.0F) matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(MathHelper.sin(f / 1.5F * (float)Math.PI) * 3.0F));
    }

    protected boolean hasLabel(HatDisplay entity) {
        double d0 = this.dispatcher.getSquaredDistanceToCamera(entity);
        return d0 < 4096 && entity.hasCustomName();
    }
}