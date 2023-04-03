package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {
    public ChatConfig config = new ChatConfig(this, "config.yml", this);
    public Chat chat = new Chat(config);
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(chat, this);
        getCommand("konusma").setExecutor(new ChatCommand(chat, config));
        getCommand("konusma").setTabCompleter(new ChatTabComplete(chat));

        //Config dosyasının okunnması için
        this.getServer().getScheduler().runTaskLater(this, new Runnable() {
            @Override
            public void run() {
                saveDefaultConfig();
                config.reloadconfig();
                if (config.getFile().exists())
                {
                    Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Title") + " &aBaşarıyla Başlatıldı!"));
                    if (getConfig().getBoolean("ConfigController",true))
                    {
                        config.updater();
                    }
                }
                else
                {
                    Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', " &7[&cYönetim Sohbet&7] &4Başlatılamadı!"));
                    Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', " &7[&cYönetim Sohbet&7] &4Başlatılamadı!"));
                }
            }
        }, 1L);
    }
}
