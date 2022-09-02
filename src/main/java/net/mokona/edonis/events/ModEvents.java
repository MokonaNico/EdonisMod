package net.mokona.edonis.events;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.command.ConfigCommand;
import net.mokona.edonis.EdonisMod;
import net.mokona.edonis.commands.HelloWorldCommand;

@Mod.EventBusSubscriber(modid = EdonisMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event){
        new HelloWorldCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
    }
}
