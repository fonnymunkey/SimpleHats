package fonnymunkey.simplehats.mixin;

import fonnymunkey.simplehats.util.TagInjector;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagLoader;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Code taken 1:1 from "https://github.com/wisp-forest/owo-lib/blob/f705f91a33f9a31f38f27c0fd9032977f97f15ca/src/main/java/io/wispforest/owo/mixin/TagGroupLoaderMixin.java#L19"
 * <p>
 * Such is under the MIT license and full credits go to "https://github.com/gliscowo" glisco for such
 */
@Mixin(TagLoader.class)
public abstract class MixinTagGroupLoader {
    
    @Shadow
    @Final
    private String directory;

    @Inject(
            method = "load",
            at = @At("TAIL")
    )
    public void injectValues(ResourceManager manager, CallbackInfoReturnable<Map<ResourceLocation,List<TagLoader.EntryWithSource>>> cir) {
        var map = cir.getReturnValue();

        TagInjector.ADDITIONS.forEach((location, entries) -> {
            if (!this.directory.equals(location.type())) return;

            var list = map.computeIfAbsent(location.tagId(), id -> new ArrayList<>());
            entries.forEach(addition -> list.add(new TagLoader.EntryWithSource(addition, "owo")));
        });
    }
}