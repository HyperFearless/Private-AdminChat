package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {

    public ChatConfig config = new ChatConfig(this, "config.yml", this);
    public Chat chat = new Chat(config);

    @Override
    public void onEnable() {

        Bukkit.getServer().getPluginManager().registerEvents(chat, this);
        getCommand("konusma").setExecutor(new ChatCommand(chat, config));

        //Dosya oluşturuldumu diye kontrol eden controller
        if (!config.getFile().exists()) {
            saveDefaultConfig();
            saveDefaultConfig();
        } else {
            //oluşturuldu ve kontrol edilecek mi
            if (config.getConfig().getBoolean("ConfigController", true)) {
                //getConfig().options().header(getDescription().getAuthors().toString());
                config.updater();
            }
        }
    }
}
