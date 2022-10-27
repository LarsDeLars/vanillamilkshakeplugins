package xyz.augustc.milkshakelimits.tasks;

import org.bukkit.scheduler.BukkitRunnable;
import xyz.augustc.milkshakelimits.Chunk;

public class ExpireChunks extends BukkitRunnable {

    @Override
    public void run() {
        for(Chunk chunk : Chunk.activeChunks){
            chunk.decreaseExpiryTime();
            if(chunk.getExpiresAfterSeconds() <= 0){
                Chunk.activeChunks.remove(chunk);
            }
        }
    }
}
