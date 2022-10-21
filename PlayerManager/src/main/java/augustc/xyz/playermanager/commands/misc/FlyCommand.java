package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Boolean senderIsPlayer = sender instanceof Player;

        if(!sender.hasPermission("player.fly") && senderIsPlayer){
            return Error.returnNoPermissionError(sender);
        }

        Player target = null;

        if(args.length > 0 &&!(senderIsPlayer && !sender.hasPermission("player.fly.others"))){
            target = Bukkit.getPlayerExact(args[0]);
        }else if(senderIsPlayer){
            target = (Player) sender;
        }

        if(target == null){
            return Error.returnError("Unknown player: " + args[0], sender);
        }

        if(target.getAllowFlight()){
            target.setAllowFlight(false);
            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your fly is now " + ChatColor.RED + "disabled" + ChatColor.LIGHT_PURPLE + ".");
            return info("Disabled fly for " + target.getDisplayName(), sender);
        }else{
            target.setAllowFlight(true);
            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your fly is now " + ChatColor.GREEN + "enabled" + ChatColor.LIGHT_PURPLE + ".");
            return info("Enabled fly for " + target.getDisplayName(), sender);
        }
    }

    private Boolean info(String info, CommandSender sender){
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.LIGHT_PURPLE + info);
        }else{
            PlayerManager.getPlugin().getServer().getLogger().info(ChatColor.stripColor(info));
        }
        return true;
    }
}
