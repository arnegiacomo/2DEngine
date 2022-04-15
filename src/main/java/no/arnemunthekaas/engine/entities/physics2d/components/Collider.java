package no.arnemunthekaas.engine.entities.physics2d.components;

import no.arnemunthekaas.engine.entities.components.Component;
import org.joml.Vector2f;

public abstract class Collider extends Component {
    protected Vector2f offset = new Vector2f();

    /**
     *
     * @return
     */
    public Vector2f getOffset() {
        return offset;
    }
}
