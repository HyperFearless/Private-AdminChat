package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ChatConfig {
    private File file;
    private FileConfiguration config;

    public ChatConfig(Plugin plugin, String path) {
        this(plugin.getDataFolder().toPath().resolve(path).toString());
    }
    public ChatConfig(String path) {
        file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
    public boolean save()
    {
        try {
            this.config.save(this.file);
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public File getFile()
    {
        return this.file;
    }
    public FileConfiguration getConfig()
    {
        return this.config;
    }

}
