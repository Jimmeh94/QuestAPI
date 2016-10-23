package com.github.questapi.core.menus;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.player.PlayerInfo;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestMenu{

    private List<Inventory> menus = new ArrayList<>();
    private PlayerInfo owner;

    public QuestMenu(PlayerInfo owner){
        this.owner = owner;
        build();
    }

    private void build(){
        int pages = owner.getQuests().size() / 27;
        if(owner.getQuests().size() % 27 > 0)
            pages++;

        for(int i = 0; i < pages; i++){
            menus.add(Inventory.builder().
                    property("title", new InventoryTitle(Text.of("Quests: Page " + (i + 1))))
                    .property("inventorydimension", new InventoryDimension(9, 6))
                    .build(QuestAPI.getInstance()));

        }
    }

}
