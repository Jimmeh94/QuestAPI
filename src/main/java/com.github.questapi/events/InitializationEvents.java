package com.github.questapi.events;

import com.github.questapi.QuestAPI;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

public class InitializationEvents {

    @Listener
    public void serverStarting(GameStartedServerEvent event){
        QuestAPI.getInstance().getQuestManager().loadQuests();
    }

}
