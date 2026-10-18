package fonnymunkey.simplehats.util;

import com.google.common.io.Files;
import com.google.gson.*;
import fonnymunkey.simplehats.SimpleHats;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.ResourceLocation;

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
            new HatEntry("babyturtle", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "splash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("bandana", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("bandanargb", EnumRarity.EPIC, 5, 0),
            new HatEntry("baseballeaster", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("baseballhat", EnumRarity.COMMON, 5, 1, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("baseballhatfestive", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("baseballhatjuly", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("baseballhatrgb", EnumRarity.EPIC, 5, 0),
            new HatEntry("batwinghat", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("beanie", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("beanieeaster", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("beaniefestive", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("beaniejuly", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("beaniergb", EnumRarity.EPIC, 5, 0),
            new HatEntry("beaniespooky", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("beehat", EnumRarity.EPIC, 5, 2, new HatEntry.HatParticleSettings(true, "dripLava", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("bicorne", EnumRarity.COMMON, 5, 0),
            new HatEntry("bigbrain", EnumRarity.COMMON, 5, 0),
            new HatEntry("bigcrown", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("bigeyes", EnumRarity.COMMON, 5, 0),
            new HatEntry("bigribbon", EnumRarity.UNCOMMON, 5, 1, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("bigstevehead", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("bluefireeye", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "flame", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("bowler", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("breadhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("brownbrick", EnumRarity.COMMON, 5, 0),
            new HatEntry("bunnyhat", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("burgerhat", EnumRarity.COMMON, 5, 0),
            new HatEntry("caddycap", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("camera", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.002F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("camerabeard", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.002F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("candleonhead", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "flame", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("candycane", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("carrotonstick", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("cartoonegg", EnumRarity.COMMON, 5, 0),
            new HatEntry("cheeseslice", EnumRarity.COMMON, 5, 0),
            new HatEntry("chefshat", EnumRarity.COMMON, 5, 0),
            new HatEntry("chickenhead", EnumRarity.COMMON, 5, 0),
            new HatEntry("chickenonhead", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("christmascakehat", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("christmastree", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "snowshovel", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL), HatEntry.HatSeason.FESTIVE),
            new HatEntry("clockface", EnumRarity.RARE, 5, 0),
            new HatEntry("cowboy", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("cowboyrgb", EnumRarity.EPIC, 5, 0),
            new HatEntry("crabonhead", EnumRarity.EPIC, 5, 0),
            new HatEntry("crown", EnumRarity.COMMON, 5, 0),
            new HatEntry("cuphead", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("cyclopseye", EnumRarity.COMMON, 5, 0),
            new HatEntry("dairyqueen", EnumRarity.COMMON, 5, 0),
            new HatEntry("dangereqsue", EnumRarity.COMMON, 5, 0),
            new HatEntry("dangeresquejuly", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("demoneyes", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "reddust", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("demonhorns", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "lava", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("digger", EnumRarity.RARE, 5, 0),
            new HatEntry("dimmahat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("discoball", EnumRarity.EPIC, 5, 0),
            new HatEntry("disguise", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("doctorhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("dorkglassesandteeth", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("doubletake", EnumRarity.COMMON, 5, 0),
            new HatEntry("dragonhead", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), new HatEntry.HatParticleSettings(true, "flame", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("dragonskull", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "smoke", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("dragonskullender", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "dragonbreath", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("drinkinhat", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("dumhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("dwarfminerbeard", EnumRarity.UNCOMMON, 5, 2),
            new HatEntry("easterhead", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("egghead", EnumRarity.EPIC, 5, 1, new HatEntry.HatDyeSettings(true, 16383998), HatEntry.HatSeason.EASTER),
            new HatEntry("eggonhead", EnumRarity.RARE, 5, 1, HatEntry.HatSeason.EASTER),
            new HatEntry("elfhat", EnumRarity.COMMON, 5, 0),
            new HatEntry("explorerhat", EnumRarity.COMMON, 5, 0),
            new HatEntry("eyepatch", EnumRarity.COMMON, 5, 0),
            new HatEntry("fakeblight", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "mobSpell", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("fakefire", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "flame", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("farmerbrim", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 7895160), HatEntry.HatSeason.EASTER),
            new HatEntry("festiveantlers", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("festiveribbon", EnumRarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), HatEntry.HatSeason.FESTIVE),
            new HatEntry("finnhood", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("fireworks", EnumRarity.EPIC, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD), HatEntry.HatSeason.SUMMER),
            new HatEntry("fishonhead", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "splash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("flagjuly", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("flies", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("floatinghearts", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "heart", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("floatingstar", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("flowercrown", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "heart", 0.001F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("floweronhead", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "townaura", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("foxhat", EnumRarity.RARE, 5, 1, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("fro", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("frozenhead", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "snowshovel", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("fullironhelm", EnumRarity.UNCOMMON, 5, 3),
            new HatEntry("ghostmask", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "endRod", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("goggles", EnumRarity.COMMON, 5, 0),
            new HatEntry("grandmadisguise", EnumRarity.COMMON, 5, 0),
            new HatEntry("greenbirb", EnumRarity.EPIC, 5, 0),
            new HatEntry("grinchhat", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("halo", EnumRarity.COMMON, 5, 0),
            new HatEntry("headbolts", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.01F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("headphonesblue", EnumRarity.EPIC, 5, 3, new HatEntry.HatParticleSettings(true, "note", 0.01F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("headshot", EnumRarity.COMMON, 5, 0),
            new HatEntry("hockeymask", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("holyhead", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "spell", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("horsemask", EnumRarity.COMMON, 5, 0),
            new HatEntry("hosthat", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "reddust", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("icedragonskull", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "snowshovel", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("jackohat", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("jesterhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("julydouble", EnumRarity.RARE, 5, 2, HatEntry.HatSeason.SUMMER),
            new HatEntry("kirbymouthful", EnumRarity.EPIC, 5, 0),
            new HatEntry("largehorns", EnumRarity.COMMON, 5, 1),
            new HatEntry("lilbow", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("madscientist", EnumRarity.RARE, 5, 0),
            new HatEntry("magikarp", EnumRarity.EPIC, 5, 1),
            new HatEntry("megamanhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("mistletoe", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("mohawk", EnumRarity.COMMON, 5, 0),
            new HatEntry("monkeyking", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("monocle", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("moreeyes", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("murdered", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("nekoears", EnumRarity.COMMON, 5, 0),
            new HatEntry("palmtree", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("paperbag", EnumRarity.COMMON, 5, 0),
            new HatEntry("partyhat", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("paypay", EnumRarity.RARE, 5, 1),
            new HatEntry("penguinbaby", EnumRarity.RARE, 5, 0),
            new HatEntry("penguinhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("pighead", EnumRarity.COMMON, 5, 0),
            new HatEntry("pinhead", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("plaguedoctor", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("pog", EnumRarity.COMMON, 5, 0),
            new HatEntry("pohatoe", EnumRarity.EPIC, 5, 0),
            new HatEntry("policebucket", EnumRarity.COMMON, 5, 0),
            new HatEntry("policesiren", EnumRarity.RARE, 5, 0),
            new HatEntry("poofballhat", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("poofballrgb", EnumRarity.EPIC, 5, 0),
            new HatEntry("popehat", EnumRarity.COMMON, 5, 0),
            new HatEntry("potionhead", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "mobSpell", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("presentsstack", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("propelhat", EnumRarity.RARE, 5, 0),
            new HatEntry("questbook", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("rabbitears", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("rabbitonhead", EnumRarity.RARE, 5, 0),
            new HatEntry("rainboworbiters", EnumRarity.EPIC, 5, 0),
            new HatEntry("ranahat", EnumRarity.COMMON, 5, 0),
            new HatEntry("redeyes", EnumRarity.RARE, 5, 0),
            new HatEntry("rednose", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.FESTIVE),
            new HatEntry("redstache", EnumRarity.UNCOMMON, 5, 1),
            new HatEntry("rgbbigribbon", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbbowler", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbdragonskull", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbdrinkinhat", EnumRarity.EPIC, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("rgbeasterhead", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbfullhelm", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbpartyhat", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbsmallbowler", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbsunglasses", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbtoptophathat", EnumRarity.EPIC, 5, 0),
            new HatEntry("rgbushanka", EnumRarity.EPIC, 5, 0),
            new HatEntry("rock", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("rubbernipple", EnumRarity.COMMON, 5, 0),
            new HatEntry("sandcastle", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("santaclaus", EnumRarity.RARE, 5, 1, HatEntry.HatSeason.FESTIVE),
            new HatEntry("sausage", EnumRarity.COMMON, 5, 0),
            new HatEntry("seaweedhat", EnumRarity.RARE, 5, 0, HatEntry.HatSeason.SUMMER),
            new HatEntry("shakehat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("sheep", EnumRarity.COMMON, 5, 0),
            new HatEntry("shrekears", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("shroomcap", EnumRarity.UNCOMMON, 5, 1, new HatEntry.HatParticleSettings(true, "townaura", 0.08F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("simsgem", EnumRarity.RARE, 5, 0),
            new HatEntry("smokingpipe", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "smoke", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("snowmanbaby", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "snowshovel", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("sombrero", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("sonichood", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FEET)),
            new HatEntry("spadesoldier", EnumRarity.RARE, 5, 0),
            new HatEntry("spiderweb", EnumRarity.UNCOMMON, 5, 0, HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("springer", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("sprout", EnumRarity.COMMON, 5, 0),
            new HatEntry("spyzombie", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("stackofeggs", EnumRarity.EPIC, 5, 0, HatEntry.HatSeason.EASTER),
            new HatEntry("stress", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "townaura", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("summerhat", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 11330558)),
            new HatEntry("sunglasses", EnumRarity.RARE, 5, 0),
            new HatEntry("sunglassesbig", EnumRarity.RARE, 5, 1),
            new HatEntry("supersandhat", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("swimmer", EnumRarity.COMMON, 5, 0),
            new HatEntry("tinkerhat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("topcathat", EnumRarity.RARE, 5, 0),
            new HatEntry("tophat", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("toptophathat", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16383998)),
            new HatEntry("triangleshades", EnumRarity.COMMON, 5, 0),
            new HatEntry("tricorne", EnumRarity.COMMON, 5, 0),
            new HatEntry("tvhead", EnumRarity.EPIC, 5, 2),
            new HatEntry("unicornhorn", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "spell", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("ushanka", EnumRarity.COMMON, 5, 0),
            new HatEntry("vikinghatbeard", EnumRarity.RARE, 5, 2),
            new HatEntry("villagernose", EnumRarity.COMMON, 5, 0),
            new HatEntry("winghat", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "cloud", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("zigzagwitchhat", EnumRarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16383998), new HatEntry.HatParticleSettings(true, "witchMagic", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL), HatEntry.HatSeason.HALLOWEEN),
            new HatEntry("acornhat", EnumRarity.COMMON, 5, 0),
            new HatEntry("aegishat", EnumRarity.RARE, 5, 0),
            new HatEntry("alienphil", EnumRarity.RARE, 5, 0),
            new HatEntry("amalgalichhat", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "portal", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("angrymask", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("antlers", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("apple", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("artsy", EnumRarity.EPIC, 5, 0),
            new HatEntry("babydolphin", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "dripWater", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),

            new HatEntry("artsy_doll", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "mobSpell", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("azumanga_hat", EnumRarity.RARE, 5, 0),
            new HatEntry("beret_ribbon", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("bucket", EnumRarity.COMMON, 5, 0, new HatEntry.HatParticleSettings(true, "dripWater", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("burning_m_bison", EnumRarity.EPIC, 5, 0),
            new HatEntry("chalk_stick", EnumRarity.COMMON, 5, 0),
            new HatEntry("chi_ears", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("circular_glasses", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 12763584)),
            new HatEntry("cucumbereyemask", EnumRarity.COMMON, 5, 0),
            new HatEntry("dejiko", EnumRarity.RARE, 5, 0),
            new HatEntry("fez", EnumRarity.COMMON, 5, 0),
            new HatEntry("fishing_hat", EnumRarity.EPIC, 5, 0),
            new HatEntry("lightning_eyes", EnumRarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16777215)),
            new HatEntry("longfoxears", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 12763584)),
            new HatEntry("milady_doll", EnumRarity.RARE, 5, 0),
            new HatEntry("nyan_doll", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "mobSpell", 0.2F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("orange_hat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("peppino", EnumRarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16777215)),
            new HatEntry("pom_moog", EnumRarity.COMMON, 5, 0),
            new HatEntry("puchiko", EnumRarity.RARE, 5, 0),
            new HatEntry("rabi_en_rose", EnumRarity.RARE, 5, 1),
            new HatEntry("raincloud", EnumRarity.EPIC, 5, 0),
            new HatEntry("scouter", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 12763584)),
            new HatEntry("sleepeyemask", EnumRarity.COMMON, 5, 0, new HatEntry.HatParticleSettings(true, "heart", 0.01F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("sport_sunglasses", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("strawberry_hat", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("teddy_bear", EnumRarity.EPIC, 5, 6),
            new HatEntry("the_noise", EnumRarity.RARE, 5, 0),
            new HatEntry("toy_story_alien", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("twilight_doll", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "enchantmenttable", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("worms_mine", EnumRarity.UNCOMMON, 5, 0),

            new HatEntry("alien_antennae", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 5308240)),
            new HatEntry("angel_and_devil", EnumRarity.EPIC, 5, 0),
            new HatEntry("astronaut", EnumRarity.COMMON, 5, 0),
            new HatEntry("axolotl_on_head", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "dripWater", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("baby_crewmate", EnumRarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16719360)),
            new HatEntry("bee_on_head", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "dripLava", 0.02F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("beetle_on_head", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("binky", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 36095)),
            new HatEntry("cardboard_box", EnumRarity.COMMON, 5, 0),
            new HatEntry("cat_hat", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 16752640)),
            new HatEntry("cat_on_head", EnumRarity.RARE, 5, 0, new HatEntry.HatDyeSettings(true, 16743680)),
            new HatEntry("caterpillar_on_head", EnumRarity.RARE, 5, 0),
            new HatEntry("chocolate_sauced", EnumRarity.RARE, 5, 0),
            new HatEntry("crystal_horns", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "spell", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("dipper", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("druid_antlers", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatDyeSettings(true, 5299200)),
            new HatEntry("druid_antlers_rare", EnumRarity.EPIC, 5, 0, new HatEntry.HatDyeSettings(true, 5299200), new HatEntry.HatParticleSettings(true, "enchantmenttable", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("eevee_ears", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("eyeholder_beeholder", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "dripLava", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("eyeholder_dark", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "flame", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("eyeholder_evil", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "portal", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("eyeholder_warm", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "lava", 0.03F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("eyeholder_xanath", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "enchantmenttable", 0.2F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("gnome", EnumRarity.COMMON, 5, 0, new HatEntry.HatDyeSettings(true, 65280)),
            new HatEntry("gnome_clover_wig", EnumRarity.UNCOMMON, 5, 1),
            new HatEntry("greaser", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("hat_of_discipline", EnumRarity.RARE, 5, 0),
            new HatEntry("ladybug_on_head", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("lil_bows", EnumRarity.RARE, 5, 0),
            new HatEntry("lil_termagant", EnumRarity.EPIC, 5, 0),
            new HatEntry("medusa", EnumRarity.RARE, 5, 0),
            new HatEntry("mimic_head", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("mimic_head_dark", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "endRod", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("mimic_head_gold", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("mindflayer", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "magicCrit", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("mindflayer_alhoon", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "enchantmenttable", 0.2F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("octodad", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "splash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("pika_ears", EnumRarity.UNCOMMON, 5, 0, new HatEntry.HatParticleSettings(true, "fireworksSpark", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_FULL)),
            new HatEntry("right_hand_hat", EnumRarity.COMMON, 5, 1),
            new HatEntry("round_purple_wig", EnumRarity.COMMON, 5, 1),
            new HatEntry("round_red_wig", EnumRarity.COMMON, 5, 1),
            new HatEntry("slime_cube_dnd", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "slime", 0.05F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("slime_head", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "slime", 0.04F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("stinkycheeseman", EnumRarity.EPIC, 5, 0, new HatEntry.HatParticleSettings(true, "townaura", 0.12F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("stuck_lollipop", EnumRarity.COMMON, 5, 0),
            new HatEntry("tanuki_leaf", EnumRarity.UNCOMMON, 5, 0),
            new HatEntry("the_noisier", EnumRarity.EPIC, 5, 0),
            new HatEntry("thumbnail", EnumRarity.EPIC, 5, 0),
            new HatEntry("tick_on_head", EnumRarity.COMMON, 5, 0),
            new HatEntry("toast", EnumRarity.COMMON, 5, 0),
            new HatEntry("toilet", EnumRarity.RARE, 5, 0, new HatEntry.HatParticleSettings(true, "splash", 0.1F, HatEntry.HatParticleSettings.HatParticleMovement.TRAILING_HEAD)),
            new HatEntry("tomato_splats", EnumRarity.EPIC, 5, 0),
            new HatEntry("traffic_cone", EnumRarity.RARE, 5, 0),
            new HatEntry("udder_hat", EnumRarity.COMMON, 5, 0),
            new HatEntry("worm_hat", EnumRarity.COMMON, 5, 0)
            );
    public static List<HatEntry> getHatList() {
        return hatList;
    }

    public static void registerHatJson(File configDir) {
        try {
            File file = new File(configDir, SimpleHats.MODID + ".json");

            if(!file.exists()) {
                SimpleHats.LOGGER.info("SimpleHats simplehats.json not found, generating default file.");

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
                SimpleHats.LOGGER.info("Loaded " + hatList.size() + " hat entries from default file.");
            }
            else {
                file.setWritable(true);
                String fileString = Files.asCharSource(file, Charset.defaultCharset()).read();
                JsonObject json = new JsonParser().parse(fileString).getAsJsonObject();

                Gson gson = new Gson();

                outer:
                for(Map.Entry<String, JsonElement> entry : json.entrySet()){
                    JsonElement dataElement = entry.getValue();
                    HatEntry hatEntry = gson.fromJson(dataElement, HatEntry.class);

                    if(hatEntry.getHatName().isEmpty()) {
                        SimpleHats.LOGGER.warn("Attempted to load empty hat name, skipping.");
                        continue;
                    }
                    if(!validateName(hatEntry.getHatName())) {
                        SimpleHats.LOGGER.warn("Attempted to load invalid hat name \"" + hatEntry.getHatName() + "\", skipping.");
                        continue;
                    }
                    for(HatEntry temp : hatList) {
                        if(temp.getHatName().equalsIgnoreCase(hatEntry.getHatName()) || hatEntry.getHatName().equalsIgnoreCase("special")) {
                            SimpleHats.LOGGER.warn("Attempted to load duplicate hat name \"" + hatEntry.getHatName() + "\", skipping.");
                            continue outer;
                        }
                    }
                    hatEntry.validateDeserializedEntry();
                    hatList.add(hatEntry);
                }
                SimpleHats.LOGGER.info("Loaded " + hatList.size() + " hat entries from simplehats.json");
            }
        }
        catch(Exception ex) {
            SimpleHats.LOGGER.error("Loading simplehats.json failed: " + ex);
        }
    }

    private static boolean validateName(String name) {
        for(char c : name.toCharArray()) {
            if(!(c >= 'a' && c <= 'z' || c >= '0' && c <= '9' || c == '_' || c == '-' || c == '.')) return false;
        }
        return true;
    }
}