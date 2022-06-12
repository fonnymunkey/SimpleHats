package fonnymunkey.simplehats.common.item;

import fonnymunkey.simplehats.common.entity.HatDisplay;
import fonnymunkey.simplehats.common.init.ModRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class HatDisplayItem extends Item {

    public HatDisplayItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        Direction direction = context.getClickedFace();
        if(direction == Direction.DOWN) {
            return InteractionResult.FAIL;
        }
        else {
            Level level = context.getLevel();
            BlockPlaceContext blockPlaceContext = new BlockPlaceContext(context);
            BlockPos pos = blockPlaceContext.getClickedPos();
            ItemStack itemStack = context.getItemInHand();
            Vec3 vec3 = Vec3.atBottomCenterOf(pos);
            AABB aabb = ModRegistry.HATDISPLAYENTITY.get().getDimensions().makeBoundingBox(vec3.x(), vec3.y(), vec3.z());
            if(level.noCollision(null, aabb) && level.getEntities(null, aabb).isEmpty()) {
                if(level instanceof ServerLevel serverLevel) {
                    HatDisplay hatDisplay = ModRegistry.HATDISPLAYENTITY.get().create(serverLevel, itemStack.getTag(), null, context.getPlayer(), pos, MobSpawnType.SPAWN_EGG, true, true);
                    if(hatDisplay == null) return InteractionResult.FAIL;

                    float f = 0;
                    if(context.getPlayer() != null && context.getPlayer().isCrouching()) f = (float)Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
                    else f = (int)Mth.wrapDegrees(context.getRotation() - 180.0F);

                    hatDisplay.moveTo(hatDisplay.getX(), hatDisplay.getY(), hatDisplay.getZ(), f, 0.0F);
                    serverLevel.addFreshEntityWithPassengers(hatDisplay);
                    level.playSound(null, hatDisplay.getX(), hatDisplay.getY(), hatDisplay.getZ(), SoundEvents.ARMOR_STAND_PLACE, SoundSource.BLOCKS, 0.75F, 0.8F);
                    level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, hatDisplay.blockPosition());
                }
                itemStack.shrink(1);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            else {
                return InteractionResult.FAIL;
            }
        }
    }
}
