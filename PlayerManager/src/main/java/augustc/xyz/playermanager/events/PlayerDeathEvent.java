package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerDeathEvent implements Listener {

    @EventHandler
    public void onDeath(org.bukkit.event.entity.PlayerDeathEvent e){

        PlayerProfile.get(e.getEntity()).setBackLocation(e.getEntity().getLocation());

    }

}
