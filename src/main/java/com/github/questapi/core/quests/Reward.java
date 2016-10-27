package com.github.questapi.core.quests;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public interface Reward {

    /*
     * Quest rewards
     */

    Text getDescription();

    void giveAward(Player player);

}
