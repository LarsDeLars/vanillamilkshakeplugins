package augustc.xyz.playermanager;

import augustc.xyz.playermanager.commands.*;
import augustc.xyz.playermanager.commands.gamemode.AdventureCommand;
import augustc.xyz.playermanager.commands.gamemode.CreativeCommand;
import augustc.xyz.playermanager.commands.gamemode.SpectatorCommand;
import augustc.xyz.playermanager.commands.gamemode.SurvivalCommand;
import augustc.xyz.playermanager.commands.homes.DelhomeCommand;
import augustc.xyz.playermanager.commands.homes.HomeCommand;
import augustc.xyz.playermanager.commands.homes.SethomeCommand;
import augustc.xyz.playermanager.commands.homes.ShowhomesCommand;
import augustc.xyz.playermanager.commands.message.MessageComand;
import augustc.xyz.playermanager.commands.message.ReplyCommand;
import augustc.xyz.playermanager.commands.misc.*;
import augustc.xyz.playermanager.commands.teleportation.*;
import augustc.xyz.playermanager.events.*;
import augustc.xyz.playermanager.files.playerdata;
import augustc.xyz.playermanager.files.serverdata;
import augustc.xyz.playermanager.tasks.IncreaseAfkTimer;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class PlayerManager extends JavaPlugin {

    private static PlayerManager plugin;

    public static PlayerManager getPlugin() {
        return plugin;
    }

    public static Permission getPerms() {
        return perms;
    }

    private static Permission perms = null;

    public Chat getChat() {
        return chat;
    }

    private Chat chat = null;

    private boolean setupChat() {
        getServer().getServicesManager().register(Chat.class, chat, this, ServicePriority.Highest);
        //RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        //chat = rsp.getProvider();
        chat = getServer().getServicesManager().load(Chat.class);
        return chat != null;
    }

    public static HashMap<Player, PlayerProfile> players = new HashMap<>();

    public static Integer idleTimeout;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        setupPermissions();

        //vault chat
        setupChat();

        //setup config file
        getConfig().addDefault("idleTimeout", 180);
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //retrieving data from default config file
        idleTimeout = getConfig().getInt("idleTimeout") * 60; //converts from minute to seconds

        //setup playerdata file
        playerdata.setup();

        //setup serverdata file
        serverdata.setup();
        serverdata.getServerData().addDefault("spawn.default", getServer().getWorlds().get(0).getSpawnLocation());
        serverdata.getServerData().options().copyDefaults(true);
        serverdata.save();

        //load online players playerprofile
        for(Player player : Bukkit.getOnlinePlayers()){
//            PlayerProfile playerProfile = new PlayerProfile(player);
//            PlayerManager.players.put(player, playerProfile);
            PlayerProfile.registerPlayer(player);
        }

        //events
        getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        getServer().getPluginManager().registerEvents(new QuitEvent(), this);
        getServer().getPluginManager().registerEvents(new MainChatEvent(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathEvent(), this);
        getServer().getPluginManager().registerEvents(new TeleportEvent(), this);
        getServer().getPluginManager().registerEvents(new CloseInventory(), this);
        getServer().getPluginManager().registerEvents(new InteractEvent(), this);

        //commands
        getCommand("nickname").setExecutor(new NicknameCommand());
        getCommand("namecolour").setExecutor(new NameColourCommand());
        getCommand("namecolour").setTabCompleter(new TabCompleter());
        getCommand("addrank").setExecutor(new RankupCommand());
        getCommand("addrank").setTabCompleter(new TabCompleter());
        getCommand("back").setExecutor(new BackCommand());
        getCommand("backpack").setExecutor(new BackpackCommand());
        getCommand("afk").setExecutor(new AfkCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("flycheck").setExecutor(new FlyCommand());
        getCommand("sethome").setExecutor(new SethomeCommand());
        getCommand("delhome").setExecutor(new DelhomeCommand());
        getCommand("delhome").setTabCompleter(new TabCompleter());
        getCommand("home").setExecutor(new HomeCommand());
        getCommand("home").setTabCompleter(new TabCompleter());
        getCommand("homes").setExecutor(new ShowhomesCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("setspawn").setTabCompleter(new TabCompleter());
        getCommand("delspawn").setExecutor(new DeleteSpawnCommand());
        getCommand("delspawn").setTabCompleter(new TabCompleter());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("tpask").setExecutor(new TpAsk());
        getCommand("tpaskhere").setExecutor(new TpAskHere());
        getCommand("tpcancel").setExecutor(new TpCancel());
        getCommand("tpaccept").setExecutor(new TpAccept());
        getCommand("tpdeny").setExecutor(new TpDeny());
        getCommand("tp").setExecutor(new ForceTpCommand());
        getCommand("tphere").setExecutor(new ForceTpHereCommand());
        getCommand("firstjoined").setExecutor(new FirstjoinedCommand());
        getCommand("msg").setExecutor(new MessageComand());
        getCommand("reply").setExecutor(new ReplyCommand());
        getCommand("me").setExecutor(new MeCommand());
        getCommand("suicide").setExecutor(new SuicideCommand());
        getCommand("survival"). setExecutor(new SurvivalCommand());
        getCommand("creative").setExecutor(new CreativeCommand());
        getCommand("spectator").setExecutor(new SpectatorCommand());
        getCommand("adventure").setExecutor(new AdventureCommand());

        //tasks
        IncreaseAfkTimer afkTimerTask = new IncreaseAfkTimer();
        afkTimerTask.runTaskTimer(this, 20l, 20l);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        for(PlayerProfile p : PlayerProfile.onlinePlayers.values()){
//            players.get(p).savePlayerData();
            p.savePlayerData();
        }

    }

    public static String translateHexColorCodes(String msg) {

        String newMsg = "";

        for(int i = 0; i < msg.length(); i++){

            if(msg.charAt(i) == '&'){
                if(msg.length() > i + 7){
                    try{
                        newMsg += net.md_5.bungee.api.ChatColor.of(msg.substring(i + 1, i + 8));
                        i += 7;
                    } catch (Exception e){
                        newMsg += msg.charAt(i);
                    }
                }else{
                    newMsg += msg.charAt(i);
                }
            }else{
                newMsg += msg.charAt(i);
            }

        }

        String withBukkitChatColors = ChatColor.translateAlternateColorCodes('&', newMsg); //replace bukkit color codes with chatcolor

        return withBukkitChatColors;

    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

}
