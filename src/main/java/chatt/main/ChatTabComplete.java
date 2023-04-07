package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ChatTabComplete implements TabCompleter {

    Chat chat;
    public ChatTabComplete(Chat chat)
    {
        this.chat = chat;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {

        //Komutları tab ile otomatik tamamalama
        List<String > list = new ArrayList<>();
        if (args.length == 1)
        {
            return StringUtil.copyPartialMatches(args[0], Arrays.asList("ekle","reload","cıkart","cık","bak"), new ArrayList<>());
        }
        else if (args.length == 2)
        {
            if (args[0].equalsIgnoreCase("cıkart"))
            {
                //Oyuncular listeleniyor
                List<String > names = new ArrayList<>();
                for (Player player : chat.getPlayerList())
                {
                    //Sadece gruptaki oyuncular alınıyor
                    names.add(player.getName());
                }
                return StringUtil.copyPartialMatches(args[1],names,new ArrayList<>());
            }
            else if (args[0].equalsIgnoreCase("ekle"))
            {
                //Oyuncular listeleniyor
                List<String> names = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    //Sadece op veya perme sahip oyuncular alınıyor
                    if (player.hasPermission("privatechat.grup")) {
                        names.add(player.getName());
                    }
                }
                return StringUtil.copyPartialMatches(args[1], names, new ArrayList<>());
            }
            else
            {
                //Oyuncular listeleniyor
                List<String> names = new ArrayList<>();
                for (Player player : Bukkit.getOnlinePlayers()) {
                    //Sadece op veya perme sahip oyuncular alınıyor
                    if (player.hasPermission("privatechat.grup")) {
                        names.add(player.getName());
                    }
                }
                return StringUtil.copyPartialMatches(args[1], names, new ArrayList<>());
            }
        }
        return new ArrayList<>();
    }
}
