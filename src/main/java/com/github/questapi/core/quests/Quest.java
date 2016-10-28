package com.github.questapi.core.quests;

import com.github.questapi.QuestAPI;
import com.github.questapi.utilities.directional.PlayerDirection;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import com.github.questapi.core.player.PlayerInfo;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Quest {

    /*
     * General quest container.
     */

    private Text title, description;
    private Optional<PlayerInfo> owner = Optional.empty();
    private int recommendedLvl = 0;
    String id;
    private boolean active = false;
    private List<Checkpoint> checkpoints;
    private ItemStack itemRepresentation;
    private Reward reward;


    /*
     * All quests are loaded on server start up from a database and stored as "templates"
     * That's what this constructor is for
     */
    public Quest(String title, String description, int lvl, String id, List<Checkpoint> checkpoints, ItemType itemType, Reward reward){
        this.title = Text.of(TextColors.GOLD, title);
        this.description = Text.of(TextColors.GRAY, description);
        recommendedLvl = lvl;
        this.id = id;
        this.checkpoints = checkpoints;
        this.itemRepresentation = ItemStack.builder().itemType(itemType).build();
        itemRepresentation.offer(Keys.DISPLAY_NAME, getTitle());
        this.reward = reward;
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
        this.itemRepresentation = quest.getItemRepresentation().copy();
        setLore(false);
        for(Checkpoint c: checkpoints){
            c.setPlayer(owner.get().getPlayer());
        }
        this.reward = quest.getReward();
    }

    public void setLore(boolean active){
        List<Text> temp = new ArrayList<>();
        temp.add(Text.of(description));
        if(active){
            temp.add(0, Text.of(" "));
            temp.add(1, Text.of(TextColors.GREEN, TextStyles.BOLD, "Active"));
            temp.add(2, Text.of(" "));
        }
        itemRepresentation.offer(Keys.ITEM_LORE, temp);
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
                if(checkpoints.size() > 1)
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
                QuestAPI.getInstance().getMessager().sendActionBarMessage(owner.get().getPlayer(), Text.builder().append(Text.of(TextColors.GOLD, String.valueOf(distance) + " ")).append(
                        PlayerDirection.getDesiredDirection(owner.get().getPlayer(), checkpoints.get(0).getTargetLocation().get())).build());
            }
        }
        return false;
    }

    public Reward getReward() {
        return reward;
    }

    private void completeQuest(){
        QuestAPI.getInstance().getMessager().sendTitleAndSubTitle(owner.get().getPlayer(), Text.of(TextColors.GOLD, getTitle()), Text.of(TextColors.GREEN, "Completed"));
        if(reward != null)
            reward.giveAward(owner.get().getPlayer());
    }

    public String getID(){return id;}

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

    public void toggleActive() {
        if(active){
            active = false;
        } else {
            active = true;
            checkpoints.get(0).start();
        }
        setLore(true);
    }

    public List<Checkpoint> getCheckpoints() {
        return checkpoints;
    }

    public ItemStack getItemRepresentation() {
        return itemRepresentation;
    }
}
