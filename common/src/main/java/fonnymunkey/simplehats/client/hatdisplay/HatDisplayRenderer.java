package fonnymunkey.simplehats.client.hatdisplay;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.item.HatItem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public class HatDisplayRenderer extends LivingEntityRenderer<HatDisplay, HatDisplayRenderState, HatDisplayModel> {
    
    public static final Identifier HATDISPLAY_TEXTURE = Identifier.fromNamespaceAndPath(Constants.MOD_ID, "textures/entity/hatdisplay.png");
    public static final ModelLayerLocation HATDISPLAY_LOCATION = new ModelLayerLocation(HATDISPLAY_TEXTURE, "main");

    public HatDisplayRenderer(EntityRendererProvider.Context context) {
        super(context, new HatDisplayModel(context.bakeLayer(HATDISPLAY_LOCATION)), 0.0F);
        this.addLayer(new HatDisplayLayer<>(this));
    }

    @Override
    public Identifier getTextureLocation(HatDisplayRenderState state) {
        return HATDISPLAY_TEXTURE;
    }

    @Override
    public HatDisplayRenderState createRenderState() {
        return new HatDisplayRenderState();
    }

    @Override
    public void extractRenderState(HatDisplay entity, HatDisplayRenderState state, float partialTicks) {
        super.extractRenderState(entity, state, partialTicks);

        state.itemStackRenderState.clear();
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);

        if (!stack.isEmpty() && stack.getItem() instanceof HatItem) {
            var itemResolver = Minecraft.getInstance().getItemModelResolver();
            itemResolver.updateForLiving(state.itemStackRenderState, stack, ItemDisplayContext.HEAD, entity);
        }

        state.hurtAnim = (float)(entity.level().getGameTime() - entity.lastHit) + partialTicks;
    }

    @Override
    protected void setupRotations(HatDisplayRenderState state, PoseStack matrixStack, float rotationYaw, float entityScale) {
        matrixStack.mulPose(Axis.YP.rotationDegrees(180.0F - rotationYaw));
        float f = state.hurtAnim;
        if(f < 5.0F) matrixStack.mulPose(Axis.YP.rotationDegrees(Mth.sin(f / 1.5F * (float)Math.PI) * 3.0F));
    }

    @Override
    protected boolean shouldShowName(HatDisplay entity, double distanceToCameraSq) {
        double d0 = this.entityRenderDispatcher.distanceToSqr(entity);
        return d0 < 4096 && entity.hasCustomName();
    }
}