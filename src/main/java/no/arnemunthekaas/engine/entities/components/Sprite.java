package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.renderer.Texture;
import org.joml.Vector2f;

public class Sprite {

    private Texture texture;
    private Vector2f[] texCoords;

    /**
     * Create a sprite with a given texture
     *
     * @param texture
     */
    public Sprite(Texture texture) {
        this.texture = texture;

        Vector2f[] texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };

        this.texCoords = texCoords;
    }

    /**
     *  Create sprite with given texture and texture coordinates
     *
     * @param texture
     * @param texCoords
     */
    public Sprite(Texture texture, Vector2f[] texCoords) {
        this.texture = texture;
        this.texCoords = texCoords;
    }

    /**
     * Get sprite texture
     * @return
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Get texture coordinates
     * @return
     */
    public Vector2f[] getTexCoords() {
        return texCoords;
    }
}
