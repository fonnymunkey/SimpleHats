/*
package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHats;
import net.minecraft.resource.*;
import net.minecraft.text.Text;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

public class HatRepositorySource implements ResourcePackProvider {

    private final File directory;

    public HatRepositorySource(Path path){
        directory = path.resolve(SimpleHats.modId + "_hatdl").toFile();
        try {
            if(!directory.exists()) directory.mkdirs();
        }
        catch(Exception ex) {
            SimpleHats.logger.log(Level.WARN, "Failed to create config folder: " + ex);
        }
    }

    @Override
    public void register(Consumer<ResourcePackProfile> consumer) {
        for(File path : Objects.requireNonNull(directory.listFiles())) {
            if(path.getName().endsWith(".zip")) {
                final ResourcePackProfile pack = ResourcePackProfile.create("resources/" + path.getName(), Text.literal("SimpleHats"), true, () -> new ZipResourcePack("SimpleHats", path, true), ResourceType.CLIENT_RESOURCES, ResourcePackProfile.InsertionPosition.TOP, ResourcePackSource.BUILTIN);
                if(pack!=null) consumer.accept(pack);
                else SimpleHats.logger.log(Level.WARN, "Failed to load config resourcepack");
            }
        }
    }
}

 */