package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.SafeTeleport;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ForceTpHereCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.tp.force")){
                return Error.returnNoPermissionError(sender);
            }

            if(args.length < 1){
                return Error.returnError("No player provided.", sender);
            }

            Player target = Bukkit.getPlayerExact(args[0]);

            if(target == null){
                return Error.returnError("Unknown player: " + args[0], sender);
            }

            if(SafeTeleport.safeTeleport(target, p.getLocation())){
                p.sendMessage(target.getDisplayName() + ChatColor.GREEN + " has been teleported to you.");
                target.sendMessage(ChatColor.GREEN + "You have been teleported to " + p.getDisplayName());
            }else{
                p.sendMessage(target.getDisplayName() + ChatColor.RED + " could not be teleported to you because there are no safe locations nearby.");
            }

        }

        return true;
    }
}
