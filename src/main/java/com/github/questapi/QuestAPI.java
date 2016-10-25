package com.github.questapi;

import com.github.questapi.core.quests.builders.QuestBuilder;
import com.github.questapi.events.InitializationEvents;
import com.google.inject.Inject;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameInitializationEvent;
import org.spongepowered.api.event.game.state.GameStoppingServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import com.github.questapi.events.PlayerConnection;
import com.github.questapi.managers.PlayerManager;
import com.github.questapi.managers.QuestManager;
import com.github.questapi.runnables.CoreTimer;
import com.github.questapi.utilities.text.Messager;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

@Plugin(id = "questapi", name = "QuestAPI", version = "1.0.0")
public class QuestAPI {

    private static QuestAPI instance;

    private final PlayerManager playerManager = new PlayerManager();
    private final QuestManager questManager = new QuestManager();
    private final Messager messager = new Messager();
    private final QuestBuilder questBuilder = new QuestBuilder();

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameInitializationEvent event){
        instance = this;

        registerListeners();
        registerCommands();
        registerRunnables();

        getMessager().setPrefix(Text.builder().append(Text.of(TextColors.GRAY, "[")).append(Text.of(TextColors.GOLD, "Quest"))
                                .append(Text.of(TextColors.GRAY, "] ")).build());
    }

    private void registerRunnables() {
        Task.Builder taskBuilder = Sponge.getScheduler().createTaskBuilder();

        taskBuilder.execute(new CoreTimer()).intervalTicks(10L).submit(this);
    }

    private void registerCommands() {

    }

    private void registerListeners() {
        EventManager eventManager = Sponge.getEventManager();

        eventManager.registerListeners(this, new PlayerConnection());
        eventManager.registerListeners(this, new InitializationEvents());
    }

    @Listener
    public void onServerStopping(GameStoppingServerEvent event){
        //database writing and player manager stuff
    }

    public static QuestAPI getInstance() {
        return instance;
    }

    public Logger getLogger() {
        return logger;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public QuestManager getQuestManager() {
        return questManager;
    }

    public Messager getMessager() {
        return messager;
    }

    public QuestBuilder getQuestBuilder() {
        return questBuilder;
    }
}
