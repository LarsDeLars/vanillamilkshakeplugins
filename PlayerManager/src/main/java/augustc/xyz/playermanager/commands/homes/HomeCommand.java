package augustc.xyz.playermanager.commands.homes;

import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HomeCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(!(sender instanceof Player)){
            return  Error.returnError("Command is only accessible for players", sender);
        }

        Player p = (Player) sender;

        if(!sender.hasPermission("player.home")){
            return Error.returnNoPermissionError(sender);
        }

        PlayerProfile profile = PlayerProfile.get(p);

        if(profile.getHomes().size() < 1){
            return  Error.returnError("You did not set a home. Use /sethome to set one.", sender);
        }

        if(!p.hasPermission("player.sethome.multiple") && profile.getHomes().containsKey("home")){
            p.teleport(profile.getHome("home"));
            p.sendMessage(ChatColor.LIGHT_PURPLE + "Teleported to home.");
            profile.savePlayerData();
            return true;
        }

        if(args.length < 1){
            return Error.returnError("No name provided. Use /home <name>. To see all your homes use /homes.", sender);
        }

        if(!profile.getHomes().containsKey(args[0])){
            p.sendMessage(ChatColor.RED + "Please use one of your homes:");
            String homelist = "";
            for(String home : profile.getHomes().keySet()){
                homelist += home + ", ";
            }
            p.sendMessage(ChatColor.LIGHT_PURPLE + homelist.substring(0, homelist.length()-2));
            return true;
        }

        p.teleport(profile.getHome(args[0]));
        p.sendMessage(ChatColor.LIGHT_PURPLE + "Teleported to " + ChatColor.BOLD + args[0] + ChatColor.LIGHT_PURPLE + ".");
        profile.savePlayerData();
        return true;
    }
}
