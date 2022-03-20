package no.arnemunthekaas.engine.renderer;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture {

    private String filepath;
    private int ID;
    private int width, height;

    /**
     * Create a new texture from specified filepath
     * @param filepath filepath of texture
     */
    public Texture(String filepath) {
        this.filepath = filepath;

        // Generate texture on GPU
        ID = glGenTextures();
        glBindTexture(GL_TEXTURE_2D, ID);

        // Set texture parameters
        // Repeat image in both directions
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);

        // WHen stretching image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        // When shrinking image, pixelate
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer channels = BufferUtils.createIntBuffer(1);
        stbi_set_flip_vertically_on_load(true);
        ByteBuffer image = stbi_load(filepath, width, height, channels, 0);

        if (image != null) {
            this.width = width.get(0);
            this.height = height.get(0);
            if ( channels.get(0) == 3)
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, width.get(0), height.get(0), 0, GL_RGB, GL_UNSIGNED_BYTE, image);
            else if (channels.get(0) == 4) {
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
                glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(0), height.get(0), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            } else
                assert false : "Error: (Texture) Unknown number of channels '" + channels.get(0) + "'";
        } else
            assert false : "Error: (Texture) Could not load image '" + filepath + "'";

        stbi_image_free(image);
    }

    /**
     * Bind texture
     */
    public void bind() {
        glBindTexture(GL_TEXTURE_2D, ID);
    }

    /**
     * Unbind texture
     */
    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Get texture height
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * Get texture width
     * @return
     */
    public int getHeight() {
        return height;
    }
}
