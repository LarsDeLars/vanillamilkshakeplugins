package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.SafeTeleport;
import augustc.xyz.playermanager.TeleportRequest;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAccept implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;


            for(TeleportRequest request : TeleportRequest.teleportRequests){

                if(request.getAsked() == p){

                    TeleportRequest.teleportRequests.remove(request);
                    Player target = request.getAsker();

                    p.sendMessage(ChatColor.GRAY + "Accepting teleport request from " + target.getDisplayName());
                    target.sendMessage(p.getDisplayName() + ChatColor.GRAY + " has accepted your teleport request.");

                    if(request.getTphere()){
                        Player placeholder = p;
                        p = target;
                        target = placeholder;
                    }

                    if(SafeTeleport.safeTeleport(target, p.getLocation())){
                        p.sendMessage(target.getDisplayName() + ChatColor.GREEN + " has been teleported to you.");
                        target.sendMessage(ChatColor.GREEN + "You have been teleported to " + p.getDisplayName());
                    }else{
                        p.sendMessage(target.getDisplayName() + ChatColor.RED + " could not be teleported to you because there are no safe locations nearby.");
                    }


                    return true;

                }

            }

            p.sendMessage(ChatColor.RED + "You have no pending teleport requests.");

        }

        return true;
    }
}
