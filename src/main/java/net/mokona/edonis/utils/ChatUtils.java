package net.mokona.edonis.utils;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.*;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class ChatUtils {
    private static final Minecraft MC = Minecraft.getInstance();
    private static final ChatComponent chat = MC.gui.getChat();

    public static void print(String msg) {
        chat.addMessage(new TextComponent(msg));
    }

    public static void printFileLink(File file) {
        TextComponent text = new TextComponent(file.getName());
        String path;

        try {
            path = file.getAbsoluteFile().getCanonicalPath();
        } catch (IOException e) {
            path = file.getAbsolutePath();
        }

        ClickEvent clickEvent = new ClickEvent( ClickEvent.Action.OPEN_FILE, path);
        Style style = Style.EMPTY.withClickEvent(clickEvent).withUnderlined(true);
        assert MC.player != null;
        MC.player.sendMessage(text.setStyle(style), MC.player.getUUID());
    }
}
