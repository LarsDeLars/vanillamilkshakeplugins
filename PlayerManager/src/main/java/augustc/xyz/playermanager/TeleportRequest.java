package augustc.xyz.playermanager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeleportRequest {

    Player asker;
    Player asked;

    public TeleportRequest(Player asker, Player asked, Boolean tphere, Integer secondsUntilExpires) {
        this.asker = asker;
        this.asked = asked;
        this.tphere = tphere;
        this.secondsUntilExpires = secondsUntilExpires;
    }

    public void addTeleportRequests(TeleportRequest teleportRequest) {
        teleportRequests.add(teleportRequest);
    }

    public static List<TeleportRequest> teleportRequests = new ArrayList<>();

    public Player getAsker() {
        return asker;
    }

    public Player getAsked() {
        return asked;
    }

    public Boolean getTphere() {
        return tphere;
    }

    public Integer getSecondsUntilExpires() {
        return secondsUntilExpires;
    }

    public void setSecondsUntilExpires(Integer secondsUntilExpires) {
        this.secondsUntilExpires = secondsUntilExpires;
        if(secondsUntilExpires <= 0){
            teleportRequests.remove(this);
        }
    }

    Boolean tphere;
    Integer secondsUntilExpires;

}
