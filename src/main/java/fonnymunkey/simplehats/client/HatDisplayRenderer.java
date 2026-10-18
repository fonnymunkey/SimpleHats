package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.entity.HatDisplay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class HatDisplayRenderer extends RenderLivingBase<HatDisplay> {

    private static final ResourceLocation TEXTURE = new ResourceLocation(SimpleHats.MODID, "textures/entity/hatdisplay.png");

    public HatDisplayRenderer(RenderManager renderManager) {
        super(renderManager, new HatDisplayModel(), 0.0F);
        this.addLayer(new LayerRenderer<HatDisplay>() {
            @Override
            public void doRenderLayer(HatDisplay entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
                ItemStack stack = entity.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
                if(stack.isEmpty() || entity.isInvisible()) return;
                GlStateManager.pushMatrix();
                GlStateManager.scale(1.01F, 1.01F, 1.01F);
                GlStateManager.translate(0.0F, 0.97F, 0.0F);
                GlStateManager.scale(0.66F, 0.66F, 0.66F);
                GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
                Minecraft.getMinecraft().getItemRenderer().renderItem(entity, stack, ItemCameraTransforms.TransformType.HEAD);
                GlStateManager.popMatrix();
            }

            @Override
            public boolean shouldCombineTextures() {
                return false;
            }
        });
    }

    @Override
    protected ResourceLocation getEntityTexture(HatDisplay entity) {
        return TEXTURE;
    }

    @Override
    protected void applyRotations(HatDisplay entity, float ageInTicks, float rotationYaw, float partialTicks) {
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        float wiggle = (float)(entity.world.getTotalWorldTime() - entity.lastHit) + partialTicks;
        if(wiggle < 5.0F) GlStateManager.rotate(MathHelper.sin(wiggle / 1.5F * (float)Math.PI) * 3.0F, 0.0F, 1.0F, 0.0F);
    }

    @Override
    protected boolean canRenderName(HatDisplay entity) {
        return entity.hasCustomName() && entity.getDistanceSq(this.renderManager.renderViewEntity) < 4096.0D;
    }
}
