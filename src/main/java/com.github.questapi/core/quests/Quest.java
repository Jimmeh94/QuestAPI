package com.github.questapi.core.quests;

import com.github.questapi.QuestAPI;
import com.github.questapi.utilities.directional.PlayerDirection;
import org.spongepowered.api.text.Text;
import com.github.questapi.core.player.PlayerInfo;
import org.spongepowered.api.text.format.TextColors;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Quest {

    /*
     * General quest container.
     * Has info: title, description, reward, active status
     * Parts: 1) opening dialogue, 2) quest checkpoints, 3) finishing dialogue
     * Quest timer: 1) checkpoint finished? Yes - load next checkpoint, if final, finish quest, 2) update location tracker
     * Quest Log/Journal: shows all owned (incomplete) quests, active quest first, click to make quest active
     */

    private Text title, description;
    private Optional<PlayerInfo> owner = Optional.empty();
    private int recommendedLvl = 0, id;
    private boolean active = false;
    private List<Checkpoint> checkpoints;

    /*
     * Possibly have all quests stored in a database and on server start up, load those quests
     * as generic, then once a player logs in, give them a "personalized" instance of the quests
     * they need using the copy constructor?
     */

    public Quest(String title, String description, int lvl, int id, List<Checkpoint> checkpoints){
        this.title = Text.of(title);
        this.description = Text.of(description);
        recommendedLvl = lvl;
        this.id = id;
        this.checkpoints = checkpoints;
    }

    /*
     * Use this constructor to give a quest to a player.
     * We set the player here due to how we have the quest load structure set up
     * Currently, quests are being loaded from a database, so once quests are loaded on server
     * initialization, we don't pass in any "personal" info. The quests are stored as "templates"
     * and we then pass personal info to these templates once the player needs a specific quest
     */
    public Quest(Quest quest, PlayerInfo playerInfo){
        title = quest.getTitle();
        description = quest.getDescription();
        owner = Optional.of(playerInfo);
        id = quest.getID();
        checkpoints = new ArrayList<>(quest.getCheckpoints());
        for(Checkpoint c: checkpoints){
            c.setPlayer(owner.get().getPlayer());
        }
    }

    /*
     * Function that makes things happen.
     * Check if checkpoint is reached
     * if so, is final checkpoint?
     * if so, give reward and finishing dialogue
     * if complete and not final, load next checkpoint and start()
     * Update quest tracker
     */
    public boolean tick(){
        if(checkpoints.size() > 0){
            if(checkpoints.get(0).isComplete(Condition.Check.ON_TIMER_TICK)){
                checkpoints.get(0).printCompletionMsg();
                checkpoints.remove(0);

                if(checkpoints.size() > 0){
                    checkpoints.get(0).start();
                } else {
                    completeQuest();
                    return true;
                }
            }
            //update tracker
            //get distance from player to target, get arrow direction, send message
            if(checkpoints.get(0).getTargetLocation().isPresent()) {
                int distance = (int) checkpoints.get(0).getTargetLocation().get().getPosition().distance(owner.get().getPlayer().getLocation().getPosition());
                QuestAPI.getInstance().getMessager().sendActionBarMessage(owner.get().getPlayer(), String.valueOf(distance) + " " +
                        PlayerDirection.getDesiredDirection(owner.get().getPlayer(), checkpoints.get(0).getTargetLocation().get()), TextColors.GOLD);
            }
        }
        return false;
    }

    private void completeQuest(){
        QuestAPI.getInstance().getMessager().sendTitleAndSubTitle(owner.get().getPlayer(), Text.of(TextColors.GOLD, getTitle()), Text.of(TextColors.GREEN, "Completed"));
    }

    public int getID(){return id;}

    public Text getTitle() {
        return title;
    }

    public Text getDescription() {
        return description;
    }

    public Optional<PlayerInfo> getOwner() {
        return owner;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        checkpoints.get(0).start();
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }
}
