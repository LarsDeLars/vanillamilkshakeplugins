package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e){

        PlayerProfile.get(e.getPlayer()).setAfk(false);

    }

}
