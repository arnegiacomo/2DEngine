package no.arnemunthekaas.physics2d.components;

import no.arnemunthekaas.engine.entities.components.Component;
import org.joml.Vector2f;

public class Box2DCollider extends Component {
    private Vector2f halfSize = new Vector2f(1);

    /**
     *
     * @return
     */
    public Vector2f getHalfSize() {
        return halfSize;
    }

    /**
     *
     * @param halfSize
     */
    public void setHalfSize(Vector2f halfSize) {
        this.halfSize = halfSize;
    }
}
