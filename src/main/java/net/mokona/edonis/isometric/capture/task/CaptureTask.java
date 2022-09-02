package net.mokona.edonis.isometric.capture.task;

import net.minecraft.client.Minecraft;
import net.minecraftforge.event.TickEvent.RenderTickEvent;
import net.mokona.edonis.isometric.capture.FramebufferCapturer;
import net.mokona.edonis.isometric.capture.FramebufferWriter;

import java.nio.file.Path;

public class CaptureTask implements RenderTickTask{
    private static final Minecraft MC = Minecraft.getInstance();

    private final Path file;

    private int frame;
    private int displayWidth;
    private int displayHeight;

    public CaptureTask(Path file) {
        this.file = file;
    }

    @Override
    public boolean onRenderTick(RenderTickEvent evt) throws Exception {
        assert MC.screen != null;
        switch (frame) {
            // override viewport size (the following frame will be black)
            case 0:
                displayWidth = MC.screen.width;
                displayHeight = MC.screen.height;

                int width = 1280;
                int height = 720;

                // resize viewport/framebuffer
                MC.screen.resize(MC,width, height);
                break;

            // capture screenshot and restore viewport size
            case 3:
                try {
                    FramebufferCapturer fbc = new FramebufferCapturer();
                    FramebufferWriter fbw = new FramebufferWriter(file, fbc);
                    fbw.write();
                } finally {
                    // restore viewport/framebuffer
                    MC.screen.resize(MC,displayWidth, displayHeight);
                }
                break;
        }

        frame++;
        return frame > 3;
    }
}