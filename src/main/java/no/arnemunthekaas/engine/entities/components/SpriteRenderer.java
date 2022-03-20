package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.renderer.Texture;
import no.arnemunthekaas.engine.renderer.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;
    private Sprite sprite;

    private Transform lastTransform;
    private boolean isDirty = false;

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
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(float dt) {
        if(!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
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

    /**
     * Change color vector
     * @param color
     */
    public void setColor(Vector4f color) {
        if(!this.color.equals(color)) {
            this.isDirty = true;
            color.set(color);
        }
    }

    /**
     * Change sprite
     * @param sprite
     */
    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    /**
     * Dirty flag, returns true if sprite has changed in any way
     * @return isDirty
     */
    public boolean isDirty() {
        return isDirty;
    }

    /**
     * Cleans the dirty flag.
     */
    public void setClean() {
        this.isDirty = false;
    }
}
