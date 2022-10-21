package augustc.xyz.playermanager.commands.message;

import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReplyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;
            PlayerProfile profile = PlayerProfile.get(p);

            if(!p.hasPermission("player.message")){
                return Error.returnNoPermissionError(sender);
            }

            Player target = profile.getLastMessaged();

            if(target == null){
                return Error.returnError("You have nobody to reply to.", sender);
            }

            if(!target.isOnline()){
                return Error.returnError(target.getDisplayName() + " is currently not online.", sender);
            }

            if(args.length < 1){
                return Error.returnError("Not enough arguments. /r <message>", sender);
            }

            String message = "";
            for(String word : args){
                message += word + " ";
            }
            message = message.substring(0, message.length()-1);

            p.sendMessage(ChatColor.GOLD + "You" + ChatColor.BOLD + " » " + ChatColor.RESET + target.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);
            target.sendMessage(p.getDisplayName() + ChatColor.GOLD + ChatColor.BOLD + " » " + ChatColor.GOLD + "you" + ChatColor.GRAY + ": " + ChatColor.WHITE + message);

            PlayerProfile targetProfile = PlayerProfile.get(target);
            targetProfile.setLastMessaged(p);

        }

        return true;
    }
}
