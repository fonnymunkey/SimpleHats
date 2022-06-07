package fonnymunkey.simplehats.util;

import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import fonnymunkey.simplehats.SimpleHats;
import org.apache.logging.log4j.Level;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class UUIDHandler {
    private static final String uuidUrl = "https://raw.githubusercontent.com/fonnymunkey/SimpleHats/main/uuids.json";
    private static final String zipUrl = "https://raw.githubusercontent.com/fonnymunkey/SimpleHats/main/simplehats_hatdl.zip";

    private static Map<String, Integer> uuidMap = new HashMap<>();

    public static void init(Path resourcePath) {
        try {
            URL url = new URL(uuidUrl);
            BufferedReader read = new BufferedReader(new InputStreamReader(url.openStream()));
            uuidMap = new Gson().fromJson(read, new TypeToken<Map<String, Integer>>(){}.getType());
            read.close();
            System.out.println("Read UUID map");
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.WARN, "Failed to fetch UUID file: " + ex);
            return;
        }
        try {
            boolean changed = false;
            File parent = resourcePath.resolve(SimpleHats.modId + "_hatdl").toFile();
            if(!parent.exists()) parent.mkdirs();
            File uuidFile = new File(parent, "uuids.json");
            File zipFile = new File(parent, "simplehats_hatdl.zip");

            if(uuidFile.exists()) {
                System.out.println("UUID exists");
                String fileString = Files.asCharSource(uuidFile, Charset.defaultCharset()).read();
                JsonObject json = JsonParser.parseString(fileString).getAsJsonObject();
                Map<String, Integer> uuidMapPre = new Gson().fromJson(json, new TypeToken<Map<String, Integer>>(){}.getType());
                changed = uuidMapPre.equals(uuidMap);
            }
            else changed = true;

            if(changed) {
                System.out.println("UUID changed, dl'ing");
                if(uuidFile.exists()) uuidFile.delete();
                if(zipFile.exists()) zipFile.delete();

                URL uuidURL = new URL(uuidUrl);
                BufferedReader readUUID = new BufferedReader(new InputStreamReader(uuidURL.openStream()));
                BufferedWriter writeUUID = new BufferedWriter(new FileWriter(uuidFile));
                readUUID.transferTo(writeUUID);
                readUUID.close();
                writeUUID.close();

                URL zipURL = new URL(zipUrl);
                BufferedReader readZIP = new BufferedReader(new InputStreamReader(zipURL.openStream()));
                BufferedWriter writeZIP = new BufferedWriter(new FileWriter(zipFile));
                readZIP.transferTo(writeZIP);
                readZIP.close();
                writeZIP.close();
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
