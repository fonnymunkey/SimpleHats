package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.ModRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.*;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

import java.util.function.Consumer;

public class HatDisplayItem extends Item {

    public HatDisplayItem(Item.Settings properties) {
        super(properties);
    }

    public ActionResult useOnBlock(ItemUsageContext context) {
        Direction direction = context.getSide();
        if(direction == Direction.DOWN) {
            return ActionResult.FAIL;
        }
        else {
            World level = context.getWorld();
            ItemPlacementContext blockPlaceContext = new ItemPlacementContext(context);
            BlockPos pos = blockPlaceContext.getBlockPos();
            ItemStack itemStack = context.getStack();
            Vec3d vec3 = Vec3d.ofBottomCenter(pos);
            Box aabb = ModRegistry.HATDISPLAYENTITY.getDimensions().getBoxAt(vec3.getX(), vec3.getY(), vec3.getZ());
            if(level.isSpaceEmpty(null, aabb) && level.getOtherEntities(null, aabb).isEmpty()) {
                if(level instanceof ServerWorld serverLevel) {
                    Consumer<HatDisplay> consumer = EntityType.nbtCopier(entity -> {}, serverLevel, itemStack, context.getPlayer());
                    HatDisplay hatDisplay = ModRegistry.HATDISPLAYENTITY.create(serverLevel, consumer, pos, SpawnReason.SPAWN_EGG, true, true);
                    if(hatDisplay == null) return ActionResult.FAIL;

                    float f = 0;
                    if(context.getPlayer() != null && context.getPlayer().isSneaking()) f = (float)MathHelper.floor((MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    else f = (int)MathHelper.wrapDegrees(context.getPlayerYaw() - 180.0F);

                    hatDisplay.refreshPositionAndAngles(hatDisplay.getX(), hatDisplay.getY(), hatDisplay.getZ(), f, 0.0F);
                    serverLevel.spawnEntityAndPassengers(hatDisplay);
                    level.playSound(null, hatDisplay.getX(), hatDisplay.getY(), hatDisplay.getZ(), SoundEvents.ENTITY_ARMOR_STAND_PLACE, SoundCategory.BLOCKS, 0.75F, 0.8F);
                    hatDisplay.emitGameEvent(GameEvent.ENTITY_PLACE, context.getPlayer());
                }
                itemStack.decrement(1);
                return ActionResult.success(level.isClient);
            }
            else {
                return ActionResult.FAIL;
            }
        }
    }
}
