package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private ChatConfig chatConfig = new ChatConfig(this,"config.yml");
    public Chat chat = new Chat();
    @Override
    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getServer().getPluginManager().registerEvents(chat, this);
        getCommand("konusma").setExecutor(new ChatCommand(chat,chatConfig));
    }
}
