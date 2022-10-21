package augustc.xyz.playermanager.commands.homes;

import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DelhomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("player.sethome")){
                return Error.returnNoPermissionError(sender);
            }

            PlayerProfile profile = PlayerProfile.get(p);

            if(profile.getHomes().size() < 1){
                return Error.returnError("You currently have not set any homes.", sender);
            }

            String name = "home";

            if(p.hasPermission("player.sethome.multiple")){
                if(args.length < 1){
                    return Error.returnError("Please provide a name, /dethome <name>", sender);
                }else{
                    name = args[0];
                }
            }

            if(!profile.getHomes().containsKey(name)){
                return Error.returnError("You do not have a home named: " + name + ".", sender);
            }

            profile.delhome(name);
            p.sendMessage(ChatColor.GREEN + "Successfully removed your home: " + name + ".");
        }
        return true;

    }
}
