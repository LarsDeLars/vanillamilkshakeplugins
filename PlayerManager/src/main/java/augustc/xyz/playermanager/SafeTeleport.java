package augustc.xyz.playermanager;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class SafeTeleport {

    public static Boolean safeTeleport(Player player, Location location){

        World world = location.getWorld();
        Integer tries = 0;
        final Integer maxTries = 1000;
        Location next = location;
        while(moveToSafety(ground(next, world), world) == null){
            tries++;
            next = getNextLocation(next);
            if(tries > maxTries){
                player.sendMessage(ChatColor.RED + "Could not teleport you, because no safe location was found.");
                return false;
            }
        }
        player.teleport(next);
        return true;

    }

    private static Location ground(Location location, World world){

        double x = location.getX();
        int y = (int) location.getY();
        double z = location.getZ();

        while (!world.getBlockAt((int) x, y -1, (int) z).getType().isSolid()){ //floating
            y--;
            if(y < -64){ //no safe block found
                return null;
            }
        }
        while(world.getBlockAt((int) x, y, (int) z).getType().isSolid() || world.getBlockAt((int) x, y+1, (int) z).getType() != Material.AIR){ //suffocating
            y++;
            if(y > 320){ //no safe block found
                return null;
            }
        }
        location.setY(y);
        return location;
    }

    private static Boolean isUnsafe(Block block){
        List<Material> unsafeBlocks = new ArrayList<>();
        unsafeBlocks.add(Material.LAVA);
        unsafeBlocks.add(Material.MAGMA_BLOCK);
        unsafeBlocks.add(Material.POWDER_SNOW);
        return unsafeBlocks.contains(block.getType());
    }



    private static Location moveToSafety(Location location, World world){

        if(location == null){
            return null;
        }

        if(isUnsafe(world.getBlockAt(location))){
            if(isUnsafe(world.getHighestBlockAt(location))){
                return null;
            }
            location.setY(world.getHighestBlockAt(location).getY() + 1);
            return location;
        }
        return location;

    }

    private static Location getNextLocation(Location location){

        int x = (int) location.getX();
        int z = (int) location.getZ();
        Integer[] coords = getNext(x, z);
        location.setX(coords[0]);
        location.setZ(coords[1]);
        return location;

    }

    //Spiral algoritm from https://gist.github.com/DizMizzer/57c60c757643dc7e5a70a9b0e05b7fb6
    private static Integer[] getNext(int x, int y) {

        //Corners
        if (x == y) {
            //Rechtsboven
            if (x > 0)
                return new Integer[]{x - 1, y};
            //linksonder
            if (x < 0)
                return new Integer[]{x + 1, y};
            //Midden
            if (x == 0)
                return new Integer[]{1, 0};
        }

        if (x == -y) {
            if (x < 0)
                return new Integer[]{x, y - 1};
            if (x > 0)
                return new Integer[]{x + 1, y};
        }

        //Rechtdoor
        if (x - y > 0) {
            if (Math.abs(x) > Math.abs(y)) {
                return new Integer[]{x, y + 1};
            } else {
                return new Integer[]{x + 1, y};
            }
        }
        if (x - y < 0) {
            if (Math.abs(x) > Math.abs(y)) {
                return new Integer[]{x, y - 1};
            } else {
                return new Integer[]{x - 1, y};
            }
        }
        return new Integer[]{0, 0};
    }


}
