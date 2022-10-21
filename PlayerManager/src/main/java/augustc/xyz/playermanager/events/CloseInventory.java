package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class CloseInventory implements Listener {

    @EventHandler
    public void onCloseInventory(InventoryCloseEvent e){

        Player p = (Player) e.getPlayer();
        PlayerProfile profile = PlayerProfile.get(p);

        if(profile != null){
            if(profile.hasBackpackOpen()){
                profile.setBackpackOpen(false);
                profile.setBackpack(e.getView().getTopInventory());
                profile.savePlayerData();
            }
        }

    }

}
