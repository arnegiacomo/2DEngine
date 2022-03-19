package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.entities.Component;
import org.joml.Vector4f;

public class SpriteRenderer extends Component {

    private Vector4f color;

    /**
     * Creates new SpriteRenderer component with given color vector
     *
     * @param color Color vector
     */
    public SpriteRenderer(Vector4f color) {
        this.color = color;
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
}
