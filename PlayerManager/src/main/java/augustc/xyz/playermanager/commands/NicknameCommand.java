package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.files.playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.nick")) {
                return Error.returnNoPermissionError(sender);
            }


            Player target = p;

            if(args.length < 1){
                return Error.returnError("Not enough arguments. /nick <nickname>", sender);
            }

            if(p.hasPermission("player.nick.others")){
                Player targetTry = Bukkit.getPlayerExact(args[0]);
                if(targetTry != null){
                    target = targetTry;
                }

            }

            String oldName = target.getDisplayName();
            String nameColour = "";
            if (!String.valueOf(oldName.charAt(0)).matches("^[a-zA-Z0-9_#]*$")) { //if it starts with a namecolour character
                nameColour = oldName.substring(0, 2); //strip namecolour
            }

            if(target != p && args.length > 1){
                target.setDisplayName(nameColour + "#" + PlayerManager.translateHexColorCodes(args[1]));
                p.sendMessage(ChatColor.WHITE + "Nickname" + ChatColor.GRAY + " for" + p.getName() + "changed to " + ChatColor.RESET + target.getDisplayName() + ChatColor.GRAY + ".");
            }else{
                if(p.hasPermission("player.nick.rgb")){
                    p.setDisplayName(nameColour + "#" + PlayerManager.translateHexColorCodes(args[0]));
                }else{
                    p.setDisplayName(nameColour + "#" + args[0]);
                }

            }

            if(ChatColor.stripColor(target.getDisplayName()).length() > 15){
                target.setDisplayName(oldName);
                p.sendMessage(ChatColor.RED + "Nickname is too long, use a maximum of 15 characters.");
            }else{
                target.sendMessage(ChatColor.WHITE + "Nickname" + ChatColor.GRAY + " changed to " + ChatColor.RESET + target.getDisplayName() + ChatColor.GRAY + ".");

                PlayerProfile.get(target).savePlayerData();

            }



        }

        return true;
    }
}
