package net.mokona.edonis.isometric;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.mokona.edonis.isometric.capture.task.CaptureTask;
import net.mokona.edonis.isometric.capture.task.RenderTickTask;
import net.mokona.edonis.utils.ChatUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_F9;

public class ScreenshotHandler {

    private static final Minecraft MC = Minecraft.getInstance();
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");

    private Path taskFile;
    private RenderTickTask task;

    public ScreenshotHandler(){
        ClientRegistry.registerKeyBinding(null); // TODO !!!!
    }

    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event){
        if (task != null) return;

        if (event.getKey() == GLFW_KEY_F9){
            taskFile = getScreenshotFile();
            task = new CaptureTask(taskFile);
        }
    }

    @SubscribeEvent
    public void onRenderTick(TickEvent.RenderTickEvent evt) {
        if (task == null) {
            return;
        }

        try {
            if (task.onRenderTick(evt)) {
                task = null;
                ChatUtils.printFileLink(taskFile.toFile());
            }
        } catch (Exception ex) {
            ChatUtils.print(ex.getMessage());
            task = null;
        }
    }

    private Path getScreenshotFile() {
        Path dir = MC.gameDirectory.toPath().resolve("screenshots");

        try {
            if (!Files.exists(dir)) {
                Files.createDirectories(dir);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // loop though suffixes while the file exists
        int i = 0;
        Path file;
        do {
            file = dir.resolve(String.format("huge_%s_%04d.tga",
                    DATE_FORMAT.format(new Date()), i++));
        } while (Files.exists(file));

        return file;
    }


}
