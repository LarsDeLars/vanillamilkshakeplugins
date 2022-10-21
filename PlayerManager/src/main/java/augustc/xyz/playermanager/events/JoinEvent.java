package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.files.serverdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.swing.text.DateFormatter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){

        Player p = e.getPlayer();

        PlayerProfile.registerPlayer(p);

        //displayname in join message
        e.setJoinMessage(ChatColor.YELLOW + p.getDisplayName() + ChatColor.YELLOW + " joined the game");

        if(!p.hasPlayedBefore()){

            PlayerProfile profile = PlayerProfile.get(p);

            ConfigurationSection configurationSection = serverdata.getServerData().getConfigurationSection("spawn");

            if(configurationSection == null){
                p.teleport(PlayerManager.getPlugin().getServer().getWorlds().get(0).getSpawnLocation());
            }

            Map<String, Object> spawns = configurationSection.getValues(false);
            p.teleport((Location) spawns.get("newbies"));

            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set server.spawn.newbies false");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set server.spawn.default true");

            LocalDate date = LocalDate.now();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String firstjoined = date.format(format);
            profile.setFirstJoined(firstjoined);

        }

    }

}
