package net.mokona.edonis.isometric.capture.task;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.mokona.edonis.isometric.capture.FramebufferCapturer;
import net.mokona.edonis.isometric.capture.FramebufferWriter;

import java.nio.file.Path;

public class CaptureTask implements RenderTickTask{
    private final Path file;

    public CaptureTask(Path file) {
        this.file = file;
    }

    @Override
    public boolean onRenderTick(RenderTickEvent evt) throws Exception {
        FramebufferCapturer fbc = new FramebufferCapturer();
        FramebufferWriter fbw = new FramebufferWriter(file, fbc);
        fbw.write();
        return true;
    }
}