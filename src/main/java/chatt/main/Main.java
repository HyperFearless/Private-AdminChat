package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public Chat chat = new Chat();
    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(chat, this);
        getCommand("konusma").setExecutor(new ChatCommand(chat));
    }
}
