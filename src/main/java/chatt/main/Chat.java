package chatt.main;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Chat implements Listener {
    public ChatConfig config;
    public BossBar bossBar;
    private final List<Player> playerList = Collections.synchronizedList(new ArrayList<>());
    public Chat(ChatConfig config)
    {
        this.config = config;
        if (config.getConfig().contains("Boosbar-Color"))
        {
            BarColor color = BarColor.valueOf(config.getConfig().getString("Boosbar-Color"));
            bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', config.getConfig().getString("Boosbar")), color, BarStyle.SOLID);
        }
        else
        {
            BarColor color = BarColor.valueOf("RED");
            bossBar = Bukkit.createBossBar(ChatColor.translateAlternateColorCodes('&', "&7[&c&lYönetim Sohbet&7]"), color, BarStyle.SOLID);
        }
    }

    //Oyuncuyu gruba ekleme
    public void addplayerList(Player player)
    {
        playerList.add(player);
        bossBar.addPlayer(player);
    }
    //Oyuncuyu gruptan cıkartma
    public void removeplayerList(Player player)
    {
        playerList.remove(player);
        bossBar.removePlayer(player);
    }
    //Gruptaki oyuncuların verisini alma
    public List<Player> getPlayerList() {
        return playerList;
    }
    public void clearList()
    {
        playerList.clear();
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        Player player = e.getPlayer();
        //Oyuncu yetkiye veya op'a sahip mi
        if (player.hasPermission("privatechat.true"))
        {
            //Oyuncu takımlisttinde var ise
            if (playerList.contains(player))
            {
                //eğer oyuncu ! ile mesaj göndermiş ise herkese göndericek kod
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
    //Oyuncu oyundan cıkar ise
    @EventHandler
    public void onQuit(PlayerQuitEvent e)
    {
        Player player = e.getPlayer();
        //playerlist eventini bekliyecek veya beklettirecek
        synchronized (playerList)
        {
            if (playerList.contains(player))
            {
                for (Player players : playerList)
                {
                    players.sendMessage(ChatColor.translateAlternateColorCodes('&',  config.getConfig().getString("Title")+ " " + player.getDisplayName() +  " " + config.getConfig().getString("player-game-quit")));
                }
                removeplayerList(player);
            }
        }
    }
}