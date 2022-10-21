package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MainChatEvent implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){

        Player p = e.getPlayer();
        PlayerProfile.get(e.getPlayer()).setAfk(false);

        //use chat colours
        if(p.hasPermission("server.chatcolors")){
            e.setMessage(translateHexColorCodes(e.getMessage()));
        }

        //set format to: <PREFIX> <DISPLAYNAME>: <MESSAGE>
        e.setFormat(translateHexColorCodes(PlayerManager.getPlugin().getChat().getPlayerPrefix(p)) + " " + p.getDisplayName() + ChatColor.GRAY + ": " + ChatColor.RESET + e.getMessage());


    }

    public String translateHexColorCodes(String msg) {

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

}
