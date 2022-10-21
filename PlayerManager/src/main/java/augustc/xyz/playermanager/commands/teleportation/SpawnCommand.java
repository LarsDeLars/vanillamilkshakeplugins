package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.commands.Error;
import augustc.xyz.playermanager.files.serverdata;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.Map;

public class SpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("player.spawn")){
                return Error.returnNoPermissionError(sender);
            }

            ConfigurationSection configurationSection = serverdata.getServerData().getConfigurationSection("spawn");

            if(configurationSection == null){
                p.teleport(PlayerManager.getPlugin().getServer().getWorlds().get(0).getSpawnLocation());
                p.sendMessage(ChatColor.GREEN + "Teleported to spawn.");
                return true;
            }
            Map<String, Object> spawns = configurationSection.getValues(false);
            for(String spawn : spawns.keySet()){
                if(p.hasPermission("server.spawn." + spawn)){
                    p.teleport((Location) spawns.get(spawn));
                    p.sendMessage(ChatColor.GREEN + "Teleported to spawn.");
                    return true;
                }

            }

        }


        return true;
    }
}
