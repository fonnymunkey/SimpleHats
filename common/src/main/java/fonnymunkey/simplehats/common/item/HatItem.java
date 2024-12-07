package fonnymunkey.simplehats.common.item;

import com.mojang.blaze3d.vertex.PoseStack;
import fonnymunkey.simplehats.common.init.SimpleHatsConfigAbstract;
import fonnymunkey.simplehats.util.HatEntry;
import io.wispforest.accessories.api.AccessoryItem;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;

import java.util.List;

public class HatItem extends AccessoryItem implements AccessoryRenderer {

    private final HatEntry hatEntry;

    public HatItem(HatEntry entry) {
        super(new Item.Properties()
                .stacksTo(1)
                .rarity(entry.getHatRarity())
                .fireResistant());
        this.hatEntry = entry;
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatVariantRange()>0) tooltipComponents.add(Component.translatable("tooltip.simplehats.variant"));
        if(((HatItem)itemStack.getItem()).getHatEntry().getHatName().equalsIgnoreCase("special")) {
            if(itemStack.has(DataComponents.CUSTOM_MODEL_DATA)) {
                tooltipComponents.add(Component.translatable("tooltip.simplehats.special_true"));
            }
            else {
                tooltipComponents.add(Component.translatable("tooltip.simplehats.special_false"));
            }
        }
    }

    @Override
    public DropRule getDropRule(ItemStack stack, SlotReference slot, DamageSource source) {
        if(slot.entity() instanceof Player && SimpleHatsConfigAbstract.keepHatOnDeath()) return DropRule.KEEP;
        else return DropRule.DEFAULT;
    }
    
    @Override
    public <M extends LivingEntity> void render(ItemStack stack, SlotReference reference, PoseStack matrices, EntityModel<M> model, MultiBufferSource multiBufferSource, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        //Empty, for compatibility with Accessories
    }
    
    @Override
    public boolean canEquipFromUse(ItemStack stack) {
        return true;
    }
}