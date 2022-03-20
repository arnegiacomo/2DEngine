package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.renderer.Texture;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;

    /**
     * Creates new SpriteRenderer component with given color vector
     *
     * @param color Color vector
     */
    public SpriteRenderer(Vector4f color) {
        this.color = color;
        this.sprite = new Sprite(null);
    }

    /**
     * Create new SpriteRenderer with given sprite
     * @param sprite
     */
    public SpriteRenderer(Sprite sprite) {
        this.sprite = sprite;
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
        return sprite.getTexture();
    }

    /**
     * Get Texture coordinates
     * @return Vector with texture coordinates
     */
    public Vector2f[] getTextureCoordinates() {
        return sprite.getTexCoords();
    }
}
