package com.github.questapi.core.menus;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.player.PlayerInfo;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.inventory.Inventory;
import org.spongepowered.api.item.inventory.InventoryArchetype;
import org.spongepowered.api.item.inventory.InventoryArchetypes;
import org.spongepowered.api.item.inventory.InventoryProperty;
import org.spongepowered.api.item.inventory.property.InventoryDimension;
import org.spongepowered.api.item.inventory.property.InventoryTitle;
import org.spongepowered.api.item.inventory.property.SlotIndex;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class QuestMenu{

    /*
     * To be the quest menu that players open to view their currently owned quests
     */

    private List<Inventory> menus = new ArrayList<>();
    private PlayerInfo owner;

    public QuestMenu(PlayerInfo owner){
        this.owner = owner;
        build();
    }

    /*
     * Default building of quests menus
     * 27 quests can fit on each menu page
     */

    private void build(){
        int pages = owner.getQuests().size() / 27;
        if(owner.getQuests().size() % 27 > 0)
            pages++;

        int counter = 0;
        for(int i = 0; i < pages; i++){
            Inventory inv = Inventory.builder().
                    property("title", InventoryTitle.of(Text.of("Quests: Page " + (i + 1)))) //TODO this isn't working
                    .property("inventorydimension", new InventoryDimension(9, 6))
                    .build(QuestAPI.getInstance());
            for(int j = 0; j < 27 && counter < owner.getQuests().size(); j++, counter++){
                inv.set(owner.getQuests().get(counter).getItemRepresentation());
            }
            menus.add(inv);
        }
    }

    public Inventory getPage(int i) {
        return menus.get(i);
    }
}
