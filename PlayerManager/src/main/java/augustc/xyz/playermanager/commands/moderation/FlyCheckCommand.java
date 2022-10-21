package augustc.xyz.playermanager.commands.moderation;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCheckCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player && !sender.hasPermission("player.flycheck")){
            return Error.returnNoPermissionError(sender);
        }

        if(args.length < 1){
            return Error.returnError("No player provided", sender);
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null){
            return Error.returnError("Unkown player: " + target, sender);
        }

        String message = target.getDisplayName() + ChatColor.GREEN + " has /fly permission.";

        if(!target.hasPermission("player.fly")){
            message = target.getDisplayName() + ChatColor.RED + " does not have /fly permission!";
        }

        if(sender instanceof Player){
            sender.sendMessage(message);
        }else{
            PlayerManager.getPlugin().getLogger().info(message);
        }
        return true;

    }
}
