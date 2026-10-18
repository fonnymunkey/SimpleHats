package fonnymunkey.simplehats.item;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.entity.HatDisplay;
import fonnymunkey.simplehats.handlers.ModRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class HatDisplayItem extends Item {

    public HatDisplayItem() {
        this.setRegistryName(SimpleHats.MODID, "hatdisplay");
        this.setTranslationKey(SimpleHats.MODID + ".hatdisplay");
        this.setCreativeTab(ModRegistry.HAT_TAB);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(facing == EnumFacing.DOWN) return EnumActionResult.FAIL;
        BlockPos placePos = world.getBlockState(pos).getBlock().isReplaceable(world, pos) ? pos : pos.offset(facing);
        ItemStack stack = player.getHeldItem(hand);
        if(!player.canPlayerEdit(placePos, facing, stack)) return EnumActionResult.FAIL;
        if(!world.isAirBlock(placePos) && !world.getBlockState(placePos).getBlock().isReplaceable(world, placePos)) return EnumActionResult.FAIL;

        double x = placePos.getX() + 0.5D;
        double y = placePos.getY();
        double z = placePos.getZ() + 0.5D;
        if(!world.getEntitiesWithinAABBExcludingEntity(null, new AxisAlignedBB(x - 0.375D, y, z - 0.375D, x + 0.375D, y + 0.8125D, z + 0.375D)).isEmpty()) return EnumActionResult.FAIL;

        if(!world.isRemote) {
            world.setBlockToAir(placePos);
            HatDisplay hatDisplay = new HatDisplay(world);
            float yaw = player.isSneaking() ? MathHelper.floor((MathHelper.wrapDegrees(player.rotationYaw - 180.0F) + 22.5F) / 45.0F) * 45.0F : (int)MathHelper.wrapDegrees(player.rotationYaw - 180.0F);
            hatDisplay.setLocationAndAngles(x, y, z, yaw, 0.0F);
            if(stack.hasDisplayName()) hatDisplay.setCustomNameTag(stack.getDisplayName());
            ItemMonsterPlacer.applyItemEntityDataToEntity(world, player, stack, hatDisplay);
            world.spawnEntity(hatDisplay);
            world.playSound(null, hatDisplay.posX, hatDisplay.posY, hatDisplay.posZ, SoundEvents.ENTITY_ARMORSTAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
        }
        stack.shrink(1);
        return EnumActionResult.SUCCESS;
    }
}
