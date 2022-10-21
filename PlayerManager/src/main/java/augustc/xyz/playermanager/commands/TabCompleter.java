package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import augustc.xyz.playermanager.files.serverdata;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if(command.getName().equals("namecolour")){
            if(args.length == 1){
                List<String> colours = new ArrayList<>();
                String[] colourArray = {"aqua", "black", "blue", "dark_aqua", "dark_blue", "dark_gray", "dark_green", "dark_purple", "dark_red", "gold", "gray", "green", "light_purple", "red", "white", "yellow", "off"};
                for(String colour : colourArray){
                    if(colour.startsWith(args[0])){
                        colours.add(colour);
                    }

                }
                return colours;

            }
        } else if(command.getName().equals("addrank")){
            if(sender.hasPermission("player.addrank")){
                if(args.length == 2){
                    List<String> ranks = new ArrayList<>();
                    for(String rank : PlayerManager.getPerms().getGroups()){
                        if(rank.startsWith(args[1])){
                            ranks.add(rank);
                        }
                    }
                    return ranks;
                }
            }

        } else if(command.getName().equals("home") || command.getName().equals("delhome")){
            if(sender.hasPermission("homes.multiple") && args.length == 1){
                List<String> homes = new ArrayList<>();
                for(String home : PlayerProfile.get((Player) sender).getHomes().keySet()){
                    if(home.startsWith(args[0])){
                        homes.add(home);
                    }
                }
                return homes;
            }

        } else if(command.getName().equals("setspawn") || command.getName().equals("delspawn")){

            if(sender instanceof Player){
                if(sender.hasPermission("server.setspawn")){

                    List<String> allSpawns = new ArrayList<>();
                    allSpawns.add("default");
                    allSpawns.add("newbies");

                    ConfigurationSection configurationSection = serverdata.getServerData().getConfigurationSection("spawn");
                    if(configurationSection == null){
                        return allSpawns;
                    }
                    Set<String> spawns = configurationSection.getValues(false).keySet();
                    for(String spawn : spawns){
                        if(!allSpawns.contains(spawn)){
                            allSpawns.add(spawn);
                        }
                    }
                    return allSpawns;

                }
            }

        }

        return null;
    }
}
