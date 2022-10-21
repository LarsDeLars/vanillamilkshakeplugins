package augustc.xyz.playermanager.commands;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import net.md_5.bungee.api.ChatMessageType;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class BackpackCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player){

            Player p = (Player) sender;
            PlayerProfile profile = PlayerProfile.get(p);
            if(!p.hasPermission("player.backpack")){
                return Error.returnNoPermissionError(sender);
            }

            Integer size = 54;
            while(size > 0){
                if(p.hasPermission("player.backpack.size." + size)){

                    profile.setBackpackOpen(true);
                    Inventory backpack = profile.getBackpack();
                    if(backpack == null){
                        backpack = Bukkit.createInventory(p, size, "Backpack");
                    }
                    p.openInventory(backpack);
                    return true;
                }
                size -= 9;
            }
            PlayerManager.getPlugin().getLogger().severe("No backpack size matches to 'player.backpack.size' permission for: " + p.getName());

        }

        return true;
    }
}
