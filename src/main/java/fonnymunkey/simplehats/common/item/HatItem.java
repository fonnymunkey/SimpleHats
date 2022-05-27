package fonnymunkey.simplehats.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModItems;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatParticleSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.awt.*;

public class HatItem extends Item implements ICurioItem, ICurioRenderer{

    private HatEntry hatEntry;

    public HatItem(HatEntry entry) {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(ModItems.TAB)
                .rarity(entry.getHatRarity()));
        this.setRegistryName(entry.getHatName());
        this.hatEntry = entry;
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
        matrixStack.pushPose();

        translateToHead(matrixStack, slotContext.entity(), netHeadYaw, headPitch);
        matrixStack.translate(0D, 0.0D, 0.3D);
        matrixStack.scale(0.66F, 0.66F, 0.66F);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));

        BakedModel model = renderer.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(SimpleHats.modId + ":" + this.hatEntry.getHatName() + "#inventory"));
        if(stack.getTag() != null && stack.getTag().getInt("CustomModelData") != 0) renderer.render(stack, ItemTransforms.TransformType.HEAD, false, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, model.getOverrides().resolve(model, stack, (ClientLevel) slotContext.entity().level, slotContext.entity(), 0));
        else renderer.render(stack, ItemTransforms.TransformType.HEAD, false, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, model);

        matrixStack.popPose();

        HatParticleSettings particleSettings = this.hatEntry.getHatParticleSettings();
        if(particleSettings.getUseParticles()) {
            //TODO positioning, speed, direction, etc
            Particle particle = Minecraft.getInstance().particleEngine.createParticle(particleSettings.getParticleType(), slotContext.entity().getX(), slotContext.entity().getY(), slotContext.entity().getZ(), 0, 0 ,0);
            if(particleSettings.getUseColor()) {
                Color color = new Color(particleSettings.getColorCode());
                particle.setColor(color.getRed(), color.getGreen(), color.getBlue());
                //Random rand = slotContext.entity().getRandom();
                //particle.setColor(rand.nextFloat(), rand.nextFloat(), rand.nextFloat());
            }
        }
    }

    /*
    private static void renderFire(PoseStack poseStack, MultiBufferSource bufferSource, Entity entity) {
        try {
            Method methodFire = EntityRenderDispatcher.class.getDeclaredMethod("renderFlame", PoseStack.class, MultiBufferSource.class, Entity.class);
            methodFire.setAccessible(true);
            methodFire.invoke(Minecraft.getInstance().getEntityRenderDispatcher(), poseStack, bufferSource, entity);
        }
        catch(Exception ex) {
            System.out.println("Failed rendering fire: " + ex);
        }
    }
     */

    private static void translateToHead(PoseStack poseStack, LivingEntity entity, float headYaw, float headPitch) {
        if(entity.isVisuallySwimming() || entity.isFallFlying()) {
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(entity.yHeadRot));
            poseStack.mulPose(Vector3f.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(-45.0F));
        }
        else {
            if(entity.isCrouching()) {
                poseStack.translate(0.0F, 0.25F, 0.0F);
            }
            poseStack.mulPose(Vector3f.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(headPitch));
        }
        poseStack.translate(0.0F, -0.25F, -0.3F);
    }
}
