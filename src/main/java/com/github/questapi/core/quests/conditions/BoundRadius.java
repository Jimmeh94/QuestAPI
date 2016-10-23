package com.github.questapi.core.quests.conditions;

import com.github.questapi.core.quests.Condition;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

import java.util.Optional;

public class BoundRadius extends Condition {

    /*
     * Quest checkpoint condition in which the checkpoint can only be complete if
     * the desired entity or location is within the radius to the center location
     */

    private double radius;
    private Location center;
    private Optional<Location> manualTrackedLocation;

    public BoundRadius(boolean reset, double radius, Location center, Entity entity){
        super(reset, Check.ON_TIMER_TICK);
        this.radius = radius;
        this.center = center;
    }

    public BoundRadius(boolean reset, double radius, Location center, Location location){
        super(reset, Check.ON_TIMER_TICK);
        this.radius = radius;
        this.center = center;
        if(location != null)
            manualTrackedLocation = Optional.of(location);
    }

    /*
     * Can manually update this before the isValid() check if using a manually tracked location
     * For example, making sure an ability fired is within the radius, etc
     */
    public void updateManualLocation(Location location){manualTrackedLocation = Optional.of(location);}

    @Override
    public boolean isValid() {
        Location check;
        if(manualTrackedLocation.isPresent()){
            check = manualTrackedLocation.get();
        } else {
            check = getPlayer().getLocation();
        }

        if(check.getPosition().distance(center.getPosition()) <= radius)
            return true;
        return false;
    }

    @Override
    public void displayWarningMessage() {
        //TODO fill this out
    }
}
