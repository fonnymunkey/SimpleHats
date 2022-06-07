package fonnymunkey.simplehats.common.loot;

import com.google.gson.JsonObject;
import fonnymunkey.simplehats.SimpleHats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.registries.ObjectHolder;

import java.util.List;

public class HatChestLootModifier extends LootModifier {
    @ObjectHolder(SimpleHats.modId + ":hat_lootinject_chest")
    public static GlobalLootModifierSerializer<HatChestLootModifier> serializer = null;

    public static final ResourceLocation INJECTABLE_LOOT_CHEST = new ResourceLocation(SimpleHats.modId, "inject/hatbag_chest");

    public HatChestLootModifier(final LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public List<ItemStack> doApply(List<ItemStack> loot, LootContext context) {
        //loot.addAll(context.getLootTable(INJECTABLE_LOOT).getRandomItems(context)); //infinite loop
        context.getLootTable(INJECTABLE_LOOT_CHEST).getRandomItems(context, loot::add);
        return loot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<HatChestLootModifier> {
        @Override
        public HatChestLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new HatChestLootModifier(conditions);
        }

        @Override
        public JsonObject write(HatChestLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
