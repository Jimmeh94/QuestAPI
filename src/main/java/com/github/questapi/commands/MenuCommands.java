package com.github.questapi.commands;

import com.github.questapi.QuestAPI;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

public class MenuCommands implements CommandExecutor {

    public MenuCommands(){
        CommandSpec commandSpec = CommandSpec.builder()
                .description(Text.of("Access your quest menu"))
                .executor(this)
                .build();
        Sponge.getCommandManager().register(QuestAPI.getInstance(), commandSpec, "quests", "questmenu", "qm");
    }

    @Override
    public CommandResult execute(CommandSource commandSource, CommandContext commandContext) throws CommandException {
        if(commandSource instanceof Player){
            Player player = (Player)commandSource;
            QuestAPI.getInstance().getPlayerManager().findPlayerInfo(player).get().displayQuestMenu();
        }
        return CommandResult.success();
    }
}
