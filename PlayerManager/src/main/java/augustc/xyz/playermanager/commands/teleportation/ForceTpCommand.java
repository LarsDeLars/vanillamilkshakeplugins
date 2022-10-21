package augustc.xyz.playermanager.commands.teleportation;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.SafeTeleport;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ForceTpCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.tp.force")){
                return Error.returnNoPermissionError(sender);
            }

            if(args.length > 2){
                ArrayList<Double> coords = new ArrayList<>();
                for(int i = 0; i < 3; i++){
                    if(isNumber(args[i])){
                        coords.add(Double.parseDouble(args[i]));
                    }
                }
                if(coords.size() == 3){
                    Location location = p.getLocation();
                    location.setX(coords.get(0));
                    location.setY(coords.get(1));
                    location.setZ(coords.get(2));
                    p.teleport(location);
                    p.sendMessage(ChatColor.GREEN + "You have been teleported to " + ChatColor.LIGHT_PURPLE + coords.get(0) + ", " + coords.get(1) + ", " + coords.get(2));
                    return true;
                }
            }

            if(args.length < 1){
                return Error.returnError("No player provided.", sender);
            }

            Player target = Bukkit.getPlayerExact(args[0]);

            if(target == null){
                return Error.returnError("Unknown player: " + args[0], sender);
            }

            Player targetTwo = null;

            if(args.length > 1){
                targetTwo = Bukkit.getPlayerExact(args[1]);
            }

            if(targetTwo != null && target != targetTwo){
                if(SafeTeleport.safeTeleport(target, targetTwo.getLocation())){
                    p.sendMessage(ChatColor.GREEN + "Teleported " + target.getDisplayName() + ChatColor.GREEN + " to " + targetTwo.getDisplayName());
                }else{
                    p.sendMessage(target.getDisplayName() + ChatColor.RED + " could not be teleported to " + targetTwo.getDisplayName() + ChatColor.RED + " because there are no safe locations nearby.");
                }
                return true;
            }

            if(SafeTeleport.safeTeleport(p, target.getLocation())){
                target.sendMessage(p.getDisplayName() + ChatColor.GREEN + " has been teleported to you.");
                p.sendMessage(ChatColor.GREEN + "You have been teleported to " + target.getDisplayName());
            }else{
                p.sendMessage(ChatColor.RED + "You could not be teleported to " + target.getDisplayName() + ChatColor.RED + " because there are no safe locations nearby.");
            }

        }

        return true;
    }

    private boolean isNumber(String x){
        try{
            double value = Double.parseDouble(x);
            return true;
        } catch (NumberFormatException e){
            return false;
        }

    }

}
