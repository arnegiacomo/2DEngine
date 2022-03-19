package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.entities.Component;
import no.arnemunthekaas.engine.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Vector2f textureCoordinates;
    private Texture texture;

    /**
     * Creates new SpriteRenderer component with given color vector
     *
     * @param color Color vector
     */
    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.texture = null;
    }

    /**
     * Creates new SpriteRenderer component with given texture object
     * @param texture Texture object
     */
    public SpriteRenderer(Texture texture) {
        this.texture = texture;
        this.color = new Vector4f(1, 1, 1, 1);
    }

    @Override
    public void start() {

    }

    @Override
    public void update(float dt) {

    }

    /**
     * Get color vector
     *
     * @return Color vector
     */
    public Vector4f getColor() {
        return color;
    }

    /**
     * Get Texture
     * @return Texture
     */
    public Texture getTexture() {
        return texture;
    }

    /**
     * Get Texture coordinates
     * @return Vector with texture coordinates
     */
    public Vector2f[] getTextureCoordinates() {
        Vector2f[] texCoords = {
                new Vector2f(1, 1),
                new Vector2f(1, 0),
                new Vector2f(0, 0),
                new Vector2f(0, 1)
        };
        return texCoords;
    }
}
