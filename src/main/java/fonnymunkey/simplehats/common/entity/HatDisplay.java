package fonnymunkey.simplehats.common.entity;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.common.item.HatItem;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.particle.BlockStateParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Arm;
import net.minecraft.util.Hand;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class HatDisplay extends LivingEntity {
    private final DefaultedList<ItemStack> hatItemSlots = DefaultedList.ofSize(1, ItemStack.EMPTY);
    public static final TrackedData<Byte> DATA_CLIENT_FLAGS = DataTracker.registerData(HatDisplay.class, TrackedDataHandlerRegistry.BYTE);
    public long lastHit;

    public HatDisplay(EntityType<? extends HatDisplay> type, World level) {
        super(type, level);
    }

    public HatDisplay(World level, double x, double y, double z) {
        this(ModRegistry.HATDISPLAYENTITY, level);
        this.setPos(x, y, z);
    }

    @Override
    public void calculateDimensions() {
        double d0 = this.getX();
        double d1 = this.getY();
        double d2 = this.getZ();
        super.calculateDimensions();
        this.setPos(d0, d1, d2);
    }

    /*
    public static DefaultAttributeContainer.Builder createLivingAttributes() {
        return DefaultAttributeContainer.builder().add(EntityAttributes.GENERIC_MAX_HEALTH, 5D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0D);
    }
     */

    @Override
    public ItemStack getEquippedStack(EquipmentSlot slot) {
        return this.hatItemSlots.get(0);
    }

    @Override
    public void equipStack(EquipmentSlot slot, ItemStack stack) {
        if(!stack.isEmpty() && !(stack.getItem() instanceof HatItem)) {
            SimpleHats.logger.log(org.apache.logging.log4j.Level.ERROR, "Attempted to place non-hat item \"" + stack.getItem().getName() + "\" on hat display stand");
            return;
        }
        this.processEquippedStack(stack);
        this.onEquipStack(EquipmentSlot.HEAD, (ItemStack)this.hatItemSlots.set(0, stack), stack);
    }

    @Override
    public boolean canEquip(ItemStack itemStack) {
        return this.getEquippedStack(null).isEmpty();
    }

    @Override
    public Iterable<ItemStack> getArmorItems() { return this.hatItemSlots; }

    @Override
    public Arm getMainArm() {
        return Arm.RIGHT;
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound compound) {
        super.writeCustomDataToNbt(compound);
        NbtList listTag = new NbtList();

        ItemStack itemStack = this.hatItemSlots.get(0);
        NbtElement compoundTag = ItemStack.CODEC.encodeStart(NbtOps.INSTANCE, itemStack).mapOrElse(e -> e, $ -> new NbtCompound());
        listTag.add(compoundTag);

        compound.put("HatItem", listTag);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound compound) {
        super.readCustomDataFromNbt(compound);
        if(compound.contains("HatItem", 9)) {
            NbtList listTag = compound.getList("HatItem", 10);
            this.hatItemSlots.set(0, ItemStack.fromNbt(this.getRegistryManager(), listTag.getFirst()).orElse(ItemStack.EMPTY));
        }
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void pushAway(Entity entity) { }

    @Override
    protected void tickCramming() { }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d vec3, Hand hand) {
        ItemStack itemStack = player.getStackInHand(hand);
        if(!itemStack.isOf(Items.NAME_TAG)) {
            if(player.isSpectator()) {
                return ActionResult.SUCCESS;
            }
            else if(player.getWorld().isClient()) {
                return ActionResult.CONSUME;
            }
            else {
                if(this.swapItem(player, itemStack, hand)) {
                    return ActionResult.SUCCESS;
                }
                return ActionResult.PASS;
            }
        }
        else {
            return ActionResult.PASS;
        }
    }

    private boolean swapItem(PlayerEntity player, ItemStack stack, Hand hand) {
        ItemStack itemStack = this.getEquippedStack(null);
        if(!stack.isEmpty() && !(stack.getItem() instanceof HatItem)) return false;
        if(player.getAbilities().creativeMode && itemStack.isEmpty() && !stack.isEmpty()) {
            ItemStack itemStack2 = stack.copy();
            itemStack2.setCount(1);
            this.equipStack(null, itemStack2);
            return true;
        }
        else if(!stack.isEmpty() && stack.getCount() > 1) {
            if(!itemStack.isEmpty()) {
                return false;
            }
            else {
                ItemStack itemStack1 = stack.copy();
                itemStack1.setCount(1);
                this.equipStack(null, itemStack1);
                stack.decrement(1);
                return true;
            }
        }
        else {
            this.equipStack(null, stack);
            player.setStackInHand(hand, itemStack);
            return true;
        }
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if(!this.getWorld().isClient() && !this.isRemoved()) {
            if(source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
                this.kill();
                return false;
            }
            else if(!this.isInvulnerableTo(source)) {
                if(source.isIn(DamageTypeTags.IS_EXPLOSION)) {
                    this.onBreak(source);
                    this.kill();
                    return false;
                }
                else if(source.isIn(DamageTypeTags.IGNITES_ARMOR_STANDS)) {
                    if(this.isOnFire()) {
                        this.updateHealth(source, 0.15F);
                    }
                    else {
                        this.setOnFireFor(5);
                    }
                    return false;
                }
                else if(source.isIn(DamageTypeTags.BURNS_ARMOR_STANDS) && this.getHealth() > 0.5F) {
                    this.updateHealth(source, 4.0F);
                    return false;
                }
                else {
                    boolean flag = source.getSource() instanceof PersistentProjectileEntity;
                    boolean flag1 = flag && ((PersistentProjectileEntity)source.getSource()).getPierceLevel() > 0;
                    boolean flag2 = "player".equals(source.getName());
                    if(!flag2 && !flag) {
                        return false;
                    }
                    else if(source.getAttacker() instanceof PlayerEntity && !((PlayerEntity)source.getAttacker()).getAbilities().allowModifyWorld) {
                        return false;
                    }
                    else if(source.isSourceCreativePlayer()) {
                        this.playBreakSound();
                        this.spawnBreakParticles();
                        this.kill();
                        return flag1;
                    }
                    else {
                        long i = this.getWorld().getTime();
                        if(i - this.lastHit > 5L && !flag) {
                            this.getWorld().sendEntityStatus(this, (byte)32);
                            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
                            this.lastHit = i;
                        }
                        else {
                            this.breakAndDropItem(source);
                            this.spawnBreakParticles();
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

    @Override
    public void handleStatus(byte id) {
        if(id == 32) {
            if(this.getWorld().isClient()) {
                this.getWorld().playSound(this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
                this.lastHit = this.getWorld().getTime();
            }
        }
        else {
            super.handleStatus(id);
        }
    }

    private void spawnBreakParticles() {
        if(this.getWorld() instanceof ServerWorld) {
            ((ServerWorld)this.getWorld()).spawnParticles(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.getDefaultState()), this.getX(), this.getBodyY(0.66D), this.getZ(), 10, (double)(this.getWidth() / 4.0F), (double)(this.getHeight() / 4.0F), (double)(this.getWidth() / 4.0F), 0.05D);
        }
    }

    private void updateHealth(DamageSource source, float dmg) {
        float f = this.getHealth() - dmg;
        if(f <= 0.5F) {
            this.onBreak(source);
            this.kill();
        }
        else {
            this.setHealth(f);
            this.emitGameEvent(GameEvent.ENTITY_DAMAGE, source.getAttacker());
        }
    }

    private void breakAndDropItem(DamageSource source) {
        Block.dropStack(this.getWorld(), this.getBlockPos(), new ItemStack(ModRegistry.HATDISPLAYITEM));
        this.onBreak(source);
    }

    private void onBreak(DamageSource source) {
        this.playBreakSound();
        this.drop((ServerWorld) this.getWorld(), source);

        ItemStack itemStack = this.hatItemSlots.get(0);
        if(!itemStack.isEmpty()) {
            Block.dropStack(this.getWorld(), this.getBlockPos().up(), itemStack);
            this.hatItemSlots.set(0, ItemStack.EMPTY);
        }
    }

    private void playBreakSound() {
        this.getWorld().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.ENTITY_ARMOR_STAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
    }

    @Override
    protected float turnHead(float f1, float f2) {
        this.prevBodyYaw = this.prevYaw;
        this.bodyYaw = this.getYaw();
        return 0.0F;
    }

    @Override
    public Vec3d getVehicleAttachmentPos(Entity vehicle) {
        return new Vec3d(0.0, 0.1, 0.0);
    }

    @Override
    public void setBodyYaw(float offset) {
        this.prevBodyYaw = this.prevYaw = offset;
        this.prevHeadYaw = this.headYaw = offset;
    }

    @Override
    public void setHeadYaw(float rotation) {
        this.prevBodyYaw = this.prevYaw = rotation;
        this.prevHeadYaw = this.headYaw = rotation;
    }

    @Override
    public void kill() {
        this.remove(Entity.RemovalReason.KILLED);
    }

    @Override
    public boolean handleAttack(Entity entity) {
        return entity instanceof PlayerEntity && !this.getWorld().canPlayerModifyAt((PlayerEntity)entity, this.getBlockPos());
    }

    @Override
    public LivingEntity.FallSounds getFallSounds() {
        return new LivingEntity.FallSounds(SoundEvents.ENTITY_ARMOR_STAND_FALL, SoundEvents.ENTITY_ARMOR_STAND_FALL);
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return SoundEvents.ENTITY_ARMOR_STAND_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMOR_STAND_BREAK;
    }

    @Override
    public void onStruckByLightning(ServerWorld pLevel, LightningEntity pLightning) { }

    @Override
    public boolean isAffectedBySplashPotions() {
        return false;
    }

    @Override
    public boolean isMobOrPlayer() {
        return false;
    }

    @Override
    public EntityDimensions getBaseDimensions(EntityPose pose) {
        return this.getType().getDimensions();
    }

    @Override
    public ItemStack getPickBlockStack() {
        return new ItemStack(ModRegistry.HATDISPLAYITEM);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> pKey) {
        if(DATA_CLIENT_FLAGS.equals(pKey)) {
            this.calculateDimensions();
            this.intersectionChecked = true;
        }
        super.onTrackedDataSet(pKey);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(DATA_CLIENT_FLAGS, (byte)0);
    }
}