package augustc.xyz.playermanager.events;

import augustc.xyz.playermanager.PlayerManager;
import augustc.xyz.playermanager.PlayerProfile;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.group.GroupManager;
import net.luckperms.api.model.user.User;
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

        //PlayerManager.getPlugin().getChat().getPlayerPrefix(p)

        User user = PlayerManager.getLuckPerms().getPlayerAdapter(Player.class).getUser(p);
        String prefix = user.getCachedData().getMetaData().getPrefix();

        if(prefix == null){
            //set format to: <DISPLAYNAME> » <MESSAGE>
            e.setFormat(p.getDisplayName() + ChatColor.GRAY + " » " + ChatColor.RESET + e.getMessage());
        }else{
            //set format to: <PREFIX> <DISPLAYNAME> » <MESSAGE>
            e.setFormat(translateHexColorCodes(prefix) + " " + p.getDisplayName() + ChatColor.GRAY + " » " + ChatColor.RESET + e.getMessage());
        }



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
