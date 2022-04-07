package no.arnemunthekaas.physics2d.components;

import no.arnemunthekaas.engine.entities.components.Component;
import no.arnemunthekaas.physics2d.enums.BodyType;
import org.jbox2d.dynamics.Body;
import org.joml.Vector2f;

public class Rigidbody2D extends Component {
    private Vector2f velocity = new Vector2f();
    private float angularDamping = 0.8f;
    private float linearDamping = 0.9f;
    private float mass = 0;
    private BodyType bodyType = BodyType.Dynamic;

    private boolean fixedRotation = false;
    private boolean continuousCollision = true;

    private Body rawBody = null;

    @Override
    public void update(float dt) {
        if(rawBody != null) {
            this.gameObject.transform.position.set(rawBody.getPosition().x, rawBody.getPosition().y);
            this.gameObject.transform.rotation = (float) Math.toDegrees(rawBody.getAngle());
        }
    }

    /**
     *
     * @return
     */
    public Vector2f getVelocity() {
        return velocity;
    }

    /**
     *
     * @param velocity
     */
    public void setVelocity(Vector2f velocity) {
        this.velocity = velocity;
    }

    /**
     *
     * @return
     */
    public float getAngularDamping() {
        return angularDamping;
    }

    /**
     *
     */
    public void setAngularDamping(float angularDamping) {
        this.angularDamping = angularDamping;
    }

    /**
     *
     * @return
     */
    public float getLinearDamping() {
        return linearDamping;
    }

    /**
     *
     * @param linearDamping
     */
    public void setLinearDamping(float linearDamping) {
        this.linearDamping = linearDamping;
    }

    /**
     *
     * @return
     */
    public float getMass() {
        return mass;
    }

    /**
     *
     * @param mass
     */
    public void setMass(float mass) {
        this.mass = mass;
    }

    /**
     *
     * @return
     */
    public BodyType getBodyType() {
        return bodyType;
    }

    /**
     *
     * @param bodyType
     */
    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    /**
     *
     * @return
     */
    public boolean isFixedRotation() {
        return fixedRotation;
    }

    /**
     *
     * @param fixedRotation
     */
    public void setFixedRotation(boolean fixedRotation) {
        this.fixedRotation = fixedRotation;
    }

    /**
     *
     * @return
     */
    public boolean isContinuousCollision() {
        return continuousCollision;
    }

    /**
     *
     * @param continuousCollision
     */
    public void setContinuousCollision(boolean continuousCollision) {
        this.continuousCollision = continuousCollision;
    }

    /**
     *
     * @return
     */
    public Body getRawBody() {
        return rawBody;
    }

    /**
     *
     * @param rawBody
     */
    public void setRawBody(Body rawBody) {
        this.rawBody = rawBody;
    }


}
