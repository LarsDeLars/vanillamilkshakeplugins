package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.TeleportRequest;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpAsk implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("server.tp.ask")){
                return Error.returnNoPermissionError(sender);
            }

            if(args.length < 1){
                return Error.returnError("No player provided.", sender);
            }

            Player target = Bukkit.getPlayer(args[0]);
            if(target == null){
                return Error.returnError("Unkown player: " + args[0], sender);
            }
            TeleportRequest request = new TeleportRequest(p, target, false, 120);

            TeleportRequest.teleportRequests.removeIf(r -> r.getAsked() == p);
            TeleportRequest.teleportRequests.add(request);

            p.sendMessage(ChatColor.GREEN + "You asked to teleport to " + target.getDisplayName() + ChatColor.GREEN + ". To cancel this request use " + ChatColor.RED + "/tpcancel");
            target.sendMessage(p.getDisplayName() + ChatColor.GOLD + " asks to teleport to " + ChatColor.BOLD + "you" + ChatColor.GOLD + ", use " + ChatColor.GREEN + "/tpaccept " + ChatColor.GOLD + "or " + ChatColor.RED + "/tpdeny");



        }
        return true;
    }
}
