package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {
    public ChatConfig config = new ChatConfig(this, "config.yml", this);
    public Chat chat = new Chat(config);
    @Override
    public void onEnable() {
        config.creatfilefolder();
        Bukkit.getServer().getPluginManager().registerEvents(chat, this);
        getCommand("agrup").setExecutor(new ChatCommand(chat, config));
        getCommand("agrup").setTabCompleter(new ChatTabComplete(chat));
        //Config dosyasının okunnması için
        this.getServer().getScheduler().runTaskLater(this, () -> {
            saveDefaultConfig();
            String lang = getConfig().getString("langue", "EN"); // Varsayılan olarak 'en' kullan
            if (lang.equals("EN")) {
                config.loadLanguageFile("EN.yml");
                Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title")+ " " + "&4FAI3333333asdasdasdasdasdLED TO START")); //&7[&cADMIN CHAT&7]

            } else if (lang.equals("TR")) {
                config.loadLanguageFile("TR.yml");
                Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title")+ " " + "&4FAIasdasdasdasdasdLED TO START")); //&7[&cADMIN CHAT&7]
                saveDefaultConfig();
            }

            //config.reloadconfig();
            if (config.getFile().exists())
            {
                Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title") + " &aBaşarıyla Başlatıldı!"));
                if (getConfig().getBoolean("ConfigController",true))
                {
                    config.updater();
                }
            }
            else
            {
                Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title")+ " " + "&4FAILED TO START")); //&7[&cADMIN CHAT&7]
            }
        }, 1L);
    }
}
