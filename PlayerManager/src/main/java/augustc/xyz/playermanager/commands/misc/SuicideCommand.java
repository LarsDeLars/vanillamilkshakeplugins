package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.commands.Error;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SuicideCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.suicide")){
                return Error.returnNoPermissionError(sender);
            }

            p.setHealth(0);
            p.sendMessage(ChatColor.GOLD + "You have killed yourself. Use /back to teleport to your death location.");

        }
        return true;
    }
}
