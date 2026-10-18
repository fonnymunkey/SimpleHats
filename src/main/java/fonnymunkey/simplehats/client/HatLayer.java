package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.handlers.ForgeConfigHandler;
import fonnymunkey.simplehats.item.HatItem;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumParticleTypes;

public class HatLayer implements LayerRenderer<EntityPlayer> {

    private final RenderPlayer renderer;

    public HatLayer(RenderPlayer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void doRenderLayer(EntityPlayer player, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        Minecraft mc = Minecraft.getMinecraft();
        if(player == mc.getRenderViewEntity() && mc.gameSettings.thirdPersonView == 0 && ForgeConfigHandler.client.forceFirstPersonNoRender) return;

        ItemStack stack = SimpleHats.SLOT_COMPAT.getVisibleHat(player);
        if(stack.isEmpty()) {
            ItemStack helmet = player.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
            if(helmet.getItem() instanceof HatItem) spawnParticles(helmet, player);
            return;
        }

        if(!player.isInvisible()) {
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.01F, 1.01F, 1.01F);
            GlStateManager.translate(0.0F, -(float)ForgeConfigHandler.client.hatYOffset, 0.0F);
            if(player.isSneaking()) GlStateManager.translate(0.0F, 0.2F, 0.0F);
            this.renderer.getMainModel().bipedHead.postRender(0.0625F);
            GlStateManager.translate(0.0F, -0.25F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.scale(0.625F, -0.625F, -0.625F);
            mc.getItemRenderer().renderItem(player, stack, ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
        spawnParticles(stack, player);
    }

    private static void spawnParticles(ItemStack stack, EntityPlayer player) {
        HatEntry.HatParticleSettings settings = ((HatItem)stack.getItem()).getHatEntry().getHatParticleSettings();
        if(!settings.getUseParticles() || Minecraft.getMinecraft().isGamePaused()) return;
        if(player.getRNG().nextFloat() >= (player.isInvisible() ? settings.getParticleFrequency() / 2 : settings.getParticleFrequency())) return;

        double y;
        switch(settings.getParticleMovement()) {
            case TRAILING_HEAD: y = player.posY + 1.75D; break;
            case TRAILING_FEET: y = player.posY + 0.25D; break;
            default: y = player.posY + player.getRNG().nextDouble() * player.height; break;
        }
        double x = player.posX + player.getRNG().nextDouble() - 0.5D;
        double z = player.posZ + player.getRNG().nextDouble() - 0.5D;
        EnumParticleTypes type = settings.getParticleType();
        if(type == EnumParticleTypes.SPELL_MOB || type == EnumParticleTypes.SPELL_MOB_AMBIENT) {
            player.world.spawnParticle(type, x, y, z, player.getRNG().nextDouble(), player.getRNG().nextDouble(), player.getRNG().nextDouble());
        }
        else {
            player.world.spawnParticle(type, x, y, z, player.getRNG().nextGaussian() * 0.02D, player.getRNG().nextGaussian() * 0.02D, player.getRNG().nextGaussian() * 0.02D);
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}
