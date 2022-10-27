package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.me")){
                return Error.returnNoPermissionError(sender);
            }

            String message = "";
            for(String word : args){
                message += word + " ";
            }
            message = message.substring(0, message.length()-1);

            PlayerManager.getPlugin().getServer().broadcastMessage(ChatColor.GRAY + "> " + p.getDisplayName() + ChatColor.GRAY + " " + message);

        }

        return true;
    }
}
