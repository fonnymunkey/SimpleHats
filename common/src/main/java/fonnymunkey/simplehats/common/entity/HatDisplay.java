package fonnymunkey.simplehats.common.entity;

import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.item.HatItem;

import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

public class HatDisplay extends LivingEntity {
    private final NonNullList<ItemStack> hatItemSlots = NonNullList.withSize(1, ItemStack.EMPTY);
    public static final EntityDataAccessor<Byte> DATA_CLIENT_FLAGS = SynchedEntityData.defineId(HatDisplay.class, EntityDataSerializers.BYTE);
    public long lastHit;

    public HatDisplay(EntityType<? extends HatDisplay> type, Level level) {
        super(type, level);
    }

    public HatDisplay(Level level, double x, double y, double z) {
        this(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayEntity(), level);
        this.setPos(x, y, z);
    }

    @Override
    public void refreshDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.refreshDimensions();
        this.setPos(d0, d1, d2);
    }
    
    public static AttributeSupplier.Builder createAttributes() {
        return LivingEntity.createLivingAttributes().add(Attributes.MAX_HEALTH, 5D).add(Attributes.MOVEMENT_SPEED, 0D);
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return this.hatItemSlots.get(0);
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if(!stack.isEmpty() && !(stack.getItem() instanceof HatItem)) {
            Constants.LOG.error( "Attempted to place non-hat item \"" + stack.getItem().getName(stack) + "\" on hat display stand");
            return;
        }

        this.onEquipItem(EquipmentSlot.HEAD, this.hatItemSlots.set(0, stack), stack);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);

        ItemStack itemStack = this.hatItemSlots.get(0);
        output.list("HatItem", ItemStack.CODEC)
            .add(itemStack);
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        input.list("HatItem", ItemStack.CODEC).ifPresent(list -> {
            this.hatItemSlots.set(0, list.stream().findFirst().orElse(ItemStack.EMPTY));
        });
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
    
    }

    @Override
    protected void pushEntities() {
    
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand, Vec3 location) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(!itemStack.is(Items.NAME_TAG)) {
            if(player.isSpectator()) {
                return InteractionResult.SUCCESS;
            }
            else if(player.level().isClientSide()) {
                return InteractionResult.CONSUME;
            }
            else {
                if(this.swapItem(player, itemStack, hand)) {
                    return InteractionResult.SUCCESS;
                }
                return InteractionResult.PASS;
            }
        }
        else {
            return InteractionResult.PASS;
        }
    }

    private boolean swapItem(Player player, ItemStack stack, InteractionHand hand) {
        ItemStack itemStack = this.getItemBySlot(null);
        if(!stack.isEmpty() && !(stack.getItem() instanceof HatItem)) return false;
        if(player.hasInfiniteMaterials() && itemStack.isEmpty() && !stack.isEmpty()) {
            ItemStack itemStack2 = stack.copy();
            itemStack2.setCount(1);
            this.setItemSlot(null, itemStack2);
            return true;
        }
        else if(!stack.isEmpty() && stack.getCount() > 1) {
            if(!itemStack.isEmpty()) {
                return false;
            }
            else {
                ItemStack itemStack1 = stack.copy();
                itemStack1.setCount(1);
                this.setItemSlot(null, itemStack1);
                stack.shrink(1);
                return true;
            }
        }
        else {
            this.setItemSlot(null, stack);
            player.setItemInHand(hand, itemStack);
            return true;
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        if(!this.isRemoved()) {
            if(this.level() instanceof ServerLevel serverLevel) {
                if(source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                    this.kill(level);
                    return false;
                }
                else if(!this.isInvulnerableTo(level, source)) {
                    if(source.is(DamageTypeTags.IS_EXPLOSION)) {
                        this.onBreak(serverLevel, source);
                        this.kill(level);
                        return false;
                    }
                    else if(source.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
                        if(this.isOnFire()) {
                            this.updateHealth(serverLevel, source, 0.15F);
                        }
                        else {
                            this.igniteForSeconds(5);
                        }
                        return false;
                    }
                    else if(source.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
                        this.updateHealth(serverLevel, source, 4.0F);
                        return false;
                    }
                    else {
                        boolean flag1 = source.is(DamageTypeTags.CAN_BREAK_ARMOR_STAND);
                        boolean flag = source.is(DamageTypeTags.ALWAYS_KILLS_ARMOR_STANDS);
                        if (!flag1 && !flag) {
                            return false;
                        } else {
                            Entity var7 = source.getEntity();
                            if (var7 instanceof Player) {
                                Player player = (Player)var7;
                                if (!player.getAbilities().mayBuild) {
                                    return false;
                                }
                            }
                            
                            if (source.isCreativePlayer()) {
                                this.playBreakSound();
                                this.spawnBreakParticles();
                                this.kill(level);
                                return true;
                            } else {
                                long i = serverLevel.getGameTime();
                                if (i - this.lastHit > 5L && !flag) {
                                    serverLevel.broadcastEntityEvent(this, (byte)32);
                                    this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
                                    this.lastHit = i;
                                } else {
                                    this.breakAndDropItem(serverLevel, source);
                                    this.spawnBreakParticles();
                                    this.kill(level);
                                }
                                
                                return true;
                            }
                        }
                    }
                }
                else {
                    return false;
                }
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }
    }

    @Override
    public void handleEntityEvent(byte id) {
        if(id == 32) {
            if(this.level().isClientSide()) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level().getGameTime();
            }
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    private void spawnBreakParticles() {
        if(this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.66D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
        }
    }

    private void updateHealth(ServerLevel level, DamageSource source, float dmg) {
        float f = this.getHealth() - dmg;
        if(f <= 0.5F) {
            this.onBreak(level, source);
            this.kill(level);
        }
        else {
            this.setHealth(f);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
        }
    }

    private void breakAndDropItem(ServerLevel level, DamageSource source) {
        Block.popResource(this.level(), this.blockPosition(), new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayItem()));
        this.onBreak(level, source);
    }

    private void onBreak(ServerLevel level, DamageSource source) {
        this.playBreakSound();
        this.dropAllDeathLoot(level, source);

        ItemStack itemStack = this.hatItemSlots.get(0);
        if(!itemStack.isEmpty()) {
            Block.popResource(this.level(), this.blockPosition().above(), itemStack);
            this.hatItemSlots.set(0, ItemStack.EMPTY);
        }
    }

    private void playBreakSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    @Override
    protected void tickHeadTurn(float yBodyRotT) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
    }
    
    @Override
    public Vec3 getVehicleAttachmentPoint(Entity vehicle) {
        return new Vec3(0.0, 0.1, 0.0);
    }

    @Override
    public void setYBodyRot(float offset) {
        this.yBodyRotO = this.yRotO = offset;
        this.yHeadRotO = this.yHeadRot = offset;
    }

    @Override
    public void setYHeadRot(float rotation) {
        this.yBodyRotO = this.yRotO = rotation;
        this.yHeadRotO = this.yHeadRot = rotation;
    }

    @Override
    public void kill(ServerLevel level) {
        this.remove(Entity.RemovalReason.KILLED);
    }

    @Override
    public boolean skipAttackInteraction(Entity entity) {
        return entity instanceof Player && !this.level().mayInteract((Player)entity, this.blockPosition());
    }

    @Override
    public LivingEntity.Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.ARMOR_STAND_FALL, SoundEvents.ARMOR_STAND_FALL);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ARMOR_STAND_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMOR_STAND_BREAK;
    }

    @Override
    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) { }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public EntityDimensions getDefaultDimensions(Pose pose) {
        return this.getType().getDimensions();
    }

    @Override
    public ItemStack getPickResult() {
        return new ItemStack(SimpleHatsCommon.MOD_REGISTRY.getHatDisplayItem());
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if(DATA_CLIENT_FLAGS.equals(pKey)) {
            this.refreshDimensions();
            this.blocksBuilding = true;
        }
        super.onSyncedDataUpdated(pKey);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_CLIENT_FLAGS, (byte)0);
    }
}