package xyz.augustc.milkshakelimits.events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;
import xyz.augustc.milkshakelimits.Chunk;
import xyz.augustc.milkshakelimits.Limits;
import xyz.augustc.milkshakelimits.MilkshakeLimits;

public class BreakBlockEvent implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){

        Block b = e.getBlock();
        Material block = b.getType();
        Limits limits = MilkshakeLimits.getLimits();

        if(limits.hasLimit(block)){

            String group = limits.getGroup(block);

            Chunk chunk = Chunk.getChunkAtLocation(b.getLocation());
            chunk.decreaseAmountPlaced(group, 1);

        }

    }

}
