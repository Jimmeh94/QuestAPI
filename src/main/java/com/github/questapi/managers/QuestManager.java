package com.github.questapi.managers;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.player.PlayerInfo;
import com.github.questapi.core.quests.Quest;
import com.github.questapi.core.quests.test.TestQuestLocation;
import org.spongepowered.api.Sponge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class QuestManager {

    /*
     * Handles all quest-giving actions
     */

    private List<Quest> quests = new ArrayList<>();

    public void loadQuests(){
        quests.add(TestQuestLocation.createLocationTest());
    }

    public void giveQuest(PlayerInfo playerInfo, String id){
        Quest quest = null;
        for(Quest q: quests){
            if(q.getID() == id)
                quest = q;
        }
        if(quest != null){
            playerInfo.getQuests().add(new Quest(quest, playerInfo));
        }
    }

    public void setActiveQuest(PlayerInfo playerInfo, String id){
        Quest quest = null;
        for(Quest q: playerInfo.getQuests()){
            if(q.getID() == id)
                quest = q;
        }
        if(quest != null && !quest.isActive()){
            Optional<Quest> temp = getActiveQuest(playerInfo);
            if(temp.isPresent()){
                temp.get().setLore(false);
                temp.get().toggleActive();
            }
            quest.toggleActive();
        }
    }

    public void setActiveQuest(PlayerInfo playerInfo, Quest quest){
        if(!playerInfo.getQuests().contains(quest))
            playerInfo.getQuests().add(quest);
        if(quest != null && !quest.isActive()){
            quest.toggleActive();
        }
    }

    public Optional<Quest> getActiveQuest(PlayerInfo playerInfo){
        Optional<Quest> quest = Optional.empty();
        for(Quest q: playerInfo.getQuests()){
            if(q.isActive())
                quest = Optional.of(q);
        }
        return quest;
    }

    public void tick(){
        Optional<Quest> quest;
        for(PlayerInfo playerInfo: QuestAPI.getInstance().getPlayerManager().getPlayers()){
            quest = getActiveQuest(playerInfo);
            if(quest.isPresent()){
                if(quest.get().tick())
                    removeQuest(playerInfo, quest.get());
            }
        }
    }

    public void removeQuest(PlayerInfo playerInfo, Quest quest){
        playerInfo.getQuests().remove(quest);
    }

    public void removeQuest(PlayerInfo playerInfo, String id){
        Quest remove = null;
        for(Quest quest: playerInfo.getQuests()){
            if(quest.getID() == id)
                remove = quest;
        }
        if(remove != null){
            playerInfo.getQuests().remove(remove);
        }
    }

}
