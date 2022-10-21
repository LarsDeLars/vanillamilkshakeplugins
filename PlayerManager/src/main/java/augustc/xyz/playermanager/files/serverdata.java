package augustc.xyz.playermanager.files;

import augustc.xyz.playermanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class serverdata {

    private static File file;
    private static FileConfiguration fileConfig;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerManager").getDataFolder(), "serverdata.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                PlayerManager.getPlugin().getServer().getLogger().severe("Failed to generate serverdata.yml\n" + e.getMessage());
            }
        }

        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getServerData(){
        return fileConfig;
    }

    public static void save(){
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            PlayerManager.getPlugin().getServer().getLogger().severe("Failed to save serverdata.yml\n" + e.getMessage());
        }
    }

    public static void reload(){
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

}
