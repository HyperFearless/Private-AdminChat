package chatt.main;
import chatt.main.Main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
                //Kullanıcı ekle gruptamı kontrollü
                if (args[0].equalsIgnoreCase("ekle"))
                {
                    //Oyuncu yanlış girer ise
                    if (args.length < 2) {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("how-to-use-command-add")));
                        return false;
                    }
                    if (Bukkit.getPlayer(args[1]) != null)
                    {
                        //Hedeflenen kişi kendin ise
                        Player target = Bukkit.getPlayerExact(args[1]);
                        if (target == player)
                        {
                            if (!chat.getPlayerList().contains(player.getPlayer()))
                            {
                                chat.addplayerList(player);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + " " + config.getConfig().getString("add-player")));
                            }
                            else
                            {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("player-already-group")));
                            }
                        }
                        //Oyuncu bulunamadıysa
                        else if (target == null)
                        {
                            player.sendMessage("asdasdas");
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', args[1] + " " + config.getConfig().get("player-not-found")));
                            return false;
                        }
                        //Oyuncu doğru bir hedef verdiğinde
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
                                }
                                else
                                {
                                    chat.addplayerList(target);
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', player.getDisplayName() + " " + config.getConfig().getString("add-player")));
                                    target.sendMessage(ChatColor.translateAlternateColorCodes('&', target.getDisplayName() + " " + config.getConfig().getString("target-player")));
                                }
                            }
                            //Oyuncu zaten grupta
                            else
                            {
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " + config.getConfig().getString("player-already-group")));
                            }
                        }
                    }
                }
                //gruptan çıkartma
                else if (args[0].equalsIgnoreCase("çıkart") || args[0].equalsIgnoreCase("cıkart"))
                {
                    //Oyuncu yanlış girdiyse
                    if (args.length < 2)
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("how-to-use-command-quit")));
                        return false;
                    }
                    //Hedef grupta veya kendisi sie
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == player)
                    {
                        if (!chat.getPlayerList().contains(player))
                        {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("none-group")));
                        }
                        else
                        {
                            chat.removeplayerList(player);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("player-quit")));
                        }
                    }
                    //Hedef yanlış ise
                    else if (target == null)
                    {
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&', args[1] + " " + config.getConfig().getString("player-not-found")));
                        return false;
                    }
                    //Hedef doğru ise
                    else
                    {
                        if (!chat.getPlayerList().contains(target))
                        {
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getDisplayName() + " " + config.getConfig().getString("target-none-group")));
                        }
                        else
                        {
                            chat.removeplayerList(target);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&',target.getDisplayName() + " " + config.getConfig().getString("target-you-left")));
                            target.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " + config.getConfig().getString("target-left")));
                        }
                    }
                }
                //kendin sohbetten çıkmak isterse
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
                        player.sendMessage(ChatColor.translateAlternateColorCodes('&',player.getDisplayName() + " " + config.getConfig().getString("none-group")));
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
            //Oyuncu komut yanlış girerse veya girmez ise
            else
            {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("how-to-use-command-all")));
                //player.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("not-found-command")));
            }
        }
        //Kullanıcı konsoldan komut kullanmak ister ise
        else
        {
            Bukkit.getLogger().info(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("not-console-command")));
            return false;
        }
        return true;
    }
}
//Player target = Bukkit.getServer().getPlayer(args[0]);
//chat.addplayerList(target);
//player.sendMessage(ChatColor.GREEN.toString() + player.getName() + " Adlı oyuncu Gruba Eklendi");
//target.sendMessage(ChatColor.GREEN.toString() + target.getName() + " Gruba Eklendin");