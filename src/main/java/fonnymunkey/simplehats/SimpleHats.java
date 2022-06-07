package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.Config;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.util.UUIDHandler;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotTypeMessage;
import top.theillusivec4.curios.api.SlotTypePreset;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;

@Mod(SimpleHats.modId)
public class SimpleHats {
    public static final String modId = "simplehats";
    public static Logger logger = LogManager.getLogger();

    public SimpleHats() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::clientSetup);

        HatJson.registerHatJson(FMLPaths.CONFIGDIR.get());
        UUIDHandler.init(FMLPaths.CONFIGDIR.get());

        ModRegistry.ITEM_REG.register(eventBus);
        ModRegistry.ENTITY_REG.register(eventBus);
        ModRegistry.RECIPE_REG.register(eventBus);
        ModRegistry.LOOT_REG.register(eventBus);
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().cosmetic().build());
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        for(Item hat : ModRegistry.hatList) {
            if(hat instanceof ICurioRenderer renderer) {
                CuriosRendererRegistry.register(hat, () -> renderer);
            }
        }
    }
}