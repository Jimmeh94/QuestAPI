package com.github.questapi.core.player;

import com.github.questapi.QuestAPI;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import com.github.questapi.core.quests.Quest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerInfo {

    private UUID uuid;
    private List<Quest> quests = new ArrayList<>();

    public UUID getUuid() {
        return uuid;
    }

    public PlayerInfo(Player player){
        uuid = player.getUniqueId();
        //populate quests
    }

    public List<Quest> getQuests(){return quests;}

    public Player getPlayer() {
        if(Sponge.getServer().getPlayer(uuid).isPresent()){
            return Sponge.getServer().getPlayer(uuid).get();
        } else {
            QuestAPI.getInstance().getLogger().error("Player wasn't found online, yet the PlayerInfo has still been interacted with");
            return null;
        }
    }
}
