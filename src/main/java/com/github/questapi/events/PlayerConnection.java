package com.github.questapi.events;

import com.github.questapi.QuestAPI;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.network.ClientConnectionEvent;

public class PlayerConnection {

    @Listener
    public void onJoin(ClientConnectionEvent.Join event){
        event.setMessageCancelled(true);
        QuestAPI.getInstance().getPlayerManager().register(event.getTargetEntity());
        QuestAPI.getInstance().getQuestManager().giveQuest(QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get(), "test");
        QuestAPI.getInstance().getQuestManager().setActiveQuest(QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get(), "test");
        QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get().generateQuestMenu();
    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event){
        event.setMessageCancelled(true);
        QuestAPI.getInstance().getPlayerManager().unregister(event.getTargetEntity());
    }

}
