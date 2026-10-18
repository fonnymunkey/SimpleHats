package fonnymunkey.simplehats.compat.baubles;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import baubles.api.cap.BaublesCapabilities;
import baubles.api.cap.IBaublesItemHandler;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.compat.ISlotCompat;
import fonnymunkey.simplehats.handlers.ForgeConfigHandler;
import fonnymunkey.simplehats.item.HatItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class BaublesCompat implements ISlotCompat {

    private static final ResourceLocation CAPABILITY_KEY = new ResourceLocation(SimpleHats.MODID, "bauble");

    private static final IBauble HAT_BAUBLE = new IBauble() {
        @Override
        public BaubleType getBaubleType(ItemStack stack) {
            return BaubleType.HEAD;
        }
    };

    private static final ICapabilityProvider HAT_PROVIDER = new ICapabilityProvider() {
        @Override
        public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE;
        }

        @Nullable
        @Override
        public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
            return capability == BaublesCapabilities.CAPABILITY_ITEM_BAUBLE ? BaublesCapabilities.CAPABILITY_ITEM_BAUBLE.cast(HAT_BAUBLE) : null;
        }
    };

    private final Map<Integer, ItemStack> keptHats = new HashMap<>();

    public BaublesCompat() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void attachCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() instanceof HatItem) event.addCapability(CAPABILITY_KEY, HAT_PROVIDER);
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerDropsPre(PlayerDropsEvent event) {
        this.keptHats.clear();
        if(!ForgeConfigHandler.server.keepHatOnDeath) return;
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(event.getEntityPlayer());
        if(handler == null) return;
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if(stack.getItem() instanceof HatItem) {
                this.keptHats.put(i, stack);
                handler.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST, receiveCanceled = true)
    public void onPlayerDropsPost(PlayerDropsEvent event) {
        if(this.keptHats.isEmpty()) return;
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(event.getEntityPlayer());
        if(handler != null) {
            for(Map.Entry<Integer, ItemStack> entry : this.keptHats.entrySet()) {
                handler.setStackInSlot(entry.getKey(), entry.getValue());
            }
        }
        this.keptHats.clear();
    }

    @Override
    public ItemStack getVisibleHat(EntityPlayer entity) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(entity);
        if(handler == null) return ItemStack.EMPTY;
        for(int i = 0; i < handler.getSlots(); i++) {
            ItemStack stack = handler.getStackInSlot(i);
            if(stack.getItem() instanceof HatItem) return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public boolean tryEquip(EntityPlayer player, ItemStack stack) {
        IBaublesItemHandler handler = BaublesApi.getBaublesHandler(player);
        if(handler == null) return false;
        for(int i = 0; i < handler.getSlots(); i++) {
            if(handler.getStackInSlot(i).isEmpty() && handler.isItemValidForSlot(i, stack, player)) {
                if(!player.world.isRemote) {
                    ItemStack equipped = stack.copy();
                    equipped.setCount(1);
                    handler.setStackInSlot(i, equipped);
                    stack.shrink(1);
                }
                player.playSound(SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }
}
