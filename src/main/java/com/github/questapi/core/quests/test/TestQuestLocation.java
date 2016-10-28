package com.github.questapi.core.quests.test;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.quests.Quest;
import com.github.questapi.core.quests.builders.CheckpointBuilder;
import com.github.questapi.core.quests.builders.QuestBuilder;
import com.github.questapi.core.quests.conditions.BoundRadius;
import com.github.questapi.core.quests.conditions.ReachLocation;
import com.github.questapi.core.quests.conditions.TimeLimit;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.ArrayList;

public class TestQuestLocation {

    public static Quest createLocationTest(){
        Location use = new Location<World>((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 55, 55, 55);
        Location use2 = new Location((new ArrayList<>(Sponge.getServer().getWorlds()).get(0)), 51, 55, 51);
        QuestBuilder questBuilder = QuestAPI.getInstance().getQuestBuilder();
        CheckpointBuilder checkpointBuilder = questBuilder.getCheckpointBuilder();

        checkpointBuilder.description("Go to a location")
                .targetLocation(use)
                .condition(new ReachLocation(false, use, 1.5))
                .condition(new TimeLimit(true, 15))
                .buildCheckpoint();
        checkpointBuilder.description("Stay within quest region and reach target location")
                .targetLocation(use2)
                .condition(new BoundRadius(true, 10.0, use))
                .condition(new ReachLocation(false, use2, 1.5))
                .buildCheckpoint();

        Quest quest = questBuilder.name("Test").description("This is a test quest").level(1).setID("test").checkpoints().itemType(ItemTypes.PAPER).reward(new TestReward()).build();
        questBuilder.reset();
        return quest;
    }

}
