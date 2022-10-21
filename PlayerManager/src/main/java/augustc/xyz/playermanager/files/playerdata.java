package augustc.xyz.playermanager.files;

import augustc.xyz.playermanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

public class playerdata {

    public static void setup(){
        File file;

        file = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerManager").getDataFolder(), "playerdata");

        if(!file.exists()){
            try {
                file.mkdir();
            } catch (Exception e){
                PlayerManager.getPlugin().getLogger().severe("Failed to generate playerdata folder.\n" + e.getMessage());
            }
        }

    }

    public static File getPlayerFile(Player player){

        File file;

        String uuid = player.getUniqueId().toString();
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerManager").getDataFolder().getAbsolutePath() + "/playerdata/", uuid + ".yml");

        if(!file.exists()){
            try{
                file.createNewFile();
            }catch (IOException e){
                PlayerManager.getPlugin().getLogger().severe("Failed to generate playerdata file: " + uuid + ".yml (" + player.getName() + ")\n" + e.getMessage());
            }
        }

        return file;
    }

    public static FileConfiguration getPlayerData(Player player){

        return YamlConfiguration.loadConfiguration(getPlayerFile(player));

    }

    public static void save(Player player, FileConfiguration config){

        try {
            config.save(getPlayerFile(player));
        } catch (IOException e) {
            PlayerManager.getPlugin().getLogger().severe("Failed to save playerdata file: " + player.getUniqueId().toString() + ".yml (" + player.getName() + ")\n" + e.getMessage());
        }
    }

}
