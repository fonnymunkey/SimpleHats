package fonnymunkey.simplehats;

import fonnymunkey.simplehats.common.HatJson;
import fonnymunkey.simplehats.common.Config;
import fonnymunkey.simplehats.common.init.ModItems;
import fonnymunkey.simplehats.common.item.HatItem;
import fonnymunkey.simplehats.common.item.HatItemDyeable;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
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
//TODO siding checks/tests
    public SimpleHats() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.CLIENT, Config.CLIENT_SPEC);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.COMMON, Config.COMMON_SPEC);
        ModLoadingContext.get().registerConfig(net.minecraftforge.fml.config.ModConfig.Type.SERVER, Config.SERVER_SPEC);

        eventBus.addListener(Config::reloadConfig);
        eventBus.addListener(this::enqueueIMC);
        eventBus.addListener(this::clientSetup);
        eventBus.addListener(this::registerItemColor);
        eventBus.addGenericListener(Item.class, ModItems::registerHats);

        HatJson.registerHatJson(FMLPaths.CONFIGDIR.get());
        ModItems.DEFREG.register(eventBus);
    }

    public void enqueueIMC(final InterModEnqueueEvent event) {
        InterModComms.sendTo(CuriosApi.MODID, SlotTypeMessage.REGISTER_TYPE, () -> SlotTypePreset.HEAD.getMessageBuilder().cosmetic().build());
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        for(Item hat : ModItems.hatList) {
            if(hat instanceof ICurioRenderer renderer) {
                CuriosRendererRegistry.register(hat, () -> renderer);
            }
        }
    }

    public void registerItemColor(ColorHandlerEvent.Item event) {
        for(HatItem hat : ModItems.hatList) {
            if(hat instanceof HatItemDyeable hatDye) {
                event.getItemColors().register((stack, color) -> ((HatItemDyeable)stack.getItem()).getColor(stack), hatDye);
                CauldronInteraction.WATER.put(hatDye, CauldronInteraction.DYED_ITEM);
            }
        }
    }
}
