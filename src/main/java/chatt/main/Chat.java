package chatt.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Chat implements Listener {
    public File file;
    public ChatConfig config;
    public Chat(ChatConfig config)
    {
        this.config = config;
    }
    private final List<Player> playerList = new ArrayList<Player>();
    public void addplayerList(Player player)
    {
        playerList.add(player);
    }
    public void removeplayerList(Player player)
    {
        playerList.remove(player);
    }
    public List<Player> getPlayerList() {
        return playerList;
    }
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        //Oyuncu yetkiye veya op'a sahip mi
        if (player.hasPermission("privatechat.true"))
        {
            //Oyuncu takımlisttinde var ise
            if (playerList.contains(player.getPlayer()))
            {
                //eğer oyuncu ! ile mesaj göndermisse herkese göndericek kod
                if (e.getMessage().startsWith("!"))
                {
                    String modifiedmessage = e.getMessage().substring(1);
                    e.setCancelled(false);
                    e.setMessage(modifiedmessage);
                }
                //hayır ise sadece özel sohbete göndericek kod
                else
                {
                    e.setCancelled(true);
                    for (Player target : playerList)
                    {
                        target.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("Title") + " " + ChatColor.GREEN.toString()+ player.getDisplayName() + config.getConfig().getString("Title-symbol") + " " + e.getMessage()));
                    }
                }
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        for (Player players : playerList)
        {
            players.sendMessage(ChatColor.translateAlternateColorCodes('&',  config.getConfig().getString("Title")+ " " + player.getDisplayName() +  " " + config.getConfig().getString("player-game-quit")));
        }
        removeplayerList(player);
    }
}