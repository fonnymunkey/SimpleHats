package fonnymunkey.simplehats.common.loot;

import com.google.gson.JsonObject;
import fonnymunkey.simplehats.SimpleHats;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

public class HatEntityLootModifier extends LootModifier {

    public static final ResourceLocation INJECTABLE_LOOT = new ResourceLocation(SimpleHats.modId, "inject/hatbag_entity");

    public HatEntityLootModifier(final LootItemCondition[] conditions) {
        super(conditions);
    }

    @Override
    public ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext context) {
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
