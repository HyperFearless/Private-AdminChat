package chatt.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
    private List<Player> playerList = new ArrayList<>();
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
        if (playerList.contains(player.getPlayer())) {
            e.setCancelled(true);
            for (Player target : playerList)
            {
                target.sendMessage(ChatColor.translateAlternateColorCodes('&',config.getConfig().getString("Title") + " " +ChatColor.GREEN.toString()+ player.getDisplayName() + config.getConfig().getString("Title-symbol") + " " + e.getMessage()));
            }
        }
    }
}