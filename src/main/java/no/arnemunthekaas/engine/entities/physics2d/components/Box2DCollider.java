package no.arnemunthekaas.engine.entities.physics2d.components;

import no.arnemunthekaas.engine.renderer.DebugDraw;
import org.joml.Vector2f;

public class Box2DCollider extends Collider {
    private Vector2f halfSize = new Vector2f(1);
    private Vector2f origin = new Vector2f();

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

    /**
     *
     * @return
     */
    public Vector2f getOrigin() {
        return origin;
    }

    @Override
    public void editorUpdate(float dt) {
        Vector2f center = new Vector2f(this.gameObject.transform.position.add(this.offset));
        DebugDraw.addBox2D(center, this.halfSize, this.gameObject.transform.rotation);
    }
}
