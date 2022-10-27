package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PingCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        boolean console = !(sender instanceof Player);

        if(!console && !sender.hasPermission("player.ping")){
            Error.returnNoPermissionError(sender);
        }

        Player target = null;

        if(console || sender.hasPermission("player.ping.others")){
            if(args.length < 1){
                if(console){
                    Error.returnError("No player provided.", sender);
                }
            }else{
                target = Bukkit.getPlayerExact(args[0]);
            }
        }

        if(target == null){
            if(console){
                Error.returnError("Unknown player: " + args[0], sender);
            }
            target = (Player) sender;
        }

        int ping = target.getPing();

        ChatColor colour = ChatColor.RED;

        if(ping < 50){
            colour = ChatColor.GREEN;
        }else if(ping < 100){
            colour = ChatColor.YELLOW;
        }else if(ping < 150){
            colour = ChatColor.GOLD;
        }

        if(console){
            PlayerManager.getPlugin().getLogger().info(target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s ping is " + colour + ping + "ms");
        }else{
            if((Player) sender == target){
                sender.sendMessage(ChatColor.LIGHT_PURPLE + "Your ping is " + colour + ping + "ms");
            }else{
                sender.sendMessage(target.getDisplayName() + ChatColor.LIGHT_PURPLE + "'s ping is " + colour + ping + "ms");
            }
        }

        return true;
    }
}
