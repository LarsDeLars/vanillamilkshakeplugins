package augustc.xyz.playermanager.commands.homes;

import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShowhomesCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;
            PlayerProfile profile = PlayerProfile.get(p);

            if(profile.getHomes().size() < 1){
                return Error.returnError("You did not set a home. Use /sethome to set one.", sender);
            }

            String message = ChatColor.GREEN + "Homes of " + p.getDisplayName() + ": " + ChatColor.LIGHT_PURPLE;
            for(String home : profile.getHomes().keySet()){
                message += home + ", ";
            }
            message = message.substring(0, message.length()-2) + ".";
            p.sendMessage(message);
        }

        return true;
    }
}
