package fonnymunkey.simplehats;

import fonnymunkey.simplehats.compat.ISlotCompat;
import fonnymunkey.simplehats.compat.NoSlotCompat;
import fonnymunkey.simplehats.compat.baubles.BaublesCompat;
import fonnymunkey.simplehats.proxy.CommonProxy;
import fonnymunkey.simplehats.util.HatJson;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = SimpleHats.MODID, version = SimpleHats.VERSION, name = SimpleHats.NAME, dependencies = "after:baubles;after:jei")
public class SimpleHats {
    public static final String MODID = "simplehats";
    public static final String VERSION = "0.4.1";
    public static final String NAME = "SimpleHats";
    public static final Logger LOGGER = LogManager.getLogger(NAME);

    public static ISlotCompat SLOT_COMPAT = new NoSlotCompat();

    @SidedProxy(clientSide = "fonnymunkey.simplehats.proxy.ClientProxy", serverSide = "fonnymunkey.simplehats.proxy.CommonProxy")
    public static CommonProxy PROXY;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        if(Loader.isModLoaded("baubles")) SLOT_COMPAT = new BaublesCompat();
        HatJson.registerHatJson(event.getModConfigurationDirectory());
        PROXY.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        PROXY.init();
    }
}