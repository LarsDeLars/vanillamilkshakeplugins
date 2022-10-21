package augustc.xyz.playermanager.commands.gamemode;

import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectatorCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.gamemode.all") || !p.hasPermission("player.gamemode.spectator")){
                return Error.returnNoPermissionError(sender);
            }

            Player target = p;

            if(args.length > 0 && p.hasPermission("player.gamemode.others")){
                target = Bukkit.getPlayerExact(args[0]);
            }

            if(target == null){
                return Error.returnError("Unknown player: " + args[0], sender);
            }

            if(target.getGameMode() == GameMode.SPECTATOR){
                return Error.returnError("Target is already in spectator mode.", sender);
            }

            target.setGameMode(GameMode.SPECTATOR);

            target.sendMessage(ChatColor.LIGHT_PURPLE + "Your gamemode is now " + ChatColor.GOLD + "spectator");
            if(target != p){
                p.sendMessage(ChatColor.LIGHT_PURPLE + "Gamemode for " + target.getDisplayName() + ChatColor.LIGHT_PURPLE + " is now " + ChatColor.GOLD + "spectator");
            }

        }

        return true;
    }
}
