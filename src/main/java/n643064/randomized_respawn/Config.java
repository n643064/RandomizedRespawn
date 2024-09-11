package n643064.randomized_respawn;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public record Config(
        int xRange,
        int zRange,
        int correctionOffset
)
{
    public static Config INSTANCE = new Config(
        30000, 30000, 300
    );
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().setLenient().create();
    static final String CONFIG_PATH = "config" + File.separator + "randomized_respawn.json";

    public static void create() throws IOException
    {
        Path p = Path.of("config");
        if (Files.exists(p))
        {
            if (Files.isDirectory(p))
            {
                FileWriter writer = new FileWriter(CONFIG_PATH);
                writer.write(GSON.toJson(INSTANCE));
                writer.flush();
                writer.close();
            }
        } else
        {
            Files.createDirectory(p);
            create();
        }
    }

    public static void read() throws IOException
    {
        FileReader reader = new FileReader(CONFIG_PATH);
        INSTANCE = GSON.fromJson(reader, Config.class);
        reader.close();
    }

    public static void setup()
    {
        try
        {
            if (Files.exists(Path.of(CONFIG_PATH)))
            {
                read();
            } else
            {
                create();
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}

