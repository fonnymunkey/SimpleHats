package fonnymunkey.simplehats.item;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.handlers.ForgeConfigHandler;
import fonnymunkey.simplehats.handlers.ModRegistry;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class HatItem extends Item {

    private final HatEntry hatEntry;

    public HatItem(HatEntry entry) {
        this.hatEntry = entry;
        this.setRegistryName(SimpleHats.MODID, entry.getHatName());
        this.setTranslationKey(SimpleHats.MODID + "." + entry.getHatName());
        this.setMaxStackSize(1);
        this.setCreativeTab(ModRegistry.HAT_TAB);
        this.addPropertyOverride(new ResourceLocation("custom_model_data"), (stack, world, entity) -> stack.hasTagCompound() ? stack.getTagCompound().getInteger("CustomModelData") : 0);
    }

    public HatEntry getHatEntry() {
        return this.hatEntry;
    }

    @Override
    public EnumRarity getRarity(ItemStack stack) {
        return this.hatEntry.getHatRarity();
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(SimpleHats.SLOT_COMPAT.tryEquip(player, stack)) return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        return super.onItemRightClick(world, player, hand);
    }

    @Nullable
    @Override
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return ForgeConfigHandler.server.allowHatInHelmetSlot ? EntityEquipmentSlot.HEAD : null;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World world, List<String> tooltip, ITooltipFlag flag) {
        if(this.hatEntry.getHatVariantRange() > 0) tooltip.add(I18n.format("tooltip.simplehats.variant"));
        if(this == ModRegistry.HATSPECIAL) tooltip.add(I18n.format("tooltip.simplehats.special_false"));
    }
}
