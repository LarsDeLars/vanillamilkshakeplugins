package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AfkCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;
            PlayerProfile profile = PlayerProfile.get(p);

            profile.setLastLocation(p.getLocation());
            if(profile.isAfk()){
                profile.setAfk(false);
            }else{
                profile.setAfk(true);
            }

        }

        return true;
    }
}
