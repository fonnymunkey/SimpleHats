package fonnymunkey.simplehats.common.init;

import com.google.common.io.Files;
import com.google.gson.*;
import fonnymunkey.simplehats.SimpleHats;
import fonnymunkey.simplehats.util.HatEntry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Rarity;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class HatJson {
    private static List<HatEntry> hatList = new ArrayList<>();

    private static List<HatEntry> defaultHats = Arrays.asList(
            new HatEntry("acornhat", Rarity.COMMON, 5),
            new HatEntry("beehat", Rarity.COMMON, 5, 3),
            new HatEntry("bigeyes", Rarity.COMMON, 5),
            new HatEntry("burgerhat", Rarity.COMMON, 5),
            new HatEntry("cyclopseye", Rarity.COMMON, 5),
            new HatEntry("fakeblight", Rarity.COMMON, 5),
            new HatEntry("flies", Rarity.COMMON, 5),
            new HatEntry("headshot", Rarity.COMMON, 5),
            new HatEntry("jesterhat", Rarity.COMMON, 5),
            new HatEntry("rabbitears", Rarity.COMMON, 5),
            new HatEntry("redeyes", Rarity.COMMON, 5),
            new HatEntry("rubbernipple", Rarity.COMMON, 5),
            new HatEntry("triangleshades", Rarity.COMMON, 5),
            new HatEntry("rat", Rarity.COMMON, 5)
    );
    public static List<HatEntry> getHatList() {
        return hatList;
    }

    public static void registerHatJson(Path path) {
        try {
            File file = new File(path.toFile(), SimpleHats.modId + ".json");

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
            if(!ResourceLocation.validPathChar(c)) return false;
        }
        return true;
    }
}
