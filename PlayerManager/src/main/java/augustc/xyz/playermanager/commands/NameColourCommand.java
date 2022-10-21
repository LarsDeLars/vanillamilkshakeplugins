package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.files.playerdata;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;

public class NameColourCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;

            if(!p.hasPermission("player.namecolour")){
                return Error.returnNoPermissionError(sender);
            }


            Player target = p;

            if(args.length < 1){
                return Error.returnError("No colour provided.", sender);
            }


            if(p.hasPermission("player.namecolour.others") && args.length > 1){
                Player targetTry = Bukkit.getPlayerExact(args[1]);
                if(targetTry != null){
                    target = targetTry;
                }

            }

            String name = target.getDisplayName();
            if (!String.valueOf(name.charAt(0)).matches("^[a-zA-Z0-9_#]*$")) { //if it starts with a namecolour character
                name = name.substring(2); //strip namecolour
            }

            if(args[0].equals("off")){
                target.setDisplayName(name);

                FileConfiguration config = playerdata.getPlayerData(target);
                config.set("displayname", target.getDisplayName());
                playerdata.save(target, config);

                target.sendMessage(ChatColor.WHITE + "Namecolour" + ChatColor.GRAY + " cleared.");
                if(target != p){
                    p.sendMessage(ChatColor.WHITE + "Namecolour" + ChatColor.GRAY + " for" + target.getDisplayName() + "cleared.");
                }

            }else {
                String[] notAllowed = {"BOLD", "RESET", "ITALIC", "MAGIC", "STRIKETHROUGH", "UNDERLINE"};

                if (Arrays.asList(notAllowed).contains(args[0].toUpperCase())) {
                    p.sendMessage(ChatColor.RED + "That is not a valid colour.");
                } else {
                    try {
                        ChatColor color = ChatColor.valueOf(args[0].toUpperCase());

                        target.setDisplayName(color + name);
                        PlayerProfile.onlinePlayers.get(target).savePlayerData();

                        target.sendMessage(ChatColor.WHITE + "Namecolour" + ChatColor.GRAY + " changed to " + color + ChatColor.BOLD + color.name().toLowerCase().replace('_', ' ') + ChatColor.GRAY + ".");

                        if (target != p) {
                            p.sendMessage(ChatColor.WHITE + "Namecolour" + ChatColor.GRAY + " for " + p.getName() + "changed to " + color + ChatColor.BOLD + color.name().toLowerCase().replace('_', ' ') + ChatColor.GRAY + ".");
                        }

                    } catch (Exception e) {
                        p.sendMessage(ChatColor.RED + "That is not a valid colour.");
                    }
                }

            }
        }


        return true;
    }
}
