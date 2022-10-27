package xyz.augustc.milkshakelimits;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.augustc.milkshakelimits.commands.ReloadCommand;
import xyz.augustc.milkshakelimits.commands.TabCompleter;
import xyz.augustc.milkshakelimits.events.BreakBlockEvent;
import xyz.augustc.milkshakelimits.events.PlaceBlockEvent;
import xyz.augustc.milkshakelimits.files.LimitsFile;
import xyz.augustc.milkshakelimits.tasks.ExpireChunks;

import java.util.ArrayList;
import java.util.List;

public final class MilkshakeLimits extends JavaPlugin {

    public static MilkshakeLimits getPlugin() {
        return plugin;
    }

    private static MilkshakeLimits plugin;

    public static Limits getLimits() {
        return limits;
    }

    private static Limits limits;

    private ArrayList<String> defaultGroupMembers = new ArrayList<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;

        //default config
        getConfig().options().copyDefaults();
        getConfig().addDefault("DeleteChunksFromMemoryAfter", 300);
        saveDefaultConfig();

        //limits file
        defaultGroupMembers.add("PISTON");
        defaultGroupMembers.add("STICKY_PISTON");
        LimitsFile.setup();
        LimitsFile.getLimitsData().addDefault("Limits.pistons.limit", 64);
        LimitsFile.getLimitsData().addDefault("Limits.pistons.blocks", defaultGroupMembers);
        LimitsFile.getLimitsData().options().copyDefaults(true);
        LimitsFile.save();

        //get limits
        limits = new Limits(LimitsFile.getLimitsData());

        //events
        getServer().getPluginManager().registerEvents(new BreakBlockEvent(), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockEvent(), this);

        //commands
        getCommand("limits").setExecutor(new ReloadCommand());
        getCommand("limits").setTabCompleter(new TabCompleter());

        //tasks
        ExpireChunks expireChunks = new ExpireChunks();
        expireChunks.runTaskTimer(this, 20l, 20l);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic


    }
}
