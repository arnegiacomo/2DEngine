package no.arnemunthekaas.physics2d.components;

import no.arnemunthekaas.engine.entities.components.Component;

public class CircleCollider extends Component {
    private float radius = 1.0f;

    /**
     *
     * @return
     */
    public float getRadius() {
        return radius;
    }

    /**
     *
     * @param radius
     */
    public void setRadius(float radius) {
        this.radius = radius;
    }
}
