package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class RankupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){
            Player executor = (Player) sender;
            if(!executor.hasPermission("player.addrank")){
                return Error.returnNoPermissionError(sender);
            }
        }

        if(args.length < 1){
            return Error.returnError("No player and rank provided.", sender);
        }

        Player p = Bukkit.getPlayerExact(args[0]);

        if(p == null){
            return Error.returnError("Unknown player.", sender);
        }

        if(args.length < 2) {
            return Error.returnError("No rank provided.", sender);
        }

        String rank = args[1];

        if (!Arrays.asList(PlayerManager.getPerms().getGroups()).contains(args[1])) {
            return Error.returnError("Rank does not exist.", sender);
        }

        Bukkit.dispatchCommand(sender, "lp user " + p.getName() + " permission set player.group." + rank + " true");
        if (p.hasPermission("player.rankup")) {
            Bukkit.dispatchCommand(sender, "lp user " + p.getName() + " parent add " + rank);
        }


        return true;
    }
}
