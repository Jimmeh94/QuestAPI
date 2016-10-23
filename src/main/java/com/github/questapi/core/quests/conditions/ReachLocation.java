package com.github.questapi.core.quests.conditions;

import com.github.questapi.core.quests.Condition;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

public class ReachLocation extends Condition {

    /*
     * Simply a condition to where a player must reach a location
     */

    private Location targetLocation;
    private double completionRadius = 1.5;

    public ReachLocation(boolean reset, Location location, double radius) {
        super(reset, Check.ON_TIMER_TICK);
        targetLocation = location;
        completionRadius = radius;
    }

    @Override
    public boolean isValid() {
        if(getPlayer().getLocation().getPosition().distance(targetLocation.getPosition()) <= completionRadius)
            return true;
        return false;
    }

    @Override
    public void displayWarningMessage() {

    }
}
