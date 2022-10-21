package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.files.playerdata;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitEvent implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e){

        Player p = e.getPlayer();

        e.setQuitMessage(ChatColor.YELLOW + p.getDisplayName() + ChatColor.YELLOW + " left the game");

        PlayerProfile.get(p).savePlayerData();

        PlayerProfile.onlinePlayers.remove(p);

    }

}
