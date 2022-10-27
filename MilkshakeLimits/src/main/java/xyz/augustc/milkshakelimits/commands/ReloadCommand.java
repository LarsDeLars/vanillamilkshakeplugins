package xyz.augustc.milkshakelimits.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.augustc.milkshakelimits.MilkshakeLimits;
import xyz.augustc.milkshakelimits.files.LimitsFile;

public class ReloadCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(args.length < 1){
            return true;
        }

        if(args[0].equals("reload")){
            if(sender instanceof Player && !sender.hasPermission("limits.reload")){
                return true;
            }


            LimitsFile.reload();
            MilkshakeLimits.getPlugin().reloadConfig();

            if(sender instanceof Player){
                sender.sendMessage(ChatColor.GREEN + "Reloaded all files for MilkshakeLimits.");
            }else{
                MilkshakeLimits.getPlugin().getLogger().info("Reloaded all files for MilkshakeLimits.");
            }
        }


        return true;
    }
}
