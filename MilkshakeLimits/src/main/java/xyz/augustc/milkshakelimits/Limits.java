package xyz.augustc.milkshakelimits;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;

public class Limits {

    private FileConfiguration fileConfig;

    public Limits(FileConfiguration fileConfig) {
        this.fileConfig = fileConfig;
        ConfigurationSection limitSection = fileConfig.getConfigurationSection("Limits");

        if (limitSection != null) {
            for(String group : limitSection.getKeys(false)){

                this.groups.put(group, limitSection.getInt(group + ".limit"));

                List<String> blocks = limitSection.getStringList(group + ".blocks");
                for(String block : blocks){

                    Material mat = Material.getMaterial(block);
                    if(mat == null){
                        MilkshakeLimits.getPlugin().getLogger().severe("Unknown material found in limits.yml: " + block);
                    }else{
                        this.allBlocks.put(Material.getMaterial(block),group);
                    }

                }

            }
        }else{
            MilkshakeLimits.getPlugin().getLogger().severe("No section for 'Limits' found in limits.yml file.");
        }

    }

    public HashMap<Material, String> getAllBlocks() {
        return allBlocks;
    }

    public boolean hasLimit(Material block){
        return allBlocks.containsKey(block);
    }

    public String getGroup(Material block){return allBlocks.get(block);}

    private HashMap<Material, String> allBlocks = new HashMap<>();

    public HashMap<String, Integer> getGroups() {
        return groups;
    }

    public Integer getLimit(String group){return groups.get(group);}

    private HashMap<String, Integer> groups = new HashMap<>();

}
