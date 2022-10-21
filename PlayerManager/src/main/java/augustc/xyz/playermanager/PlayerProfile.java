package augustc.xyz.playermanager;

import augustc.xyz.playermanager.files.playerdata;
import augustc.xyz.playermanager.serialisation.InventorySerialisation;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class PlayerProfile {

    public static HashMap<Player, PlayerProfile> onlinePlayers = new HashMap<>();

    public static void registerPlayer(Player player){
        onlinePlayers.put(player, new PlayerProfile(player));
    }

    public static PlayerProfile get(Player p){
        return onlinePlayers.get(p);
    }

    public static void unregisterPlayer(Player player){
        onlinePlayers.get(player).savePlayerData();
        onlinePlayers.remove(player);
    }

    public void setBackLocation(Location backLocation) {
        this.backLocation = backLocation;
    }

    public Location getBackLocation() {
        return backLocation;
    }

    Location backLocation;

    String displayname;

    public Integer getAfkTimer() {
        return afkTimer;
    }

    public void setAfkTimer(Integer afkTimer) {
        if(afkTimer == 0){
            setAfk(false);
        }
        if(afkTimer > PlayerManager.idleTimeout){
            player.kickPlayer("You have been kicked for being AFK for " + PlayerManager.idleTimeout/60 + " minutes.");
            for(Player p : Bukkit.getOnlinePlayers()){
                if(p.hasPermission("player.notify.afkkick")){
                    p.sendMessage(player.getDisplayName() + ChatColor.LIGHT_PURPLE + " was kicked for being AFK for " + PlayerManager.idleTimeout/60 + " minutes.");
                }
            }
        }
        this.afkTimer = afkTimer;
    }

    Integer afkTimer;

    public Boolean isAfk() {
        return afk;
    }

    public void setAfk(Boolean afk) {
        if(this.afk != afk){ //status changed
            if(afk){
                PlayerManager.getPlugin().getServer().broadcastMessage(ChatColor.GRAY + "> " + player.getDisplayName() + ChatColor.GRAY + " is now AFK.");
            }else{
                PlayerManager.getPlugin().getServer().broadcastMessage(ChatColor.GRAY + "> " + player.getDisplayName() + ChatColor.GRAY + " is no longer AFK.");
            }
        }
        if(!afk){
            afkTimer = 0;
        }
        this.afk = afk;
    }

    Boolean afk;

    Player player;

    public Inventory getBackpack() {
        return backpack;
    }

    public void setBackpack(Inventory backpack) {
        this.backpack = backpack;
    }

    Inventory backpack;

    public Boolean hasBackpackOpen() {
        return backpackOpen;
    }

    public void setBackpackOpen(Boolean backpackOpen) {
        this.backpackOpen = backpackOpen;
    }

    Boolean backpackOpen;

    public Integer getHomesAllowed() {
        return homesAllowed;
    }

    public void setHomesAllowed(Integer homesAllowed) {
        this.homesAllowed = homesAllowed;
    }

    Integer homesAllowed;

    public HashMap<String, Location> getHomes() {
        return homes;
    }

    public Location getHome(String name){
        return homes.get(name);
    }

    public void delhome(String name){
        homes.remove(name);
    }

    public void sethome(String name, Location location){
        homes.put(name, location);
    }

    HashMap<String, Location> homes;

    public Location getLastLocation() {
        return lastLocation;
    }

    public void setLastLocation(Location lastLocation) {
        this.lastLocation = lastLocation;
    }

    Location lastLocation;

    public String getFirstJoined() {
        return firstJoined;
    }

    public void setFirstJoined(String firstJoined) {
        this.firstJoined = firstJoined;
    }

    String firstJoined;

    public Player getLastMessaged() {
        return lastMessaged;
    }

    public void setLastMessaged(Player lastMessaged) {
        this.lastMessaged = lastMessaged;
    }

    Player lastMessaged;

    public Player getPlayer() {
        return player;
    }

    public PlayerProfile(Player player) {
        this.afkTimer = 0;
        this.afk = false;
        this.backpackOpen = false;
        this.player = player;
        this.backpack = null;
        this.homes = new HashMap<>();
        this.displayname = player.getDisplayName();
        this.lastLocation = player.getLocation();
        this.firstJoined = "Unknown";
        this.lastMessaged = null;
        loadPlayerData();
        player.setDisplayName(displayname);
    }

    public void savePlayerData(){

        FileConfiguration data = playerdata.getPlayerData(player);

        if(backpack != null){
            data.set("backpack.inventory", InventorySerialisation.serialise(backpack));
            data.set("backpack.size", backpack.getSize());
        }
        data.set("backLocation", backLocation);
        data.set("displayname", player.getDisplayName());
        for(String home : homes.keySet()){
            data.set("homes." + home, homes.get(home));
        }
        data.set("firstjoined", firstJoined);

        playerdata.save(player, data);

    }

    public void loadPlayerData(){

        FileConfiguration data = playerdata.getPlayerData(player);

        //displayname:
        if(data.contains("displayname")){
            if(data.getString("displayname") != null){
                displayname = data.getString("displayname");
            }
        }

        //load backlocation:
        backLocation = player.getLocation();

        if(data.contains("backLocation")){
            if(data.getLocation("backLocation") != null){
                backLocation = data.getLocation("backLocation");
            }
        }else{
            data.set("backLocation", player.getLocation());
            playerdata.save(player, data);
        }

        //backpack
        if(data.contains("backpack")){
            if(data.getString("backpack.inventory") != null){
                backpack = InventorySerialisation.deserialise(data.getString("backpack.inventory"), player, "Backpack", data.getInt("backpack.size"));
            }
        }

        //homes allowed
        //TODO calculate homes by adding up ranks
        homesAllowed = 3;

        //homes
        if(data.contains("homes")){
            ConfigurationSection homes = data.getConfigurationSection("homes");
            for(String home : homes.getKeys(true)){
                this.homes.put(home, data.getLocation("homes." + home));
            }
        }

        //firstjoined
        if(data.contains("firstjoined")){
            if(data.getString("firstjoined") != null){
                firstJoined = data.getString("firstjoined");
            }
        }

    }

}
