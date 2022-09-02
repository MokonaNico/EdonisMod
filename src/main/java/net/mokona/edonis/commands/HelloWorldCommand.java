package net.mokona.edonis.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.commands.CommandSource;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerPlayer;
import net.mokona.edonis.utils.ChatUtils;

import java.io.File;


public class HelloWorldCommand {
    public HelloWorldCommand(CommandDispatcher<CommandSourceStack> dispatcher){
        dispatcher.register(Commands.literal("debug").then(Commands.literal("isometric").executes(
                (command) -> printConsole(command.getSource())
        )));
    }

    private int printConsole(CommandSourceStack source) throws CommandSyntaxException {

        return 1;
    }
}
