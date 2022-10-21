package augustc.xyz.playermanager.commands.message;

import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageComand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.message")){
                return Error.returnNoPermissionError(sender);
            }

            if(args.length < 2){
                return Error.returnError("Not enough arguments provided. /msg <player> <message>", sender);
            }

            Player target = Bukkit.getPlayerExact(args[0]);

            if(target == null){
                return Error.returnError("Unkown player: " + args[0], sender);
            }

            String message = "";
            for(int i = 1; i < args.length; i++){
                message += args[i];
                if(i+1 < args.length){
                    message += " ";
                }
            }

            p.sendMessage(ChatColor.GOLD + "You" + ChatColor.BOLD + " » " + ChatColor.RESET + target.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.WHITE + message);
            target.sendMessage(p.getDisplayName() + ChatColor.GOLD + ChatColor.BOLD + " » " + ChatColor.GOLD + "you" + ChatColor.GRAY + ": " + ChatColor.WHITE + message);

            PlayerProfile profile = PlayerProfile.get(p);
            PlayerProfile targetProfile = PlayerProfile.get(target);

            profile.setLastMessaged(target);
            targetProfile.setLastMessaged(p);

        }
        return true;

    }
}
