package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {
    public ChatConfig config = new ChatConfig(this, "config.yml", this);
    public Chat chat = new Chat(config);
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(chat, this);
        getCommand("agrup").setExecutor(new ChatCommand(chat, config));
        getCommand("agrup").setTabCompleter(new ChatTabComplete(chat));

        this.getServer().getScheduler().runTaskLater(this, () -> {
            //Config dosyasının okunnması için
            String lang = getConfig().getString("langue", "En");
            if (lang.equals("En"))
            {
                config.loadLanguageFile("En.yml");
                saveDefaultConfig();
                if (config.getFile().exists())
                {
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title") + " &aStarted Successfully!"));
                }
            }
            else if (lang.equals("Tr"))
            {
                //config.reloadconfig();
                config.loadLanguageFile("Tr.yml");
                if (config.getFile().exists())
                {
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title") + " &aBaşarıyla Başlatıldı!"));
                    if (config.yamlConfiguration.getBoolean("ConfigController",true))
                    {
                        config.updater();
                    }
                }
                else
                {
                    Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', config.yamlConfiguration.getString("Title")+ " " + "&4FAILED TO START")); //&7[&cADMIN CHAT&7]
                }

            }
        }, 1L);
    }
}
