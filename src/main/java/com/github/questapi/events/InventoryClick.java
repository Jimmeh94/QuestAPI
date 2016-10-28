package com.github.questapi.events;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.player.PlayerInfo;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.NamedCause;
import org.spongepowered.api.event.filter.cause.First;
import org.spongepowered.api.event.item.inventory.ClickInventoryEvent;

public class InventoryClick {

    /*
     * Current problems are clicking on the item multiple times. It works the first time, but the second+
     * says there's no value present. Also, closing the inventory doesn't work. need to wait for Sponge
     * to update
     */

    @Listener
    public void onClick(ClickInventoryEvent.Primary event, @First Player player){
        if(event.getTargetInventory().getName().get().contains("Quests")){
            event.setCancelled(true);
            PlayerInfo playerInfo = QuestAPI.getInstance().getPlayerManager().findPlayerInfo(player).get();

            QuestAPI.getInstance().getQuestManager().setActiveQuest(playerInfo,
                    playerInfo.getQuestMenu()
                            .findClickedQuest(event.getCursorTransaction().getFinal()
                                    .createStack().get(Keys.DISPLAY_NAME).get()));

            player.closeInventory(Cause.of(NamedCause.of("Server Action", this)));
        }
    }

}
