package fonnymunkey.simplehats.common.recipe;

import java.util.ArrayList;
import java.util.List;

import com.mojang.serialization.MapCodec;
import fonnymunkey.simplehats.Constants;
import fonnymunkey.simplehats.SimpleHatsCommon;
import fonnymunkey.simplehats.common.item.HatItemDyeable;

import net.minecraft.core.component.DataComponents;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.DyedItemColor;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.TransmuteRecipe;
import net.minecraft.world.level.Level;

public class HatDyeingRecipe extends CustomRecipe {
    public static final HatDyeingRecipe INSTANCE = new HatDyeingRecipe();
    public static final MapCodec<HatDyeingRecipe> CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, HatDyeingRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    public HatDyeingRecipe() {
        super();
    }

    @Override
    public String group() {
        return Constants.MOD_ID + ":hat_dyeing";
    }

    @Override
    public boolean matches(CraftingInput input, Level level) {
        if (input.ingredientCount() < 2) {
            return false;
        } else {
            boolean hasTarget = false;
            boolean hasDyes = false;

            for (int slot = 0; slot < input.size(); slot++) {
                ItemStack itemStack = input.getItem(slot);
                if (!itemStack.isEmpty()) {
                    if (itemStack.getItem() instanceof HatItemDyeable) {
                        if (hasTarget) {
                            return false;
                        }

                        hasTarget = true;
                    } else {
                        if (!itemStack.is(ItemTags.DYES) || !itemStack.has(DataComponents.DYE)) {
                            return false;
                        }

                        hasDyes = true;
                    }
                }
            }

            return hasDyes && hasTarget;
        }
    }

    @Override
    public ItemStack assemble(CraftingInput input) {
        List<DyeColor> dyes = new ArrayList<>();
        ItemStack targetStack = ItemStack.EMPTY;

        for (int slot = 0; slot < input.size(); slot++) {
            ItemStack itemStack = input.getItem(slot);
            if (!itemStack.isEmpty()) {
                if (itemStack.getItem() instanceof HatItemDyeable) {
                    if (!targetStack.isEmpty()) {
                        return ItemStack.EMPTY;
                    }

                    targetStack = itemStack;
                } else {
                    if (!itemStack.is(ItemTags.DYES)) {
                        return ItemStack.EMPTY;
                    }

                    DyeColor dye = itemStack.getOrDefault(DataComponents.DYE, DyeColor.WHITE);
                    dyes.add(dye);
                }
            }
        }

        if (!targetStack.isEmpty() && !dyes.isEmpty()) {
            DyedItemColor currentDye = targetStack.get(DataComponents.DYED_COLOR);
            DyedItemColor newDyedColor = DyedItemColor.applyDyes(currentDye, dyes);
            ItemStack result = TransmuteRecipe.createWithOriginalComponents(new ItemStackTemplate(targetStack.typeHolder(), targetStack.count(), targetStack.getComponentsPatch()), targetStack);
            result.set(DataComponents.DYED_COLOR, newDyedColor);
            return result;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public RecipeSerializer<? extends CustomRecipe> getSerializer() {
        return SimpleHatsCommon.MOD_REGISTRY.getHatDyeingSerializer();
    }
}
