package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.init.HatJson;
import fonnymunkey.simplehats.common.init.ModConfig;
import fonnymunkey.simplehats.common.init.ModRegistry;
import fonnymunkey.simplehats.util.UUIDHandler;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
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
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, ModConfig.COMMON_SPEC);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::commonSetup);

        HatJson.registerHatJson();

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
        CuriosRendererRegistry.register((Item)ModRegistry.HATSPECIAL.get(), () -> (ICurioRenderer)ModRegistry.HATSPECIAL.get());
    }

    public void commonSetup(final FMLCommonSetupEvent event) {
        if(ModConfig.COMMON.allowUpdates.get()) {
            UUIDHandler.setupUUIDMap();
            DistExecutor.safeRunWhenOn(Dist.CLIENT, () -> UUIDHandler::checkResourceUpdates);//Only need to download resources on client
        }
    }
}