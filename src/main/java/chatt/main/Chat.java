package chatt.main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;
import java.util.List;

public class Chat implements Listener {
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
                target.sendMessage(ChatColor.GREEN.toString()+ player.getDisplayName() + ChatColor.RED + " >> " + e.getMessage());
            }
        }
    }
}
//deneme