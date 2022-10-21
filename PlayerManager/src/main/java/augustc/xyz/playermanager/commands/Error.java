package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Error {

    public static Boolean returnError(String error, CommandSender sender){
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.RED + error);
        }else{
            PlayerManager.getPlugin().getServer().getLogger().severe(error);
        }
        return true;
    }

    public static Boolean returnNoPermissionError(CommandSender sender){
        String error = "You do not have permission to use this command.";
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.RED + error);
        }else{
            PlayerManager.getPlugin().getServer().getLogger().severe(error);
        }
        return true;
    }

}
