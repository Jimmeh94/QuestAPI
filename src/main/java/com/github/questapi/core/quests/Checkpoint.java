package com.github.questapi.core.quests;

import com.github.questapi.QuestAPI;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Checkpoint {

    private Player player;
    private Optional<Location> targetLocation = Optional.empty();
    private Optional<String> description = Optional.empty();
    private List<Condition> conditions;

    /*
     * Reset progress used for when a certain condition is invalidated. For example, we could use this as a
     * tutorial area with a BoundRadius condition. If the player leaves the tutorial area, they would
     * be teleported back and the checkpoint would restart
     */
    private void resetProgress(){
        for(Condition condition: conditions){
            condition.reset();
        }
    }

    public Checkpoint(Location location, String description, Condition... conditions){
        if(location != null)
            targetLocation = Optional.of(location);
        if(description != null)
            this.description = Optional.of(description);
        if(conditions != null)
            this.conditions = Arrays.asList(conditions);
    }

    /*
     * Just called whenever the checkpoint is initiated/reset. Gives them an idea of what to do
     */
    public void start(){
        if(getDescription().isPresent())
            QuestAPI.getInstance().getMessager().sendMessage(player, "Quest updated: " + getDescription().get(), TextColors.YELLOW);
        for(Condition condition: conditions){
            condition.setStartingInfo();
        }
    }

    /*
     * Called whenever all conditions are met and the checkpoint is completed
     */
    public void printCompletionMsg() {
        QuestAPI.getInstance().getMessager().sendMessage(player, "Checkpoint reached!", TextColors.GREEN);
    }

    /*
     * Quest timer will check this function, making sure all conditions and inherited class checks are complete
     */
    public boolean isComplete(Condition.Check check){
        if(hasConditionWithCheck(check) && conditionsMet()){
                return true;
        }
        return false;
    }

    /*
     * Making sure that this checkpoint has a condition with the certain Check type. So for instance,
     * a condition that is suppose to be checked before the player's ability is cast isn't checked on the normal
     * timer check.
     */
    private boolean hasConditionWithCheck(Condition.Check check){
        for(Condition condition: conditions){
            if(condition.getCheck() == check) {
                return true;
            }
        }
        return false;
    }

    /*
     * Makes sure all checkpoint conditions are validated before completing
     */
    private boolean conditionsMet(){
        boolean valid = true;
        for(Condition condition: conditions){
            if(!condition.isValid()){
                condition.displayWarningMessage();
                if(condition.shouldResetProgress()){
                    resetProgress();
                }
                valid = false;
            }
        }
        return valid;
    }

    public Optional<Location> getTargetLocation() {
        return targetLocation;
    }

    public Optional<String> getDescription() {
        return description;
    }

    public Player getPlayer() {

        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        for(Condition condition: conditions)
            condition.setPlayer(player);
    }
}
