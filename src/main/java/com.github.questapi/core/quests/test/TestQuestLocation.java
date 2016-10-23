package com.github.questapi.core.quests.test;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.quests.Quest;
import com.github.questapi.core.quests.builders.QuestBuilder;
import com.github.questapi.core.quests.conditions.ReachLocation;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;

public class TestQuestLocation {

    public static Quest createLocationTest(){
        Location use = new Location<World>((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 55, 55, 55);
        QuestBuilder questBuilder = QuestAPI.getInstance().getQuestBuilder();
        questBuilder.getCheckpointBuilder().description("Go to a location")
                .targetLocation(use)
                .condition(new ReachLocation(false, use, 1.5))
                .buildCheckpoint();
        Quest quest = questBuilder.name("Test").description("This is a test quest").level(1).checkpoints().itemType(ItemTypes.WRITTEN_BOOK).build();
        questBuilder.reset();
        return quest;
    }

}
