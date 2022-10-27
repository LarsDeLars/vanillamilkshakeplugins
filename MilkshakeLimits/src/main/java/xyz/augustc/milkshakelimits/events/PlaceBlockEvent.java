package xyz.augustc.milkshakelimits.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import xyz.augustc.milkshakelimits.Chunk;
import xyz.augustc.milkshakelimits.Limits;
import xyz.augustc.milkshakelimits.MilkshakeLimits;

public class PlaceBlockEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e){

        Block b = e.getBlock();
        Material block = b.getType();
        Limits limits = MilkshakeLimits.getLimits();

        if(limits.hasLimit(block)){

            String group = limits.getGroup(block);
            int limit = limits.getLimit(group);

            Chunk chunk = Chunk.getChunkAtLocation(b.getLocation());

            if(chunk.getAmountPlaced(group) > limit){
                e.setCancelled(true);
                e.getPlayer().sendMessage(ChatColor.RED + "You can not place more than " + ChatColor.LIGHT_PURPLE + limit + " " + group + ChatColor.RED + " in one chunk!");
            }else{
                chunk.increaseAmountPlaced(group, 1);
            }

        }

    }
}
