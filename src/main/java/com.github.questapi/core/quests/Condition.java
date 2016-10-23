package com.github.questapi.core.quests;

import org.spongepowered.api.entity.living.player.Player;

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

    public abstract boolean isValid();

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

    public Check getCheck() {
        return checkWhen;
    }

    public boolean shouldResetProgress(){return resetCheckpointProgress;}

    public Player getPlayer() {
        return player;
    }
}
