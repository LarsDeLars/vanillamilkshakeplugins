package augustc.xyz.playermanager.commands.misc;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.commands.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HealCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        Boolean senderIsPlayer = sender instanceof Player;

        if(!sender.hasPermission("player.heal") && senderIsPlayer){
            return Error.returnNoPermissionError(sender);
        }

        Player target = null;

        if(args.length > 0 &&!(senderIsPlayer && !sender.hasPermission("player.heal.others"))){
            target = Bukkit.getPlayerExact(args[0]);
        }else if(senderIsPlayer){
            target = (Player) sender;
        }

        if(target == null){
            return Error.returnError("Unknown player: " + args[0], sender);
        }

        target.setHealth(20.0);
        target.setFoodLevel(20);
        target.setSaturation(5f);
        target.setExhaustion(0f);
        target.sendMessage(ChatColor.LIGHT_PURPLE + "You have been healed.");
        return info("Healed " + target.getDisplayName(), sender);
    }

    private Boolean info(String info, CommandSender sender){
        if(sender instanceof Player){
            sender.sendMessage(ChatColor.LIGHT_PURPLE + info);
        }else{
            PlayerManager.getPlugin().getServer().getLogger().info(ChatColor.stripColor(info));
        }
        return true;
    }

}
