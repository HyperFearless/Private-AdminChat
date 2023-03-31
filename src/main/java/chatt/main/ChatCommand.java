package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCommand implements CommandExecutor {
    private final Chat chat;

    public ChatCommand(Chat chat) {
        this.chat = chat;
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
                        player.sendMessage(ChatColor.RED + "Kullanım şekli: /grup ekle [oyuncu adı]");
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
                                player.sendMessage(ChatColor.GREEN + player.getDisplayName() + " Gruba Eklendin");
                            }
                            else
                            {
                                player.sendMessage(ChatColor.RED + "Zaten gruptasın.");
                            }
                        }
                        else if (target == null)
                        {
                            player.sendMessage(ChatColor.RED + args[1] + " adlı oyuncu bulunamadı.");
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
                                    player.sendMessage(ChatColor.GREEN + player.getDisplayName() + " Gruba Eklendin");
                                    player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Adlı oyuncu Gruba Eklendi");
                                    target.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Gruba Eklendin");
                                }
                                else
                                {
                                    chat.addplayerList(target);
                                    player.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Adlı oyuncu Gruba Eklendi");
                                    target.sendMessage(ChatColor.GREEN + target.getDisplayName() + " Gruba Eklendin");
                                }
                            }
                            else
                            {
                                player.sendMessage(ChatColor.RED + "Zaten grupda.");
                            }
                        }
                    }
                }
                else if (args[0].equalsIgnoreCase("çıkart"))
                {
                    if (args.length < 2)
                    {
                        player.sendMessage(ChatColor.RED + "Kullanım şekli: /grup çıkart [oyuncu adı]");
                        return false;
                    }
                    Player target = Bukkit.getPlayerExact(args[1]);
                    if (target == player)
                    {
                        if (!chat.getPlayerList().contains(player))
                        {
                            player.sendMessage(ChatColor.RED + "Bir Grupta değilsin.");
                        }
                        else
                        {
                            chat.removeplayerList(player);
                            player.sendMessage(ChatColor.GREEN + "Gruptan çıktın.");
                        }
                    }
                    else if (target == null)
                    {
                        player.sendMessage(ChatColor.GREEN + args[1] + ChatColor.RED+" adlı oyuncu bulunamadı.");
                        return false;
                    }
                    else
                    {
                        if (!chat.getPlayerList().contains(target))
                        {
                            player.sendMessage(ChatColor.RED + target.getName() + " bir grupta değil.");
                        }
                        else
                        {
                            chat.removeplayerList(target);
                            player.sendMessage(ChatColor.GREEN + target.getName() + " adlı oyuncu gruptan çıkartıldı.");
                            target.sendMessage(ChatColor.RED + "Gruptan çıkartıldın.");
                        }
                    }
                }
                //Oyuncu sayılarını kontrol eder
                else if (args[0].equalsIgnoreCase("bak"))
                {
                    if (!chat.getPlayerList().contains(player))
                    {
                        player.sendMessage(ChatColor.RED + "Grupta değilsin.");
                    }
                    else
                    {
                        int i = 1;
                        player.sendMessage(ChatColor.GREEN + "Gruptaki oyuncular:");
                        for (Player target : chat.getPlayerList())
                        {
                            player.sendMessage( i +". "+ target.getDisplayName());
                            i++;
                        }
                    }
                }
            }
            else
            {
                player.sendMessage(ChatColor.RED + "Geçersiz komut.");
            }
        }
        else
        {
            Bukkit.getLogger().info("Bu komut sadece oyuncular tarafından kullanılabilir.");
            return false;
        }
        return true;
    }
}
//Player target = Bukkit.getServer().getPlayer(args[0]);
//chat.addplayerList(target);
//player.sendMessage(ChatColor.GREEN.toString() + player.getName() + " Adlı oyuncu Gruba Eklendi");
//target.sendMessage(ChatColor.GREEN.toString() + target.getName() + " Gruba Eklendin");