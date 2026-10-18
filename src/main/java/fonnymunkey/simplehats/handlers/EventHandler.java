package fonnymunkey.simplehats.handlers;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.item.HatItemDyeable;
import net.minecraft.block.BlockCauldron;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootEntry;
import net.minecraft.world.storage.loot.LootEntryTable;
import net.minecraft.world.storage.loot.LootPool;
import net.minecraft.world.storage.loot.RandomValueRange;
import net.minecraft.world.storage.loot.conditions.LootCondition;
import net.minecraftforge.event.LootTableLoadEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Arrays;
import java.util.List;

@Mod.EventBusSubscriber(modid = SimpleHats.MODID)
public class EventHandler {

    private static final ResourceLocation INJECT_CHEST = new ResourceLocation(SimpleHats.MODID, "inject/hatbag_chest");
    private static final ResourceLocation INJECT_ENTITY = new ResourceLocation(SimpleHats.MODID, "inject/hatbag_entity");

    private static final List<String> CHEST_TABLES = Arrays.asList(
            "minecraft:chests/abandoned_mineshaft",
            "minecraft:chests/desert_pyramid",
            "minecraft:chests/end_city_treasure",
            "minecraft:chests/jungle_temple",
            "minecraft:chests/simple_dungeon",
            "minecraft:chests/spawn_bonus_chest",
            "minecraft:chests/stronghold_library",
            "minecraft:chests/woodland_mansion");

    private static final List<String> ENTITY_TABLES = Arrays.asList(
            "minecraft:entities/blaze",
            "minecraft:entities/cave_spider",
            "minecraft:entities/creeper",
            "minecraft:entities/enderman",
            "minecraft:entities/evocation_illager",
            "minecraft:entities/ghast",
            "minecraft:entities/guardian",
            "minecraft:entities/husk",
            "minecraft:entities/illusion_illager",
            "minecraft:entities/skeleton",
            "minecraft:entities/spider",
            "minecraft:entities/stray",
            "minecraft:entities/vindication_illager",
            "minecraft:entities/witch",
            "minecraft:entities/wither_skeleton",
            "minecraft:entities/zombie",
            "minecraft:entities/zombie_pigman",
            "minecraft:entities/zombie_villager");

    @SubscribeEvent
    public static void onLootTableLoad(LootTableLoadEvent event) {
        String name = event.getName().toString();
        ResourceLocation inject = CHEST_TABLES.contains(name) ? INJECT_CHEST : ENTITY_TABLES.contains(name) ? INJECT_ENTITY : null;
        if(inject == null) return;
        LootEntry entry = new LootEntryTable(inject, 1, 0, new LootCondition[0], SimpleHats.MODID + "_inject");
        event.getTable().addPool(new LootPool(new LootEntry[]{entry}, new LootCondition[0], new RandomValueRange(1), new RandomValueRange(0), SimpleHats.MODID + "_inject"));
    }

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        ItemStack stack = event.getItemStack();
        if(!(stack.getItem() instanceof HatItemDyeable) || !((HatItemDyeable)stack.getItem()).hasColor(stack)) return;
        IBlockState state = event.getWorld().getBlockState(event.getPos());
        if(state.getBlock() != Blocks.CAULDRON) return;
        int level = state.getValue(BlockCauldron.LEVEL);
        if(level <= 0) return;
        if(!event.getWorld().isRemote) {
            ((HatItemDyeable)stack.getItem()).removeColor(stack);
            Blocks.CAULDRON.setWaterLevel(event.getWorld(), event.getPos(), state, level - 1);
            event.getEntityPlayer().addStat(StatList.ARMOR_CLEANED);
        }
        event.setCanceled(true);
        event.setCancellationResult(EnumActionResult.SUCCESS);
    }
}
