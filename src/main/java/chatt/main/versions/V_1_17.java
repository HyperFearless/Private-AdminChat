package chatt.main.versions;


import chatt.main.ChatConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class V_1_17 implements VersionSupport{
    public File file;
    public ChatConfig config;
    public V_1_17(ChatConfig config)
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
    @Override
    public void exampleMethod(){

        Bukkit.getLogger().info(ChatColor.GREEN + "test");
    }
}
