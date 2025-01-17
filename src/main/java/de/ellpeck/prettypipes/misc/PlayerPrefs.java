package de.ellpeck.prettypipes.misc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.ellpeck.prettypipes.PrettyPipes;
import net.minecraft.client.Minecraft;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlayerPrefs {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static PlayerPrefs instance;

    public ItemOrder terminalItemOrder = ItemOrder.AMOUNT;
    public boolean terminalAscending;
    public boolean syncJei = true;

    public void save() {
        var file = getFile();
        if (file.exists())
            file.delete();
        try (var writer = new FileWriter(file)) {
            GSON.toJson(this, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static PlayerPrefs get() {
        if (instance == null) {
            var file = getFile();
            if (file.exists()) {
                try (var reader = new FileReader(file)) {
                    instance = GSON.fromJson(reader, PlayerPrefs.class);
                    return instance;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            instance = new PlayerPrefs();
        }
        return instance;
    }

    private static File getFile() {
        var location = Minecraft.getInstance().gameDirectory;
        return new File(location, PrettyPipes.ID + "prefs");
    }
}
