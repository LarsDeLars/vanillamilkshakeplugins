package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.vanish")){
                return Error.returnNoPermissionError(sender);
            }

            //TODO finish command

        }

        return true;
    }
}
