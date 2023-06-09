package fonnymunkey.simplehats.common.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketEnums;
import dev.emi.trinkets.api.TrinketItem;
import dev.emi.trinkets.api.client.TrinketRenderer;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.util.HatEntry;
import fonnymunkey.simplehats.util.HatEntry.HatParticleSettings;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.world.World;

import java.util.List;
import java.util.Objects;

public class HatItem extends TrinketItem implements TrinketRenderer {

    private HatEntry hatEntry;
    private BakedModel hatModel;

    public HatItem(HatEntry entry) {
        super(new Item.Settings()
                .maxCount(1)
                //.group(SimpleHats.HAT_TAB)
                .rarity(entry.getHatRarity())
                .fireproof());
        this.hatEntry = entry;
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity.getWorld().isClient()) return;
        /*
        if(stack.getItem() == ModRegistry.HATSPECIAL && entity instanceof PlayerEntity player) {
            NbtCompound tag = stack.getOrCreateNbt();
            tag.putInt("CustomModelData", UUIDHandler.getUUIDMap().getOrDefault(player.getUuidAsString(), 0));
        }
        */
    }
    @Override
    public void appendTooltip(ItemStack itemStack, World level, List<Text> tooltip, TooltipContext flag) {
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatVariantRange()>0) tooltip.add(Text.translatable("tooltip.simplehats.variant"));
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatName().equalsIgnoreCase("special")) {
            if(itemStack.getNbt()!=null && itemStack.getNbt().getInt("CustomModelData") > 0) {
                tooltip.add(Text.translatable("tooltip.simplehats.special_true"));
            }
            else {
                tooltip.add(Text.translatable("tooltip.simplehats.special_false"));
            }
        }
    }

    @Override
    public TrinketEnums.DropRule getDropRule(ItemStack stack, SlotReference slot, LivingEntity entity) {
        if(entity instanceof PlayerEntity && SimpleHats.config.common.keepHatOnDeath) return TrinketEnums.DropRule.KEEP;
        else return TrinketEnums.DropRule.DEFAULT;
    }

    @Override
    public void render(ItemStack stack, SlotReference slotContext, EntityModel<? extends LivingEntity> renderLayerParent, MatrixStack matrixStack, VertexConsumerProvider renderTypeBuffer, int light, LivingEntity entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        ItemRenderer renderer = MinecraftClient.getInstance().getItemRenderer();

        if(entity == MinecraftClient.getInstance().cameraEntity && MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON && SimpleHats.config.client.forceFirstPersonNoRender) return;

        if(!entity.isInvisible()) {
            matrixStack.push();
            if(entity instanceof HatDisplay) matrixStack.translate(0D, 0.97D, 0.0D);
            else if(entity instanceof AbstractClientPlayerEntity clientEntity && renderLayerParent instanceof PlayerEntityModel layerModel) {
                TrinketRenderer.translateToFace(matrixStack, layerModel, clientEntity, netHeadYaw, headPitch);
                matrixStack.translate(0.0F, 0.0F - SimpleHats.config.client.hatYOffset, 0.31F);
                if(entity.isInSneakingPose()) matrixStack.translate(0.0F, 0.0F, 0.015F);
            }
            matrixStack.scale(0.66F, 0.66F, 0.66F);
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(180.0F));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
            if(hatModel == null) hatModel = renderer.getModel(stack, entity.getWorld(), entity, 0);
            if(stack.getNbt() != null && stack.getNbt().getInt("CustomModelData") != 0) renderer.renderItem(stack, ModelTransformationMode.HEAD, false, matrixStack, renderTypeBuffer, light, OverlayTexture.DEFAULT_UV, Objects.requireNonNullElse(hatModel.getOverrides().apply(hatModel, stack, (ClientWorld)entity.getWorld(), entity, 0), hatModel));
            else renderer.renderItem(stack, ModelTransformationMode.HEAD, false, matrixStack, renderTypeBuffer, light, OverlayTexture.DEFAULT_UV, hatModel);
            matrixStack.pop();
        }
        if(entity instanceof PlayerEntity) {
            HatParticleSettings particleSettings = this.hatEntry.getHatParticleSettings();
            if(particleSettings.getUseParticles() && !MinecraftClient.getInstance().isPaused() && entity.getRandom().nextFloat() < (entity.isInvisible() ? particleSettings.getParticleFrequency()/2 : particleSettings.getParticleFrequency())) {
                double d0 = entity.getRandom().nextGaussian() * 0.02D,
                        d1 = entity.getRandom().nextGaussian() * 0.02D,
                        d2 = entity.getRandom().nextGaussian() * 0.02D,
                        y = switch(particleSettings.getParticleMovement()) {
                            case TRAILING_HEAD -> entity.getY()+1.75;
                            case TRAILING_FEET -> entity.getY()+0.25;
                            case TRAILING_FULL -> entity.getRandomBodyY();
                        };
                entity.getWorld().addParticle(particleSettings.getParticleType(), entity.getX() + entity.getRandom().nextFloat() - 0.5, y, entity.getZ() + entity.getRandom().nextFloat() - 0.5, d0, d1,d2);
            }
        }
    }
}
