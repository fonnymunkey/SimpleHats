package fonnymunkey.simplehats.common.entity;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
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
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.Vec3;

public class HatDisplay extends LivingEntity {
    private final NonNullList<ItemStack> hatItemSlots = NonNullList.withSize(1, ItemStack.EMPTY);
    public static final EntityDataAccessor<Byte> DATA_CLIENT_FLAGS = SynchedEntityData.defineId(HatDisplay.class, EntityDataSerializers.BYTE);
    public long lastHit;

    public HatDisplay(EntityType<? extends HatDisplay> type, Level level) {
        super(type, level);
    }

    public HatDisplay(Level level, double x, double y, double z) {
        this(ModRegistry.HATDISPLAYENTITY.get(), level);
        this.setPos(x, y, z);
    }

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

    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return this.hatItemSlots.get(0);
    }

    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if(!stack.isEmpty() && !(stack.getItem() instanceof HatItem)) {
            SimpleHats.logger.log(org.apache.logging.log4j.Level.ERROR, "Attempted to place non-hat item \"" + stack.getDisplayName() + "\" on hat display stand");
            return;
        }
        this.verifyEquippedItem(stack);
        super.onEquipItem(EquipmentSlot.HEAD, this.hatItemSlots.set(0, stack), stack);
    }

    public boolean canTakeItem(ItemStack itemStack) {
        return this.getItemBySlot(null).isEmpty();
    }

    public Iterable<ItemStack> getArmorSlots() { return this.hatItemSlots; }
    
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        ListTag listTag = new ListTag();

        ItemStack itemStack = this.hatItemSlots.get(0);
        CompoundTag compoundTag = new CompoundTag();
        if(!itemStack.isEmpty()) itemStack.save(compoundTag);
        listTag.add(compoundTag);

        compound.put("HatItem", listTag);
    }

    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        if(compound.contains("HatItem", 9)) {
            ListTag listTag = compound.getList("HatItem", 10);
            this.hatItemSlots.set(0, ItemStack.of(listTag.getCompound(0)));
        }
    }

    public boolean isPushable() {
        return false;
    }

    protected void doPush(Entity entity) { }

    protected void pushEntities() { }
    public InteractionResult interactAt(Player player, Vec3 vec3, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if(!itemStack.is(Items.NAME_TAG)) {
            if(player.isSpectator()) {
                return InteractionResult.SUCCESS;
            }
            else if(player.level().isClientSide) {
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
        if(player.getAbilities().instabuild && itemStack.isEmpty() && !stack.isEmpty()) {
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

    public boolean hurt(DamageSource source, float amount) {
        if(!this.level().isClientSide && !this.isRemoved()) {
            if(source.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.kill();
                return false;
            }
            else if(!this.isInvulnerableTo(source)) {
                if(source.is(DamageTypeTags.IS_EXPLOSION)) {
                    this.brokenByAnything(source);
                    this.kill();
                    return false;
                }
                else if(source.is(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
                    if(this.isOnFire()) {
                        this.causeDamage(source, 0.15F);
                    }
                    else {
                        this.setSecondsOnFire(5);
                    }
                    return false;
                }
                else if(source.is(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
                    this.causeDamage(source, 4.0F);
                    return false;
                }
                else {
                    boolean flag = source.getDirectEntity() instanceof AbstractArrow;
                    boolean flag1 = flag && ((AbstractArrow)source.getDirectEntity()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(source.getMsgId());
                    if(!flag2 && !flag) {
                        return false;
                    }
                    else if(source.getEntity() instanceof Player && !((Player)source.getEntity()).getAbilities().mayBuild) {
                        return false;
                    }
                    else if(source.isCreativePlayer()) {
                        this.playBrokenSound();
                        this.showBreakingParticles();
                        this.kill();
                        return flag1;
                    }
                    else {
                        long i = this.level().getGameTime();
                        if(i - this.lastHit > 5L && !flag) {
                            this.level().broadcastEntityEvent(this, (byte)32);
                            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
                            this.lastHit = i;
                        }
                        else {
                            this.brokenByPlayer(source);
                            this.showBreakingParticles();
                            this.kill();
                        }
                        return true;
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

    public void handleEntityEvent(byte id) {
        if(id == 32) {
            if(this.level().isClientSide) {
                this.level().playLocalSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_HIT, this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level().getGameTime();
            }
        }
        else {
            super.handleEntityEvent(id);
        }
    }

    private void showBreakingParticles() {
        if(this.level() instanceof ServerLevel) {
            ((ServerLevel)this.level()).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.66D), this.getZ(), 10, (double)(this.getBbWidth() / 4.0F), (double)(this.getBbHeight() / 4.0F), (double)(this.getBbWidth() / 4.0F), 0.05D);
        }
    }

    private void causeDamage(DamageSource source, float dmg) {
        float f = this.getHealth() - dmg;
        if(f <= 0.5F) {
            this.brokenByAnything(source);
            this.kill();
        }
        else {
            this.setHealth(f);
            this.gameEvent(GameEvent.ENTITY_DAMAGE, source.getEntity());
        }
    }

    private void brokenByPlayer(DamageSource source) {
        Block.popResource(this.level(), this.blockPosition(), new ItemStack(ModRegistry.HATDISPLAYITEM.get()));
        this.brokenByAnything(source);
    }

    private void brokenByAnything(DamageSource source) {
        this.playBrokenSound();
        this.dropAllDeathLoot(source);

        ItemStack itemStack = this.hatItemSlots.get(0);
        if(!itemStack.isEmpty()) {
            Block.popResource(this.level(), this.blockPosition().above(), itemStack);
            this.hatItemSlots.set(0, ItemStack.EMPTY);
        }
    }

    private void playBrokenSound() {
        this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ARMOR_STAND_BREAK, this.getSoundSource(), 1.0F, 1.0F);
    }

    protected float tickHeadTurn(float f1, float f2) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.getYRot();
        return 0.0F;
    }

    public double getMyRidingOffset() {
        return 0.1D;
    }

    public void setYBodyRot(float offset) {
        this.yBodyRotO = this.yRotO = offset;
        this.yHeadRotO = this.yHeadRot = offset;
    }

    public void setYHeadRot(float rotation) {
        this.yBodyRotO = this.yRotO = rotation;
        this.yHeadRotO = this.yHeadRot = rotation;
    }

    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
    }

    public boolean skipAttackInteraction(Entity entity) {
        return entity instanceof Player && !this.level().mayInteract((Player)entity, this.blockPosition());
    }

    public LivingEntity.Fallsounds getFallSounds() {
        return new LivingEntity.Fallsounds(SoundEvents.ARMOR_STAND_FALL, SoundEvents.ARMOR_STAND_FALL);
    }

    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ARMOR_STAND_HIT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ARMOR_STAND_BREAK;
    }

    public void thunderHit(ServerLevel pLevel, LightningBolt pLightning) { }

    public boolean isAffectedByPotions() {
        return false;
    }

    public boolean attackable() {
        return false;
    }

    public EntityDimensions getDimensions(Pose pose) {
        return this.getType().getDimensions();
    }

    public ItemStack getPickResult() {
        return new ItemStack(ModRegistry.HATDISPLAYITEM.get());
    }

    public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
        if(DATA_CLIENT_FLAGS.equals(pKey)) {
            this.refreshDimensions();
            this.blocksBuilding = true;
        }
        super.onSyncedDataUpdated(pKey);
    }

    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_CLIENT_FLAGS, (byte)0);
    }
}