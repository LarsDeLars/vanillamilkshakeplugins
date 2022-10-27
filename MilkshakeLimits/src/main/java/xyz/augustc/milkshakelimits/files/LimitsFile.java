package xyz.augustc.milkshakelimits.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import xyz.augustc.milkshakelimits.MilkshakeLimits;

import java.io.File;
import java.io.IOException;

public class LimitsFile {


    private static File file;
    private static FileConfiguration fileConfig;

    public static void setup(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MilkshakeLimits").getDataFolder(), "limits.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                MilkshakeLimits.getPlugin().getServer().getLogger().severe("Failed to generate limits.yml\n" + e.getMessage());
            }
        }

        fileConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration getLimitsData(){
        return fileConfig;
    }

    public static void save(){
        try {
            fileConfig.save(file);
        } catch (IOException e) {
            MilkshakeLimits.getPlugin().getServer().getLogger().severe("Failed to save limits.yml\n" + e.getMessage());
        }
    }

    public static void reload(){
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("MilkshakeLimits").getDataFolder(), "limits.yml");
        fileConfig = YamlConfiguration.loadConfiguration(file);
    }
}
