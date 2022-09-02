package net.mokona.edonis.isometric.capture;

import net.minecraft.client.Minecraft;

import java.awt.*;
import java.nio.ByteBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.GL_BGR;

public class FramebufferCapturer {

    private static final int BPP = 3; // bytes per pixel
    private static final int TYPE = GL_UNSIGNED_BYTE;
    private static final Minecraft MC = Minecraft.getInstance();

    private final ByteBuffer bb;
    private final Dimension dim;
    private final byte[] line1;
    private final byte[] line2;
    private boolean flipColors = false;
    private boolean flipLines = false;

    public FramebufferCapturer() {
        dim = getCurrentDimension();
        bb = ByteBuffer.allocateDirect((int) (dim.getWidth() * dim.getHeight() * BPP));
        assert MC.screen != null;
        line1 = new byte[MC.screen.width * BPP];
        line2 = new byte[MC.screen.width * BPP];
    }

    public void setFlipColors(boolean flipColors) {
        this.flipColors = flipColors;
    }

    public boolean isFlipColors() {
        return flipColors;
    }

    public void setFlipLines(boolean flipLines) {
        this.flipLines = flipLines;
    }

    public boolean isFlipLines() {
        return flipLines;
    }

    public int getBytesPerPixel() {
        return BPP;
    }

    public ByteBuffer getByteBuffer() {
        bb.rewind();
        return bb.duplicate();
    }

    public Dimension getCaptureDimension() {
        return dim;
    }

    private Dimension getCurrentDimension() {
        assert MC.screen != null;
        return new Dimension(MC.screen.width, MC.screen.height);
    }

    public void capture() {
        // check if the dimensions are still the same
        Dimension dim1 = getCurrentDimension();
        Dimension dim2 = getCaptureDimension();
        if (!dim1.equals(dim2)) {
            throw new IllegalStateException(String.format(
                    "Display size changed! %fx%f != %fx%f",
                    dim1.getWidth(), dim1.getHeight(),
                    dim2.getWidth(), dim2.getHeight()));
        }

        // set alignment flags
        glPixelStorei(GL_PACK_ALIGNMENT, 1);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);

        int format = flipColors ? GL_BGR : GL_RGB;
        assert MC.screen != null;
        // read texture from framebuffer if enabled, otherwise use slower glReadPixels
        glReadPixels(0, 0, MC.screen.width, MC.screen.height, format, TYPE, bb);

        if (!flipLines) {
            return;
        }

        // flip buffer vertically
        for (int i = 0; i < MC.screen.height / 2; i++) {
            int ofs1 = i * MC.screen.width * BPP;
            int ofs2 = (MC.screen.height - i - 1) * MC.screen.width * BPP;

            // read lines
            bb.position(ofs1);
            bb.get(line1);
            bb.position(ofs2);
            bb.get(line2);

            // write lines at swapped positions
            bb.position(ofs2);
            bb.put(line1);
            bb.position(ofs1);
            bb.put(line2);
        }
    }
}
