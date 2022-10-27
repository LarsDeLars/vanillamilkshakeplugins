package xyz.augustc.milkshakelimits.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> tab = new ArrayList<>();

        if(command.getName().equals("limits")){
            tab.add("reload");
            return tab;
        }

        return null;
    }
}
