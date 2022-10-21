package augustc.xyz.playermanager.commands.gamemode;

import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SurvivalCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.gamemode.all") || !p.hasPermission("player.gamemode.survival")){
                return Error.returnNoPermissionError(sender);
            }

            Player target = p;

            if(args.length > 0 && p.hasPermission("player.gamemode.others")){
                target = Bukkit.getPlayerExact(args[0]);
            }

            if(target == null){
                return Error.returnError("Unknown player: " + args[0], sender);
            }

            if(target.getGameMode() == GameMode.SURVIVAL){
                return Error.returnError("Target is already in survival mode.", sender);
            }

            boolean isFlying = target.isFlying();

            target.setGameMode(GameMode.SURVIVAL);
            if(isFlying && target.hasPermission("player.fly")){
                target.setAllowFlight(true);
                target.setFlying(true);
                target.sendMessage(ChatColor.LIGHT_PURPLE + "Your fly is now " + ChatColor.GREEN + "enabled" + ChatColor.LIGHT_PURPLE + ".");
            }

            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your gamemode is now " + ChatColor.GOLD + "survival");
            if(target != p){
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Gamemode for " + target.getDisplayName() + ChatColor.LIGHT_PURPLE + " is now " + ChatColor.GOLD + "survival");
            }

        }

        return true;
    }
}
