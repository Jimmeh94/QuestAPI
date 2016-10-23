package com.github.questapi.runnables;

import com.github.questapi.QuestAPI;

import java.util.Optional;

public class CoreTimer implements Runnable {
    @Override
    public void run() {
        QuestAPI.getInstance().getQuestManager().tick();
    }
}
