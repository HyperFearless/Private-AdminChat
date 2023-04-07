package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
public class ChatConfig {
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
    public File getFile()
    {
        return this.file;
    }
    public FileConfiguration getConfig()
    {
        return this.config;
    }
    public void save()
    {
        try {
            this.config.save(this.file);
            main.saveDefaultConfig();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Reload sistemi
    public void reloadconfig()
    {
        main.reloadConfig();
        config = main.getConfig();
    }

    //Dosya kontrol sistemi
    String[] anahtarKelimeler = {
            "ConfigController",
            "Auto-default-config",
            "Author",
            "Title",
            "Boosbar",
            "Boosbar-Color",
            "adding-player",
            "add-player",
            "target-player",
            "player-quit",
            "target-you-left",
            "target-left",
            "none-group",
            "target-none-group",
            "player-game-quit",
            "player-already-group",
            "player-not-found",
            "not-found-command",
            "not-console-command",
            "not-have-permission",
            "how-to-use-command-all",
            "how-to-use-command-add",
            "how-to-use-command-quit"};
    String[] valuekey = {
            "false",
            "false",
            "Umut",
            "&7[&cYönetim Sohbet&7]",
            " &b➤&f",
            "&7[&c&lYönetim Sohbet&7]",
            "RED",
            "&bGruba katıldınız!",
            "&bAdlı oyuncu gruba eklendi!",
            "&aGruba eklendiniz!",
            "&cGrubu terk ettiniz!",
            "&cGrubunuzdan çıkartıldı!",
            "&aTarafından gruptan çıkartıldınız!",
            "&bHerhangi bir grupta değilsiniz!",
            "&aAdlı oyuncu herhangi bir grupta değil!",
            "&cAdlı oyuncu oyundan çıktığı için gruptan atıldı",
            "&3Zaten bir grupta!",
            "&cBelirtilen oyuncu bulunamadı!",
            "&4Komut bulunamadı!",
            "&e&lBu komut sadece oyuncular tarafından kullanılabilir!",
            "&4Malesef yetkiniz yok!!",
            "Kullanım şekli: /konuşma ekle/çıkart/bak",
            "Kullanım şekli: /konusma ekle [oyuncu adı]",
            "Kullanım şekli: /konusma çıkart [oyuncu adı]"};
    public void updater() {
        config.options().header(" \n" +
                "Lütfen dosyanın formatını doğru bir şekilde koruyunuz.\n" +
                "Anahtar kelimeler değiştirilirse config dosyası bozulabilir.\n" +
                "Varsayılan değer FALSE\n" +
                "True = Config dosyasınıdaki hataları bulur sırasıyla ve kayıt dosyasına yazar.\n" +
                "False = Config dosyasındaki hatayı ellemez. Hatalı olan anahtar kelimesini null değerine çevirir.\n" +
                "Eğer dosya bozulduysa true veya false değerlerinin yanındaki kesme işaretini (') kaldırınız. \n");
        int a = 9;
        for (int i = 0; i < anahtarKelimeler.length; i++) {
            if (getConfig().contains(anahtarKelimeler[i])) {
                a++;
                Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&',"&7[&cYönetim Sohbet&7] &c" + a + ".&eSatır " + anahtarKelimeler[i] + " &aanahtar kelimesini içeriyor."));
            }
            else {
                a++;
                Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&',"&7[&cYönetim Sohbet&7] &c" + a + ".&eSatır " + anahtarKelimeler[i] + " &4anahtar kelimesini içermiyor."));
                if (getConfig().getBoolean("Auto-default-config",true))
                {
                    config.set(anahtarKelimeler[i], valuekey[i]);
                    save();
                }
            }
        }
    }
}
