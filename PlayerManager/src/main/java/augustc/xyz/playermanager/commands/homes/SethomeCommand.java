package augustc.xyz.playermanager.commands.homes;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SethomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(!p.hasPermission("player.sethome")){
                return Error.returnNoPermissionError(sender);
            }

            if(p.hasPermission("player.sethome.multiple") && args.length < 1){
                return Error.returnError("Please provide a name, /sethome <name>", sender);
            }

            String name = "home";
            Location location = p.getLocation();
            PlayerProfile profile = PlayerProfile.onlinePlayers.get(p);

            if(!p.hasPermission("player.sethome.multiple") && !p.hasPermission("player.sethome.multiple")){
                profile.sethome("home", location);
                p.sendMessage(ChatColor.GREEN + "Home set at current location.");
                return true;
            }

            name = args[0];

            if(p.hasPermission("player.sethome.unlimited")){
                profile.sethome(name, location);
                p.sendMessage(ChatColor.GREEN + "Home " + ChatColor.LIGHT_PURPLE + name + ChatColor.GREEN + " set at current location.");
                return true;
            }

            if(!profile.getHomes().containsKey(name) && profile.getHomes().size()+1 > profile.getHomesAllowed()){
                return Error.returnError("You can only set a maximum of " + ChatColor.BOLD + profile.getHomesAllowed() + ChatColor.RED + " homes.", sender);
            }

            profile.sethome(name, location);
            p.sendMessage(ChatColor.GREEN + "Home " + ChatColor.LIGHT_PURPLE + name + ChatColor.GREEN + " set at current location.");

        }
        return true;
    }

}
