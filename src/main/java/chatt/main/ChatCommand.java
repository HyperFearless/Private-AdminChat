package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChatCommand implements CommandExecutor {

    private final ChatConfig config;
    private final Chat chat;
    public ChatCommand(Chat chat,ChatConfig config) {
        this.chat = chat;
        this.config = config;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            if (args.length >= 1)
            {
                if (args[0].equalsIgnoreCase("ekle"))
                {
                    if (args.length < 2) {
                        //player.sendMessage(ChatColor.RED + "Kullanım şekli: /grup ekle [oyuncu adı]");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("how-to-use-command-add")));
                        return false;
                    }
                    if (Bukkit.getPlayer(args[1]) != null)
                    {
                        Player target = Bukkit.getPlayer(args[1]);
                        if (target == player)
                        {
                            if (!chat.getPlayerList().contains(player.getPlayer()))
                            {
                                chat.addplayerList(player);
                                //player.sendMessage(ChatColor.GREEN + player.getDisplayName() + " Gruba Eklendin");
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + " " + config.getConfig().getString("add-player")));
                            }
                            else
                            {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("player-already-group")));
                                //player.sendMessage(ChatColor.RED + "Zaten gruptasın.");
                            }
                        }
                        else if (target == null)
                        {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getDisplayName() + " " + config.getConfig().get("player-not-found") ));
                            //player.sendMessage(ChatColor.RED + args[1] + " adlı oyuncu bulunamadı.");
                            return false;
                        }
                        else
                        {
                            if (!chat.getPlayerList().contains(target.getPlayer()))
                            {
                                if (!chat.getPlayerList().contains(player.getPlayer()))
                                {
                                    chat.addplayerList(player);
                                    chat.addplayerList(target);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " + config.getConfig().getString("adding-player")));
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getDisplayName() + " " + config.getConfig().getString("add-player")));
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getDisplayName() + " " + config.getConfig().getString("target-player")));
                                    //player.sendMessage(ChatColor.GREEN + player.getDisplayName() + " Gruba Eklendin");
                                    //player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Adlı oyuncu Gruba Eklendi");
                                    //target.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Gruba Eklendin");
                                }
                                else
                                {
                                    chat.addplayerList(target);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + " " + config.getConfig().getString("add-player")));
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getDisplayName() + " " + config.getConfig().getString("target-player")));
                                    //player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Adlı oyuncu Gruba Eklendi");
                                    //target.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Gruba Eklendin");
                                }
                            }
                            else
                            {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " + config.getConfig().getString("player-already-group")));
                                //player.sendMessage(ChatColor.RED + "Zaten grupda.");
                            }
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("çıkart") || args[0].equalsIgnoreCase("cıkart"))
                {
                    if (args.length < 2)
                    {
                        //player.sendMessage(ChatColor.RED + "Kullanım şekli: /grup çıkart [oyuncu adı]");
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("how-to-use-command-quit")));
                        return false;
                    }
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == player)
                    {
                        if (!chat.getPlayerList().contains(player))
                        {
                            //player.sendMessage(ChatColor.RED + "Bir Grupta değilsin.");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("none-group")));
                        }
                        else
                        {
                            chat.removeplayerList(player);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("player-quit")));
                            //player.sendMessage(ChatColor.GREEN + "Gruptan çıktın.");
                        }
                    }
                    else if (target == null)
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getName() + " " + config.getConfig().getString("player-already-group")));
                        //player.sendMessage(ChatColor.GREEN + args[1] + ChatColor.RED+" adlı oyuncu bulunamadı.");
                        return false;
                    }
                    else
                    {
                        if (!chat.getPlayerList().contains(target))
                        {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getDisplayName() + " " + config.getConfig().getString("target-none-group")));
                            //player.sendMessage(ChatColor.RED + target.getName() + " bir grupta değil.");
                        }
                        else
                        {
                            chat.removeplayerList(target);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getDisplayName() + " " + config.getConfig().getString("target-you-left")));
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " + config.getConfig().getString("target-left")));
                            //player.sendMessage(ChatColor.GREEN + target.getName() + " adlı oyuncu gruptan çıkartıldı.");
                            //target.sendMessage(ChatColor.RED + "Gruptan çıkartıldın.");
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("çık") || args[0].equalsIgnoreCase("cık"))
                {
                    if (chat.getPlayerList().contains(player))
                    {
                        chat.removeplayerList(player);
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', player.getName() + " " + config.getConfig().getString("player-quit")));
                    }
                }
                //Oyuncu sayılarını kontrol eder
                else if (args[0].equalsIgnoreCase("bak"))
                {
                    if (!chat.getPlayerList().contains(player))
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " +config.getConfig().getString("none-group")));
                        //player.sendMessage(ChatColor.RED + "Grupta değilsin.");
                    }
                    else
                    {
                        int i = 1;
                        player.sendMessage(ChatColor.GREEN + "Gruptaki oyuncular:");
                        for (Player target : chat.getPlayerList())
                        {
                            player.sendMessage(i +". "+ target.getDisplayName());
                            i++;
                        }
                    }
                }
            }
            else
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("not-found-command")));
                //player.sendMessage(ChatColor.RED + "Geçersiz komut.");
            }
        }
        else
        {
            Bukkit.getLogger().warning(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("not-console-command")));
            //Bukkit.getLogger().info("Bu komut sadece oyuncular tarafından kullanılabilir.");
            return false;
        }
        return true;
    }
}
//Player target = Bukkit.getServer().getPlayer(args[0]);
//chat.addplayerList(target);
//player.sendMessage(ChatColor.GREEN.toString() + player.getName() + " Adlı oyuncu Gruba Eklendi");
//target.sendMessage(ChatColor.GREEN.toString() + target.getName() + " Gruba Eklendin");