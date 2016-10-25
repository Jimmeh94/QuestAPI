package com.github.questapi.core.quests.test;

import com.github.questapi.core.quests.Reward;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;

public class TestReward implements Reward {
    @Override
    public Text getDescription() {
        return Text.of("Hammer of Doom");
    }

    @Override
    public void giveAward(Player player) {
        player.getInventory().offer(ItemStack.of(ItemTypes.ACACIA_DOOR, 1));
    }
}
