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
        QuestAPI.getInstance().getQuestManager().giveQuest(QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get(), 0);
        QuestAPI.getInstance().getQuestManager().setActiveQuest(QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get(), 0);
        QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get().generateQuestMenu();
        event.getTargetEntity().openInventory(QuestAPI.getInstance().getPlayerManager().findPlayerInfo(event.getTargetEntity()).get().getQuestMenu().getPage(0),
                Cause.builder().named(NamedCause.of("testing", this)).build());
    }

    @Listener
    public void onLeave(ClientConnectionEvent.Disconnect event){
        event.setMessageCancelled(true);
        QuestAPI.getInstance().getPlayerManager().unregister(event.getTargetEntity());
    }

}
