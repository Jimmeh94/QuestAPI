package com.github.questapi.managers;

import org.spongepowered.api.entity.living.player.Player;
import com.github.questapi.core.player.PlayerInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlayerManager {
    
    private List<PlayerInfo> players = new ArrayList<>();

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    /*
     * Called when a player joins the server
     */
    public void register(Player player){
        if(!isRegistered(player))
            players.add(new PlayerInfo(player));
    }

    public boolean isRegistered(Player player) {
        for(PlayerInfo p: players){
            if(p.getUuid().equals(player.getUniqueId()))
                return true;
        }
        return false;
    }

    public Optional<PlayerInfo> findPlayerInfo(Player player){
        Optional<PlayerInfo> give = Optional.empty();
        for(PlayerInfo p: players){
            if(p.getUuid().equals(player.getUniqueId()))
                give = Optional.of(p);
        }
        return give;
    }

    /*
     * To be called when a player leaves the server
     */
    public void unregister(Player targetEntity) {
        Optional<PlayerInfo> temp = findPlayerInfo(targetEntity);
        if(temp.isPresent()){
            players.remove(temp.get());
        }
    }
}
