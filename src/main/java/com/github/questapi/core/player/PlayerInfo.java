package com.github.questapi.core.player;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.menus.QuestMenu;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.entity.living.player.Player;
import com.github.questapi.core.quests.Quest;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerInfo {

    /*
     * Basic player info file
     */

    private UUID uuid;
    private List<Quest> quests = new ArrayList<>();
    private int level = 0;
    private QuestMenu questMenu;

    public UUID getUuid() {
        return uuid;
    }

    public PlayerInfo(Player player){
        uuid = player.getUniqueId();
        //TODO populate quests
        //questMenu = new QuestMenu(this);
    }

    public void displayQuestMenu(){
        getPlayer().openInventory(questMenu.getPage(0), Cause.builder().named(NamedCause.of("Server Action", this)).build());
    }

    /*
     * For testing purposes
     */
    public void generateQuestMenu(){questMenu = new QuestMenu(this);}

    public QuestMenu getQuestMenu() {
        return questMenu;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level){this.level = level;}

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
