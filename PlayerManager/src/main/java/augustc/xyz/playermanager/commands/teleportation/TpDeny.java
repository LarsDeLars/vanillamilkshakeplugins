package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.TeleportRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpDeny implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            for(TeleportRequest request : TeleportRequest.teleportRequests){

                if(request.getAsked() == p){

                    TeleportRequest.teleportRequests.remove(request);
                    Player target = request.getAsker();

                    p.sendMessage(ChatColor.GRAY + "Teleport request from " + target.getDisplayName() + ChatColor.GRAY + " denied.");
                    target.sendMessage(p.getDisplayName() + ChatColor.RED + " has denied your teleport request.");

                    return true;

                }

            }

            p.sendMessage(ChatColor.RED + "You have no pending teleport requests.");

        }

        return true;
    }
}
