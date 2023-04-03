package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
public class ChatConfig {

    //Plugin plugin = Bukkit.getPluginManager().getPlugin("AdminChat");
    //String authors = plugin.getDescription().getAuthors().toString();
    public Main main;
    public File file;
    public FileConfiguration config;
    public ChatConfig(Plugin plugin , String path, Main main) {
        this(plugin.getDataFolder().toPath().resolve(path).toString());
        this.main = main;
    }
    public ChatConfig(String path) {
        file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
    public void save()
    {
        try {
            //this.config.save(this.file);
            main.saveDefaultConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
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

    //Dosya kontrol sistemi
    public void updater() {
        String[] anahtarKelimeler = {"Title","Boosbar","Boosbar-Color"};
        String[] valuekey = {"&c&lYönetim Sohbet","&eYönetim Sohbet","&a"};
        config.options().copyDefaults(true);
        config.options().header(  "Authors: Umut #Lütfen bu alanı değiştirmeyiz\n "+ "# Lütfen dosyanın formatını doğru bir şekilde koruyunuz.");
        for (int i = 0; i < anahtarKelimeler.length; i++) {
            if (getConfig().contains(anahtarKelimeler[i])) {
                Bukkit.getLogger().warning("Metin, " + anahtarKelimeler[i] + " anahtar kelimesini içeriyor.");
            } else {
                Bukkit.getLogger().warning("Metin, " + anahtarKelimeler[i] + " anahtar kelimesini içermiyor.");
                getConfig().set(anahtarKelimeler[i], valuekey[i]);
                save();

            }
        }
    }
}
