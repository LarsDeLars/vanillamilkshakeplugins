package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.SafeTeleport;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("server.back")){
                Error.returnNoPermissionError(sender);
            }

            SafeTeleport.safeTeleport(p, PlayerProfile.get(p).getBackLocation());
            p.sendMessage(ChatColor.GREEN + "Teleported to previous location.");

        }

        return true;
    }
}
