package no.arnemunthekaas.engine.renderer;

import org.joml.Vector2f;

public class Transform {

    public Vector2f position;
    public Vector2f scale;

    /**
     * Create new transform, with new position and scale vectors
     */
    public Transform() {
        this.position = new Vector2f();
        this.scale = new Vector2f();
    }

    /**
     * Create new transform, with given position vector
     * @param position position vector
     */
    public Transform(Vector2f position) {
        this.position = position;
        this.scale = new Vector2f();
    }

    /**
     * Create new transform with both position and scale vectors
     *
     * @param position position vector
     * @param scale scale vector
     */
    public Transform(Vector2f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }

}
