/*
package fonnymunkey.simplehats.mixin.core;

import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.client.HatRepositorySource;
import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.resource.ResourcePackProfile;
import net.minecraft.resource.ResourceType;
import org.apache.logging.log4j.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ModResourcePackCreator.class)
public class MixinModResourcePackCreator {

    @Final
    @Shadow
    private ResourceType type;

    @Inject(method = "register(Ljava/util/function/Consumer;Lnet/minecraft/resource/ResourcePackProfile$Factory;)V", at = @At("RETURN"))
    public void simplehats_register(Consumer<ResourcePackProfile> consumer, ResourcePackProfile.Factory factory, CallbackInfo ci) {
        if(this.type == ResourceType.CLIENT_RESOURCES) {
            (new HatRepositorySource(FabricLoader.getInstance().getConfigDir())).register(consumer, factory);
            SimpleHats.logger.log(Level.INFO, "Registered " + SimpleHats.modId + " resource folder.");
        }
    }
}
 */
