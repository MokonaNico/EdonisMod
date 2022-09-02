package net.mokona.edonis.isometric.capture.task;

import net.minecraftforge.event.TickEvent.RenderTickEvent;

public interface RenderTickTask {
    boolean onRenderTick(RenderTickEvent evt) throws Exception;

}
