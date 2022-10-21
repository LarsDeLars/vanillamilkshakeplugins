package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.commands.Error;
import augustc.xyz.playermanager.files.serverdata;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;
            if(p.hasPermission("server.setspawn")){
                return Error.returnNoPermissionError(sender);
            }

            if(args.length < 1) {
                return Error.returnError("Please provide the name of this spawn.", sender);
            }

            serverdata.getServerData().set("spawn." + args[0], p.getLocation());
            serverdata.save();
            serverdata.reload();
            p.sendMessage(ChatColor.GREEN + "Spawn " + ChatColor.BOLD + args[0] + ChatColor.GREEN + " set at your current location.");

        }

        return true;
    }
}
