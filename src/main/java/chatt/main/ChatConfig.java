package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ChatConfig {
    public Main main;
    public Chat chat;
    public File file;
    public FileConfiguration config;
    public YamlConfiguration yamlConfiguration;
    public ChatConfig(Plugin plugin , String path, Main main) {
        this(plugin.getDataFolder().toPath().resolve(path).toString());
        this.main = main;
        file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
    public ChatConfig(String path) {
        file = new File(path);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }
    //Dil dosyaları yoksa oluştutur
    public void creatfilefolder(){
        File langFolder = new File (main.getDataFolder(), "lang");
        langFolder.mkdirs();
        File enFile = new File(langFolder, "En.yml");
        File trFile = new File(langFolder, "Tr.yml");
        if (langFolder.exists()) { // Koşul değiştirildi
            try {
                if (!enFile.exists()) {
                    enFile.createNewFile();
                    InputStream enInputStream = main.getResource("En.yml");
                    Files.copy(enInputStream, enFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
                if (!trFile.exists()) {
                    trFile.createNewFile();
                    InputStream trInputStream = main.getResource("Tr.yml");
                    Files.copy(trInputStream, trFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }

                FileConfiguration enConfig = YamlConfiguration.loadConfiguration(enFile);
                FileConfiguration trConfig = YamlConfiguration.loadConfiguration(trFile);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    //Dil dosyaları var ise okur ve kaydeder
    void loadLanguageFile(String fileName) {
        File langFile = new File(main.getDataFolder(), "lang/" + fileName);
        if (!langFile.exists())
        {
            main.saveResource("lang/" + fileName, false);
        }
        if (fileName.equalsIgnoreCase("En.yml")) {
            YamlConfiguration enconfig = YamlConfiguration.loadConfiguration(langFile);
            this.yamlConfiguration = enconfig;
            Language.setLanguage(enconfig);
        } else if (fileName.equalsIgnoreCase("Tr.yml")) {
            YamlConfiguration trconfig = YamlConfiguration.loadConfiguration(langFile);
            this.yamlConfiguration = trconfig;
            Language.setLanguage(trconfig);
        }
    }
    //Yazılmış dosyayı kontrol edip tanımlama
    public static class Language {
        private static FileConfiguration lang;

        public static void setLanguage(FileConfiguration langConfig) {
            lang = langConfig;
        }

        public static String getMessage(String path) {
            if (lang == null) {
                return null;
            }
            return ChatColor.translateAlternateColorCodes('&', lang.getString(path));
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

    public void completereload() {
        String lang = getConfig().getString("langue", "En");
        if (lang.equals("En")) {
            main.config.loadLanguageFile("En.yml");
            main.saveDefaultConfig();
            if (main.config.getFile().exists()) {
                Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("Title") + " &aStarted Successfully!"));
            }
        } else if (lang.equals("Tr")) {
            //config.reloadconfig();
            main.config.loadLanguageFile("Tr.yml");
            if (main.config.getFile().exists())
            {
                Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("Title") + " &aBaşarıyla Başlatıldı!"));
            }
        }
    }
    //Dosya kontrol sistemi
    String[] anahtarKelimeler = {
            "Langue",
            "ConfigController",
            "Auto-default-config",
            //"Author",
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
            "Tr",
            "false",
            "false",
            //"Umut",
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
        yamlConfiguration.options().header(" \n" +
                "Lütfen dosyanın formatını doğru bir şekilde koruyunuz.\n" +
                "Anahtar kelimeler değiştirilirse config dosyası bozulabilir.\n" +
                "Varsayılan değer FALSE\n" +
                "True = Config dosyasınıdaki hataları bulur sırasıyla ve kayıt dosyasına yazar.\n" +
                "False = Config dosyasındaki hatayı ellemez. Hatalı olan anahtar kelimesini null değerine çevirir.\n" +
                "Eğer dosya bozulduysa true veya false değerlerinin yanındaki kesme işaretini (') kaldırınız. \n");
        int a = 4;
        for (int i = 0; i < anahtarKelimeler.length; i++) {
            if (yamlConfiguration.contains(anahtarKelimeler[i])) {
                a++;
                if (yamlConfiguration.contains("En"))
                {
                    Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("Title") + " &c" + a + ".&eSatır " + anahtarKelimeler[i] + " &asdasdasdasdasdasd."));
                }
                else if (yamlConfiguration.contains("Tr"))
                {
                    Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', yamlConfiguration.getString("Title") + " &c" + a + ".&eSatır " + anahtarKelimeler[i] + " &aanahtar kelimesini içeriyor."));

                }
            }
            else {
                a++;
                Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&',yamlConfiguration.getString("Title")  + "&c" + a + ".&eSatır " + anahtarKelimeler[i] + " &4anahtar kelimesini içermiyor."));
                if (yamlConfiguration.getBoolean("Auto-default-config",true))
                {
                    yamlConfiguration.set(anahtarKelimeler[i], valuekey[i]);
                    save();
                }
            }
        }
    }
}
