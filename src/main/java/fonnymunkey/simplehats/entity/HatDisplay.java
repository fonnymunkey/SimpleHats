package fonnymunkey.simplehats.entity;

import fonnymunkey.simplehats.handlers.ModRegistry;
import fonnymunkey.simplehats.item.HatItem;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class HatDisplay extends EntityLivingBase {

    private final NonNullList<ItemStack> hatItemSlots = NonNullList.withSize(1, ItemStack.EMPTY);
    public long lastHit;

    public HatDisplay(World world) {
        super(world);
        this.setSize(0.75F, 0.8125F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.0D);
    }

    @Override
    public Iterable<ItemStack> getArmorInventoryList() {
        return this.hatItemSlots;
    }

    @Override
    public ItemStack getItemStackFromSlot(EntityEquipmentSlot slot) {
        return slot == EntityEquipmentSlot.HEAD ? this.hatItemSlots.get(0) : ItemStack.EMPTY;
    }

    @Override
    public void setItemStackToSlot(EntityEquipmentSlot slot, ItemStack stack) {
        if(slot != EntityEquipmentSlot.HEAD || (!stack.isEmpty() && !(stack.getItem() instanceof HatItem))) return;
        this.playEquipSound(stack);
        this.hatItemSlots.set(0, stack);
    }

    @Override
    public EnumHandSide getPrimaryHand() {
        return EnumHandSide.RIGHT;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        NBTTagList list = new NBTTagList();
        NBTTagCompound tag = new NBTTagCompound();
        if(!this.hatItemSlots.get(0).isEmpty()) this.hatItemSlots.get(0).writeToNBT(tag);
        list.appendTag(tag);
        compound.setTag("HatItem", list);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        if(compound.hasKey("HatItem", 9)) {
            this.hatItemSlots.set(0, new ItemStack(compound.getTagList("HatItem", 10).getCompoundTagAt(0)));
        }
    }

    @Override
    public boolean canBePushed() {
        return false;
    }

    @Override
    protected void collideWithEntity(Entity entity) {
    }

    @Override
    protected void collideWithNearbyEntities() {
    }

    @Override
    public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if(stack.getItem() == Items.NAME_TAG) return EnumActionResult.PASS;
        if(player.isSpectator()) return EnumActionResult.SUCCESS;
        if(this.world.isRemote) return EnumActionResult.SUCCESS;
        return this.swapItem(player, stack, hand) ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
    }

    private boolean swapItem(EntityPlayer player, ItemStack stack, EnumHand hand) {
        ItemStack current = this.getItemStackFromSlot(EntityEquipmentSlot.HEAD);
        if(!stack.isEmpty() && !(stack.getItem() instanceof HatItem)) return false;
        if(player.capabilities.isCreativeMode && current.isEmpty() && !stack.isEmpty()) {
            ItemStack copy = stack.copy();
            copy.setCount(1);
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, copy);
            return true;
        }
        else if(!stack.isEmpty() && stack.getCount() > 1) {
            if(!current.isEmpty()) return false;
            ItemStack copy = stack.copy();
            copy.setCount(1);
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, copy);
            stack.shrink(1);
            return true;
        }
        else {
            this.setItemStackToSlot(EntityEquipmentSlot.HEAD, stack);
            player.setHeldItem(hand, current);
            return true;
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        if(this.world.isRemote || this.isDead) return false;
        if(DamageSource.OUT_OF_WORLD.equals(source)) {
            this.setDead();
            return false;
        }
        if(this.isEntityInvulnerable(source)) return false;
        if(source.isExplosion()) {
            this.dropContents();
            this.setDead();
            return false;
        }
        if(DamageSource.IN_FIRE.equals(source)) {
            if(this.isBurning()) this.damageHatDisplay(0.15F);
            else this.setFire(5);
            return false;
        }
        if(DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F) {
            this.damageHatDisplay(4.0F);
            return false;
        }
        boolean arrow = "arrow".equals(source.getDamageType());
        boolean player = "player".equals(source.getDamageType());
        if(!player && !arrow) return false;
        if(source.getTrueSource() instanceof EntityPlayer && !((EntityPlayer)source.getTrueSource()).capabilities.allowEdit) return false;
        if(source.isCreativePlayer()) {
            this.playBrokenSound();
            this.playParticles();
            this.setDead();
            return false;
        }
        long time = this.world.getTotalWorldTime();
        if(time - this.lastHit > 5L && !arrow) {
            this.world.setEntityState(this, (byte)32);
            this.lastHit = time;
        }
        else {
            Block.spawnAsEntity(this.world, this.getPosition(), new ItemStack(ModRegistry.HATDISPLAY));
            this.dropContents();
            this.playParticles();
            this.setDead();
        }
        return true;
    }

    @Override
    public void handleStatusUpdate(byte id) {
        if(id == 32) {
            if(this.world.isRemote) {
                this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARMORSTAND_HIT, this.getSoundCategory(), 0.3F, 1.0F, false);
                this.lastHit = this.world.getTotalWorldTime();
            }
        }
        else super.handleStatusUpdate(id);
    }

    private void playParticles() {
        if(this.world instanceof WorldServer) {
            ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY + this.height / 1.5D, this.posZ, 10, this.width / 4.0F, this.height / 4.0F, this.width / 4.0F, 0.05D, Block.getStateId(Blocks.PLANKS.getDefaultState()));
        }
    }

    private void damageHatDisplay(float damage) {
        float health = this.getHealth() - damage;
        if(health <= 0.5F) {
            this.dropContents();
            this.setDead();
        }
        else this.setHealth(health);
    }

    private void dropContents() {
        this.playBrokenSound();
        ItemStack stack = this.hatItemSlots.get(0);
        if(!stack.isEmpty()) {
            Block.spawnAsEntity(this.world, this.getPosition().up(), stack);
            this.hatItemSlots.set(0, ItemStack.EMPTY);
        }
    }

    private void playBrokenSound() {
        this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_ARMORSTAND_BREAK, this.getSoundCategory(), 1.0F, 1.0F);
    }

    @Override
    protected float updateDistance(float yaw, float distance) {
        this.prevRenderYawOffset = this.prevRotationYaw;
        this.renderYawOffset = this.rotationYaw;
        return 0.0F;
    }

    @Override
    public double getYOffset() {
        return 0.1D;
    }

    @Override
    public void setRenderYawOffset(float offset) {
        this.prevRenderYawOffset = this.prevRotationYaw = offset;
        this.prevRotationYawHead = this.rotationYawHead = offset;
    }

    @Override
    public void setRotationYawHead(float rotation) {
        this.prevRenderYawOffset = this.prevRotationYaw = rotation;
        this.prevRotationYawHead = this.rotationYawHead = rotation;
    }

    @Override
    public void onKillCommand() {
        this.setDead();
    }

    @Override
    public boolean hitByEntity(Entity entity) {
        return entity instanceof EntityPlayer && !this.world.isBlockModifiable((EntityPlayer)entity, this.getPosition());
    }

    @Override
    protected SoundEvent getFallSound(int height) {
        return SoundEvents.ENTITY_ARMORSTAND_FALL;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_ARMORSTAND_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ARMORSTAND_BREAK;
    }

    @Override
    public void onStruckByLightning(EntityLightningBolt lightning) {
    }

    @Override
    public boolean canBeHitWithPotion() {
        return false;
    }

    @Override
    public boolean attackable() {
        return false;
    }

    @Override
    public ItemStack getPickedResult(RayTraceResult target) {
        return new ItemStack(ModRegistry.HATDISPLAY);
    }
}
