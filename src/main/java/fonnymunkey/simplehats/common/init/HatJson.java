package fonnymunkey.simplehats.common.init;

import com.google.common.io.Files;
import com.google.gson.*;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.util.HatEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HatJson {
    private static List<HatEntry> hatList = new ArrayList<>();

    private static final List<HatEntry> defaultHats = Arrays.asList(
            new HatEntry("babyturtle", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:splash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("bandana", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("bandanargb", Rarity.EPIC, 5, 0),
            new HatEntry("baseballeaster", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("baseballhat", Rarity.COMMON, 5, 1, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("baseballhatfestive", Rarity.RARE, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("baseballhatjuly", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("baseballhatrgb", Rarity.EPIC, 5, 0),
            new HatEntry("batwinghat", Rarity.EPIC, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("beanie", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("beanieeaster", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("beaniefestive", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("beaniejuly", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("beaniergb", Rarity.EPIC, 5, 0),
            new HatEntry("beaniespooky", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("beehat", Rarity.EPIC, 5, 2, new HatEntry.HatParticleSettings(true, "minecraft:falling_honey", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("bicorne", Rarity.COMMON, 5, 0),
            new HatEntry("bigbrain", Rarity.COMMON, 5, 0),
            new HatEntry("bigcrown", Rarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("bigeyes", Rarity.COMMON, 5, 0),
            new HatEntry("bigribbon", Rarity.UNCOMMON, 5, 1, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("bigstevehead", Rarity.UNCOMMON, 5, 0),
            new HatEntry("bluefireeye", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:soul_fire_flame", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("bowler", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("breadhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("brownbrick", Rarity.COMMON, 5, 0),
            new HatEntry("bunnyhat", Rarity.RARE, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("burgerhat", Rarity.COMMON, 5, 0),
            new HatEntry("caddycap", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("camera", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:flash", 0.002F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("camerabeard", Rarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:flash", 0.002F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("candleonhead", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:small_flame", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("candycane", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("carrotonstick", Rarity.UNCOMMON, 5, 0),
            new HatEntry("cartoonegg", Rarity.COMMON, 5, 0),
            new HatEntry("cheeseslice", Rarity.COMMON, 5, 0),
            new HatEntry("chefshat", Rarity.COMMON, 5, 0),
            new HatEntry("chickenhead", Rarity.COMMON, 5, 0),
            new HatEntry("chickenonhead", Rarity.UNCOMMON, 5, 0),
            new HatEntry("christmascakehat", Rarity.EPIC, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("christmastree", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:snowflake", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL), HatEntry.HatSeason.FESTIVE),
            new HatEntry("clockface", Rarity.RARE, 5, 0),
            new HatEntry("cowboy", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("cowboyrgb", Rarity.EPIC, 5, 0),
            new HatEntry("crabonhead", Rarity.EPIC, 5, 0),
            new HatEntry("crown", Rarity.COMMON, 5, 0),
            new HatEntry("cuphead", Rarity.UNCOMMON, 5, 0),
            new HatEntry("cyclopseye", Rarity.COMMON, 5, 0),
            new HatEntry("dairyqueen", Rarity.COMMON, 5, 0),
            new HatEntry("dangereqsue", Rarity.COMMON, 5, 0),
            new HatEntry("dangeresquejuly", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("demoneyes", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:crimson_spore", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("demonhorns", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:lava", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("digger", Rarity.RARE, 5, 0),
            new HatEntry("dimmahat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("discoball", Rarity.EPIC, 5, 0),
            new HatEntry("disguise", Rarity.UNCOMMON, 5, 0),
            new HatEntry("doctorhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("dorkglassesandteeth", Rarity.UNCOMMON, 5, 0),
            new HatEntry("doubletake", Rarity.COMMON, 5, 0),
            new HatEntry("dragonhead", Rarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), new HatEntry.HatParticleSettings(true, "minecraft:small_flame", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("dragonskull", Rarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:ash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("dragonskullender", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:dragon_breath", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("drinkinhat", Rarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("dumhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("dwarfminerbeard", Rarity.UNCOMMON, 5, 2),
            new HatEntry("easterhead", Rarity.UNCOMMON, 5, 0),
            new HatEntry("egghead", Rarity.EPIC, 5, 1, new HatEntry.HatDyeSettings(true, 16383998), HatEntry.HatSeason.EASTER),
            new HatEntry("eggonhead", Rarity.RARE, 5, 1, HatEntry.HatSeason.EASTER),
            new HatEntry("elfhat", Rarity.COMMON, 5, 0),
            new HatEntry("explorerhat", Rarity.COMMON, 5, 0),
            new HatEntry("eyepatch", Rarity.COMMON, 5, 0),
            new HatEntry("fakeblight", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:entity_effect", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("fakefire", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:flame", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("farmerbrim", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("festiveantlers", Rarity.EPIC, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("festiveribbon", Rarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), HatEntry.HatSeason.FESTIVE),
            new HatEntry("finnhood", Rarity.UNCOMMON, 5, 0),
            new HatEntry("fireworks", Rarity.EPIC, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), new HatEntry.HatParticleSettings(true, "minecraft:firework", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD), HatEntry.HatSeason.SUMMER),
            new HatEntry("fishonhead", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:splash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("flagjuly", Rarity.RARE, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("flies", Rarity.UNCOMMON, 5, 0),
            new HatEntry("floatinghearts", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:heart", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("floatingstar", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:electric_spark", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("flowercrown", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:spore_blossom_air", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("floweronhead", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:spore_blossom_air", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("foxhat", Rarity.RARE, 5, 1, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("fro", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("frozenhead", Rarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:snowflake", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("fullironhelm", Rarity.UNCOMMON, 5, 2),
            new HatEntry("ghostmask", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:soul", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("goggles", Rarity.COMMON, 5, 0),
            new HatEntry("grandmadisguise", Rarity.COMMON, 5, 0),
            new HatEntry("greenbirb", Rarity.EPIC, 5, 0),
            new HatEntry("grinchhat", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("halo", Rarity.COMMON, 5, 0),
            new HatEntry("headbolts", Rarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:electric_spark", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("headphonesblue", Rarity.EPIC, 5, 3, new HatEntry.HatParticleSettings(true, "minecraft:note", 0.01F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("headshot", Rarity.COMMON, 5, 0),
            new HatEntry("hockeymask", Rarity.RARE, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("holyhead", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:glow", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("horsemask", Rarity.COMMON, 5, 0),
            new HatEntry("hosthat", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:crimson_spore", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("icedragonskull", Rarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:snowflake", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("jackohat", Rarity.EPIC, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("jesterhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("julydouble", Rarity.RARE, 5, 2, HatEntry.HatSeason.SUMMER),
            new HatEntry("kirbymouthful", Rarity.EPIC, 5, 0),
            new HatEntry("largehorns", Rarity.COMMON, 5, 1),
            new HatEntry("lilbow", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("madscientist", Rarity.RARE, 5, 0),
            new HatEntry("magikarp", Rarity.EPIC, 5, 1),
            new HatEntry("megamanhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("mistletoe", Rarity.RARE, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("mohawk", Rarity.COMMON, 5, 0),
            new HatEntry("monkeyking", Rarity.UNCOMMON, 5, 0),
            new HatEntry("monocle", Rarity.UNCOMMON, 5, 0),
            new HatEntry("moreeyes", Rarity.UNCOMMON, 5, 0),
            new HatEntry("murdered", Rarity.RARE, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("nekoears", Rarity.COMMON, 5, 0),
            new HatEntry("palmtree", Rarity.EPIC, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("paperbag", Rarity.COMMON, 5, 0),
            new HatEntry("partyhat", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("paypay", Rarity.RARE, 5, 1),
            new HatEntry("penguinbaby", Rarity.RARE, 5, 0),
            new HatEntry("penguinhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("pighead", Rarity.COMMON, 5, 0),
            new HatEntry("pinhead", Rarity.RARE, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("plaguedoctor", Rarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("pog", Rarity.COMMON, 5, 0),
            new HatEntry("pohatoe", Rarity.EPIC, 5, 0),
            new HatEntry("policebucket", Rarity.COMMON, 5, 0),
            new HatEntry("policesiren", Rarity.RARE, 5, 0),
            new HatEntry("poofballhat", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("poofballrgb", Rarity.EPIC, 5, 0),
            new HatEntry("popehat", Rarity.COMMON, 5, 0),
            new HatEntry("potionhead", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:entity_effect", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("presentsstack", Rarity.EPIC, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("propelhat", Rarity.RARE, 5, 0),
            new HatEntry("questbook", Rarity.UNCOMMON, 5, 0),
            new HatEntry("rabbitears", Rarity.RARE, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("rabbitonhead", Rarity.RARE, 5, 0),
            new HatEntry("rainboworbiters", Rarity.EPIC, 5, 0),
            new HatEntry("ranahat", Rarity.COMMON, 5, 0),
            new HatEntry("redeyes", Rarity.RARE, 5, 0),
            new HatEntry("rednose", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("redstache", Rarity.UNCOMMON, 5, 1),
            new HatEntry("rgbbigribbon", Rarity.EPIC, 5, 0),
            new HatEntry("rgbbowler", Rarity.EPIC, 5, 0),
            new HatEntry("rgbdragonskull", Rarity.EPIC, 5, 0),
            new HatEntry("rgbdrinkinhat", Rarity.EPIC, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("rgbeasterhead", Rarity.EPIC, 5, 0),
            new HatEntry("rgbfullhelm", Rarity.EPIC, 5, 0),
            new HatEntry("rgbpartyhat", Rarity.EPIC, 5, 0),
            new HatEntry("rgbsmallbowler", Rarity.EPIC, 5, 0),
            new HatEntry("rgbsunglasses", Rarity.EPIC, 5, 0),
            new HatEntry("rgbtoptophathat", Rarity.EPIC, 5, 0),
            new HatEntry("rgbushanka", Rarity.EPIC, 5, 0),
            new HatEntry("rock", Rarity.UNCOMMON, 5, 0),
            new HatEntry("rubbernipple", Rarity.COMMON, 5, 0),
            new HatEntry("sandcastle", Rarity.EPIC, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("santaclaus", Rarity.RARE, 5, 1, HatEntry.HatSeason.FESTIVE),
            new HatEntry("sausage", Rarity.COMMON, 5, 0),
            new HatEntry("seaweedhat", Rarity.RARE, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("shakehat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("sheep", Rarity.COMMON, 5, 0),
            new HatEntry("shrekears", Rarity.UNCOMMON, 5, 0),
            new HatEntry("shroomcap", Rarity.UNCOMMON, 5, 1, new HatEntry.HatParticleSettings(true, "minecraft:mycelium", 0.08F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("simsgem", Rarity.RARE, 5, 0),
            new HatEntry("smokingpipe", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:smoke", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("snowmanbaby", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:snowflake", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("sombrero", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("sonichood", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:electric_spark", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("spadesoldier", Rarity.RARE, 5, 0),
            new HatEntry("spiderweb", Rarity.UNCOMMON, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("springer", Rarity.UNCOMMON, 5, 0),
            new HatEntry("sprout", Rarity.COMMON, 5, 0),
            new HatEntry("spyzombie", Rarity.UNCOMMON, 5, 0),
            new HatEntry("stackofeggs", Rarity.EPIC, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("stress", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:warped_spore", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("summerhat", Rarity.COMMON, 5, 0),
            new HatEntry("sunglasses", Rarity.RARE, 5, 0),
            new HatEntry("sunglassesbig", Rarity.RARE, 5, 1),
            new HatEntry("supersandhat", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:electric_spark", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("swimmer", Rarity.COMMON, 5, 0),
            new HatEntry("tinkerhat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("topcathat", Rarity.RARE, 5, 0),
            new HatEntry("tophat", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("toptophathat", Rarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("triangleshades", Rarity.COMMON, 5, 0),
            new HatEntry("tricorne", Rarity.COMMON, 5, 0),
            new HatEntry("tvhead", Rarity.EPIC, 5, 2),
            new HatEntry("unicornhorn", Rarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:glow", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("ushanka", Rarity.COMMON, 5, 0),
            new HatEntry("vikinghatbeard", Rarity.RARE, 5, 2),
            new HatEntry("villagernose", Rarity.COMMON, 5, 0),
            new HatEntry("winghat", Rarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:cloud", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("zigzagwitchhat", Rarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), new HatEntry.HatParticleSettings(true, "minecraft:witch", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("acornhat", Rarity.COMMON, 5, 0),
            new HatEntry("aegishat", Rarity.RARE, 5, 0),
            new HatEntry("alienphil", Rarity.RARE, 5, 0),
            new HatEntry("amalgalichhat", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:portal", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("angrymask", Rarity.UNCOMMON, 5, 0),
            new HatEntry("antlers", Rarity.UNCOMMON, 5, 0),
            new HatEntry("apple", Rarity.UNCOMMON, 5, 0),
            new HatEntry("artsy", Rarity.EPIC, 5, 0),
            new HatEntry("babydolphin", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:dolphin", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),

            new HatEntry("artsy_doll", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:glow", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("azumanga_hat", Rarity.RARE, 5, 0),
            new HatEntry("beret_ribbon", Rarity.UNCOMMON, 5, 0),
            new HatEntry("bucket", Rarity.COMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:falling_water", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("burning_m_bison", Rarity.EPIC, 5, 0),
            new HatEntry("chalk_stick", Rarity.COMMON, 5, 0),
            new HatEntry("chi_ears", Rarity.UNCOMMON, 5, 0),
            new HatEntry("circular_glasses", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 12763584)),
            new HatEntry("cucumbereyemask", Rarity.COMMON, 5, 0),
            new HatEntry("dejiko", Rarity.RARE, 5, 0),
            new HatEntry("fez", Rarity.COMMON, 5, 0),
            new HatEntry("fishing_hat", Rarity.EPIC, 5, 0),
            new HatEntry("lightning_eyes", Rarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16777215)),
            new HatEntry("longfoxears", Rarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 12763584)),
            new HatEntry("milady_doll", Rarity.RARE, 5, 0),
            new HatEntry("nyan_doll", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:entity_effect", 0.2F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("orange_hat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("peppino", Rarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16777215)),
            new HatEntry("pom_moog", Rarity.COMMON, 5, 0),
            new HatEntry("puchiko", Rarity.RARE, 5, 0),
            new HatEntry("rabi_en_rose", Rarity.RARE, 5, 0),
            new HatEntry("raincloud", Rarity.EPIC, 5, 0),
            new HatEntry("scouter", Rarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 12763584)),
            new HatEntry("sleepeyemask", Rarity.COMMON, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:heart", 0.01F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("sport_sunglasses", Rarity.UNCOMMON, 5, 0),
            new HatEntry("strawberry_hat", Rarity.UNCOMMON, 5, 0),
            new HatEntry("teddy_bear", Rarity.EPIC, 5, 6),
            new HatEntry("the_noise", Rarity.RARE, 5, 0),
            new HatEntry("toy_story_alien", Rarity.UNCOMMON, 5, 0),
            new HatEntry("twilight_doll", Rarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "minecraft:enchant", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("worms_mine", Rarity.UNCOMMON, 5, 0)
            );
    public static List<HatEntry> getHatList() {
        return hatList;
    }

    public static void registerHatJson() {
        try {
            File file = new File(FabricLoader.getInstance().getConfigDir().toFile(), SimpleHats.modId + ".json");

            if(!file.exists()) {
                SimpleHats.logger.log(Level.INFO, "SimpleHats simplehats.json not found, generating default file.");

                file.createNewFile();
                file.setWritable(true);

                JsonObject dataJson = new JsonObject();
                Gson gson = new GsonBuilder().setPrettyPrinting().create();

                for(HatEntry entry : defaultHats) {
                    JsonElement element = gson.toJsonTree(entry);
                    dataJson.add(entry.getHatName(), element);
                }

                String dataString = gson.toJson(dataJson);
                PrintWriter writer = new PrintWriter(file);
                writer.write(dataString);
                writer.flush();
                writer.close();

                hatList = defaultHats;
                SimpleHats.logger.log(Level.INFO, "Loaded " + hatList.size() + " hat entries from default file.");
            }
            else {
                file.setWritable(true);
                String fileString = Files.asCharSource(file, Charset.defaultCharset()).read();
                JsonObject json = JsonParser.parseString(fileString).getAsJsonObject();

                Gson gson = new Gson();

                outer:
                for(Map.Entry<String, JsonElement> entry : json.entrySet()){
                    JsonElement dataElement = entry.getValue();
                    HatEntry hatEntry = gson.fromJson(dataElement, HatEntry.class);

                    if(hatEntry.getHatName().isEmpty()) {
                        SimpleHats.logger.log(Level.WARN, "Attempted to load empty hat name, skipping.");
                        continue;
                    }
                    if(!validateName(hatEntry.getHatName())) {
                        SimpleHats.logger.log(Level.WARN, "Attempted to load invalid hat name \"" + hatEntry.getHatName() + "\", skipping.");
                        continue;
                    }
                    for(HatEntry temp : hatList) {
                        if(temp.getHatName().equalsIgnoreCase(hatEntry.getHatName()) || hatEntry.getHatName().equalsIgnoreCase("special")) {
                            SimpleHats.logger.log(Level.WARN, "Attempted to load duplicate hat name \"" + hatEntry.getHatName() + "\", skipping.");
                            continue outer;
                        }
                    }
                    //validate entries from json and set defaults
                    hatEntry.validateDeserializedEntry();
                    hatList.add(hatEntry);
                }
                SimpleHats.logger.log(Level.INFO, "Loaded " + hatList.size() + " hat entries from simplehats.json");
            }
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.ERROR, "Loading simplehats.json failed: " + ex);
        }
    }

    private static boolean validateName(String name) {
        for(char c : name.toCharArray()) {
            if(!Identifier.isCharValid(c)) return false;
        }
        return true;
    }
}
