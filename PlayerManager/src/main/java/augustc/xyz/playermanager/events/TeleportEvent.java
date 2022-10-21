package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

public class TeleportEvent implements Listener {

    @EventHandler
    public void onTeleport(PlayerTeleportEvent e){

        if(e.getCause() == TeleportCause.PLUGIN){

            PlayerProfile.onlinePlayers.get(e.getPlayer()).setBackLocation(e.getFrom());

        }
    }

}
