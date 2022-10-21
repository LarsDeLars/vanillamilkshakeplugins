package augustc.xyz.playermanager.tasks;

import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

public class IncreaseAfkTimer extends BukkitRunnable {

    @Override
    public void run() {

        for(PlayerProfile player : PlayerProfile.onlinePlayers.values()){
            player.setAfkTimer(player.getAfkTimer() + 1);
            if(player.getAfkTimer() > 300){
                player.setAfk(true);
            }
            if(getDistance(player.getLastLocation(), player.getPlayer().getLocation()) > 1.0){
                player.setAfk(false);
            }
            player.setLastLocation(player.getPlayer().getLocation());
        }


    }

    private double getDistance(Location location1, Location location2){ //3D Pythagoras
        return Math.sqrt( (location1.getX()-location2.getX())*(location1.getX()-location2.getX()) + (location1.getY()-location2.getY())*(location1.getY()-location2.getY()) + (location1.getZ()-location2.getZ())*(location1.getZ()-location2.getZ()));
    }
}
