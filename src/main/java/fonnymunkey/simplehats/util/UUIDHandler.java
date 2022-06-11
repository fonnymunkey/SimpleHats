package fonnymunkey.simplehats.util;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fonnymunkey.simplehats.SimpleHats;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

public class UUIDHandler {
    private static final String uuidUrl = "https://raw.githubusercontent.com/fonnymunkey/SimpleHatsAux/main/Forge-1.18.2/uuids.json";
    private static final String zipUrl = "https://raw.githubusercontent.com/fonnymunkey/SimpleHatsAux/main/Forge-1.18.2/simplehats_forge_1.18.2_hatdl.zip";

    private static Map<String, Integer> uuidMap = new HashMap<>();

    public static void setupUUIDMap() {
        try {
            URL url = new URL(uuidUrl);
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
            uuidMap = new Gson().fromJson(read, new TypeToken<Map<String, Integer>>(){}.getType());
            read.close();
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.WARN, "Failed to fetch UUID file: " + ex);
        }
    }

    public static void checkResourceUpdates() {
        try {
            File parent = FMLPaths.CONFIGDIR.get().resolve(SimpleHats.modId + "_hatdl").toFile();
            if(!parent.exists()) parent.mkdirs();
            File uuidFile = new File(parent, "uuids.json");
            File zipFile = new File(parent, SimpleHats.modId + "_hatdl.zip");

            boolean changed;
            if(uuidFile.exists()) {
                String fileString = Files.asCharSource(uuidFile, Charset.defaultCharset()).read();
                JsonObject json = JsonParser.parseString(fileString).getAsJsonObject();
                Map<String, Integer> uuidMapPre = new Gson().fromJson(json, new TypeToken<Map<String, Integer>>(){}.getType());
                changed = !uuidMapPre.equals(uuidMap);
            }
            else changed = true;

            if(changed) {
                if(uuidFile.exists()) uuidFile.delete();
                if(zipFile.exists()) zipFile.delete();

                URL uuidURL = new URL(uuidUrl);
                java.nio.file.Files.copy(uuidURL.openStream(), uuidFile.toPath());
                URL zipURL = new URL(zipUrl);
                java.nio.file.Files.copy(zipURL.openStream(), zipFile.toPath());
            }
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.WARN, "Failed to handle model downloading: " + ex);
        }
    }

    public static Map<String, Integer> getUUIDMap() {
        return uuidMap;
    }
}