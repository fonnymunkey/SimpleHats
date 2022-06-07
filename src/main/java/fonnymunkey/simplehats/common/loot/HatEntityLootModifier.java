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

public class HatEntityLootModifier extends LootModifier {
    @ObjectHolder(SimpleHats.modId + ":hat_lootinject_entity")
    public static GlobalLootModifierSerializer<HatEntityLootModifier> serializer = null;

    public static final ResourceLocation INJECTABLE_LOOT = new ResourceLocation(SimpleHats.modId, "inject/hatbag_entity");

    public HatEntityLootModifier(final LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public List<ItemStack> doApply(List<ItemStack> loot, LootContext context) {
        //loot.addAll(context.getLootTable(INJECTABLE_LOOT).getRandomItems(context)); //infinite loop
        context.getLootTable(INJECTABLE_LOOT).getRandomItems(context, loot::add);
        return loot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<HatEntityLootModifier> {
        @Override
        public HatEntityLootModifier read(ResourceLocation location, JsonObject object, LootItemCondition[] conditions) {
            return new HatEntityLootModifier(conditions);
        }

        @Override
        public JsonObject write(HatEntityLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
