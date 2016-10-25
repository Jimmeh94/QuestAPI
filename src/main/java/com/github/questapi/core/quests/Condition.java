package com.github.questapi.core.quests;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.world.Location;

public abstract class Condition {

    /*
     * Conditions have to do with the environment in which the quest checkpoint must be completed in
     * The Check enum describes when the condition should be checked for validity.
     * These are essentially the goals of each checkpoint
     */

    public enum Check{
        BEFORE_ACTION_FIRE,
        AFTER_ACTION_FIRE,
        ON_TIMER_TICK
    }

    private boolean resetCheckpointProgress;
    private Check checkWhen;
    private Player player;
    private Long lastWarningMessage;
    private Location startLocation;

    public abstract boolean isValid();

    /*
     * In case there's any additional info the condition will need once it becomes active
     */
    public abstract void setAdditionalStartInfo();

    /*
     * Reset the current condition
     * If boolean is true, can/should teleport player back to where they started the checkpoint at
     */
    public abstract void reset();

    /*
     * Warning message should be sent to the player if they are being reset
     */
    public abstract void displayWarningMessage();

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Condition(boolean reset, Check check){
        resetCheckpointProgress = reset;
        checkWhen = check;

    }

    public void setStartingInfo() {
        startLocation = getPlayer().getLocation().copy();
    }

    protected Location getStartLocation(){return startLocation;}

    public Check getCheck() {
        return checkWhen;
    }

    public boolean shouldResetProgress(){return resetCheckpointProgress;}

    public Player getPlayer() {
        return player;
    }

    protected void setLastWarningMessage(){lastWarningMessage = System.currentTimeMillis();}

    /*
     * Will only send warning message every 10 seconds
     */
    protected boolean shouldSendWarningMessage(){
        if(lastWarningMessage == null)
            return true;
        return ((System.currentTimeMillis() - lastWarningMessage)/1000 >= 10);
    }
}
