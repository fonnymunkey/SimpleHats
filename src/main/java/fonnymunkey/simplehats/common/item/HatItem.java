package fonnymunkey.simplehats.common.item;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.ModConfig;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatParticleSettings;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurio;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import java.util.List;
import java.util.Objects;


public class HatItem extends Item implements ICurioItem, ICurioRenderer {

    private HatEntry hatEntry;
    private BakedModel hatModel;

    public HatItem(HatEntry entry) {
        super(new Item.Properties()
                .stacksTo(1)
                .tab(ModRegistry.HAT_TAB)
                .rarity(entry.getHatRarity())
                .fireResistant());
        this.hatEntry = entry;
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, Level level, List<Component> tooltip, TooltipFlag flag) {
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatVariantRange()>0) tooltip.add(Component.translatable("tooltip.simplehats.variant"));
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatName().equalsIgnoreCase("special")) {
            if(itemStack.getTag()!=null && itemStack.getTag().getInt("CustomModelData") > 0) {
                tooltip.add(Component.translatable("tooltip.simplehats.special_true"));
            }
            else {
                tooltip.add(Component.translatable("tooltip.simplehats.special_false"));
            }
        }
    }

    @Override
    public ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        if(slotContext.entity() instanceof Player && ModConfig.COMMON.keepHatOnDeath.get()) return ICurio.DropRule.ALWAYS_KEEP;
        else return defaultInstance.getDropRule(slotContext, source, lootingLevel, recentlyHit);
    }

    @Override
    public boolean canEquipFromUse(SlotContext slotContext, ItemStack stack) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrixStack, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

        //Hopefully fixes hats rendering over the camera when using the first person render mod?
        if(slotContext.entity() == Minecraft.getInstance().cameraEntity && Minecraft.getInstance().options.getCameraType() == CameraType.FIRST_PERSON && ModConfig.CLIENT.forceFirstPersonNoRender.get()) return;

        if(!slotContext.entity().isInvisible()) {
            matrixStack.pushPose();
            if(slotContext.entity() instanceof HatDisplay) matrixStack.translate(0D, 0.97D, 0.0D);
            else translateToHead(matrixStack, slotContext.entity(), netHeadYaw, headPitch);
            matrixStack.scale(0.66F, 0.66F, 0.66F);
            matrixStack.mulPose(Vector3f.XP.rotationDegrees(180.0F));
            matrixStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
            if(hatModel == null) hatModel = renderer.getItemModelShaper().getModelManager().getModel(new ModelResourceLocation(SimpleHats.modId + ":" + this.hatEntry.getHatName() + "#inventory"));
            if(stack.getTag() != null && stack.getTag().getInt("CustomModelData") != 0) renderer.render(stack, ItemTransforms.TransformType.HEAD, false, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, Objects.requireNonNullElse(hatModel.getOverrides().resolve(hatModel, stack, (ClientLevel)slotContext.entity().level, slotContext.entity(), 0), hatModel));
            else renderer.render(stack, ItemTransforms.TransformType.HEAD, false, matrixStack, renderTypeBuffer, light, OverlayTexture.NO_OVERLAY, hatModel);
            matrixStack.popPose();
        }
        if(slotContext.entity() instanceof Player) {
            HatParticleSettings particleSettings = this.hatEntry.getHatParticleSettings();
            if(particleSettings.getUseParticles() && !Minecraft.getInstance().isPaused() && slotContext.entity().getRandom().nextFloat() < (slotContext.entity().isInvisible() ? particleSettings.getParticleFrequency()/2 : particleSettings.getParticleFrequency())) {
                double d0 = slotContext.entity().getRandom().nextGaussian() * 0.02D,
                        d1 = slotContext.entity().getRandom().nextGaussian() * 0.02D,
                        d2 = slotContext.entity().getRandom().nextGaussian() * 0.02D,
                        y = switch(particleSettings.getParticleMovement()) {
                            case TRAILING_HEAD -> slotContext.entity().getY()+1.75;
                            case TRAILING_FEET -> slotContext.entity().getY()+0.25;
                            case TRAILING_FULL -> slotContext.entity().getRandomY();
                        };
                slotContext.entity().level.addParticle(particleSettings.getParticleType(), slotContext.entity().getRandomX(0.5D), y, slotContext.entity().getRandomZ(0.5D), d0, d1,d2);
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
            if(entity.isCrouching()) poseStack.translate(0.0F, 0.25F, 0.0F);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(headYaw));
            poseStack.mulPose(Vector3f.XP.rotationDegrees(headPitch));
        }
        poseStack.translate(0.0F, -0.25F - ModConfig.CLIENT.hatYOffset.get(), 0.0F);
    }
}
