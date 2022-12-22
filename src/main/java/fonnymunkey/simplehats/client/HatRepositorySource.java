/*
package fonnymunkey.simplehats.client;

import fonnymunkey.simplehats.SimpleHats;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.server.packs.repository.RepositorySource;
import org.apache.logging.log4j.Level;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;
import java.util.function.Consumer;

public class HatRepositorySource implements RepositorySource {

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
    public void loadPacks(Consumer<Pack> consumer, Pack.PackConstructor constructor) {
        for(File path : Objects.requireNonNull(directory.listFiles())) {
            if(path.getName().endsWith(".zip")) {
                final Pack pack = Pack.create("resources/" + path.getName(), true, () -> new FilePackResources(path), constructor, Pack.Position.TOP, PackSource.BUILT_IN);
                if(pack!=null) consumer.accept(pack);
                else SimpleHats.logger.log(Level.WARN, "Failed to load config resourcepack");
            }
        }
    }
}
 */