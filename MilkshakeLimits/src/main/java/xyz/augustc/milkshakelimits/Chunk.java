package xyz.augustc.milkshakelimits;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Chunk {

    private static Point getCoordsAtLocation(Location location){

        double x = location.getX();
        double z = location.getZ();

        int chunk_x = (int) (Math.floor(x/16)*16);
        int chunk_z = (int) (Math.floor(z/16)*16);


        return new Point(chunk_x, chunk_z);

    }

    public static Chunk getChunkAtLocation(Location location){
        Point coords = getCoordsAtLocation(location);
        for(Chunk chunk : activeChunks){
            if((coords.getX() == chunk.getChunk().getX()) && (coords.getY() == chunk.getChunk().getY())){
                return chunk;
            }
        }
        Chunk chunk = new Chunk(location);
        chunk.countAllBlocks();
        activeChunks.add(chunk);
        return chunk;
    }

    private void countAllBlocks(){

        World world = location.getWorld();

        Set<Material> blocks = MilkshakeLimits.getLimits().getAllBlocks().keySet();
        Set<String> groups = MilkshakeLimits.getLimits().getGroups().keySet();
        int chunkX = (int) chunk.getX();
        int chunkZ = (int) chunk.getY();

        for(String group : groups){
            amountPlaced.put(group, 0);
        }

        for(int x = chunkX; x < chunkX+16; x++){
            for(int z = chunkZ; z < chunkZ+16; z++){
                location.setX(x);
                location.setZ(z);
                for(int y = -64; y <= world.getHighestBlockAt(location).getY(); y++){

                    Material block = world.getBlockAt(x,y,z).getType();
                    if(blocks.contains(block)){
                        String group = MilkshakeLimits.getLimits().getGroup(block);
                        amountPlaced.put(group, amountPlaced.get(group)+1);
                    }

                }
            }
        }

    }

    public static ArrayList<Chunk> activeChunks = new ArrayList<>();

    public Point getChunk() {
        return chunk;
    }

    private final Point chunk;

    public Integer getAmountPlaced(String group) {
        if(amountPlaced.containsKey(group)) {
            return amountPlaced.get(group);
        }
        return 0;
    }

    public void increaseAmountPlaced(String group, Integer amount){
        expiresAfterSeconds = deleteChunksAfterSeconds;
        if(!amountPlaced.containsKey(group)){
            countAllBlocks();
        }
        amountPlaced.put(group, amountPlaced.get(group)+amount);
    }

    public void decreaseAmountPlaced(String group, Integer amount){
        expiresAfterSeconds = deleteChunksAfterSeconds;
        if(!amountPlaced.containsKey(group)){
            countAllBlocks();
        }
        amountPlaced.put(group, amountPlaced.get(group)-amount);
    }

    private HashMap<String, Integer> amountPlaced;

    private Location location;

    public void decreaseExpiryTime(){
        expiresAfterSeconds--;
    }

    public int getExpiresAfterSeconds() {
        return expiresAfterSeconds;
    }

    private int expiresAfterSeconds;
    
    public static final int deleteChunksAfterSeconds = MilkshakeLimits.getPlugin().getConfig().getInt("DeleteChunksFromMemoryAfter");

    public Chunk(Location location) {
        this.expiresAfterSeconds = deleteChunksAfterSeconds;
        this.location = location;
        this.chunk = getCoordsAtLocation(location);
        this.amountPlaced = new HashMap<>();
        activeChunks.add(this);
    }
}
