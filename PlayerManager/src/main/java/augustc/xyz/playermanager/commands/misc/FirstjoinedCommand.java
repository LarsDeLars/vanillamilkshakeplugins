package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FirstjoinedCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;
            Player target = p;

            if(!p.hasPermission("player.firstjoined")){
                return Error.returnNoPermissionError(sender);
            }

            if(args.length > 0 && p.hasPermission("player.firstjoined.others")){
                Player targetTry = Bukkit.getPlayerExact(args[0]);
                if(targetTry != null){
                    target = targetTry;
                }
            }

            PlayerProfile targetProfile = PlayerProfile.get(target);
            String firstjoined = targetProfile.getFirstJoined();

            p.sendMessage(target.getDisplayName() + ChatColor.GREEN + " joined the server for the first time on: " + firstjoined);

        }
        return true;
    }
}
