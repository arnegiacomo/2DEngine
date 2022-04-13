package no.arnemunthekaas.engine.entities.physics2d.components;

public class CircleCollider extends Collider {
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
