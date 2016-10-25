package com.github.questapi.core.quests.conditions;

import com.github.questapi.QuestAPI;
import com.github.questapi.core.quests.Condition;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class TimeLimit extends Condition {

    private Long whenStarted;
    private int seconds;

    public TimeLimit(boolean reset, int seconds) {
        super(reset, Check.ON_TIMER_TICK);
        this.seconds = seconds;
    }

    @Override
    public boolean isValid() {
        if(whenStarted != null){
            if((System.currentTimeMillis() - whenStarted)/1000 >= seconds)
                return false;
            else return true;
        } else {
            whenStarted = System.currentTimeMillis();
            return true;
        }
    }

    @Override
    public void setAdditionalStartInfo() {

    }

    @Override
    public void reset() {
        getPlayer().setLocation(getStartLocation());
        whenStarted = null;
    }

    @Override
    public void displayWarningMessage() {
        if(shouldSendWarningMessage()) {
            setLastWarningMessage();
            QuestAPI.getInstance().getMessager().sendMessage(getPlayer(), Text.of(TextColors.RED, "Time limit expired! Checkpoint reloaded!"));
        }
    }
}
