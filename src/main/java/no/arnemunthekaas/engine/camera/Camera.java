package no.arnemunthekaas.engine.camera;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {

    private Matrix4f projectionMat, viewMat;
    public Vector2f position;

    /**
     * Create a Camera object
     *
     * @param position Position vector
     */
    public Camera(Vector2f position) {
        this.position = position;
        this.projectionMat = new Matrix4f();
        this.viewMat = new Matrix4f();
        adjustProjection();
    }

    /**
     *  Adjusts current projection
     */
    public void adjustProjection() {
        projectionMat.identity();
        projectionMat.ortho(0.0f, 32.0f * 40.0f, 0.0f, 32.0f * 21.0f, 0.0f, 100.0f);
    }

    /**
     * Returns the view matrix
     *
     * @return Matrix4f view matrix
     */
    public Matrix4f getViewMat() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        this.viewMat.identity();
        viewMat.lookAt(new Vector3f(position.x, position.y, 20.0f), cameraFront.add(position.x, position.y, 0.0f), cameraUp);

        return this.viewMat;
    }

    /**
     * Returns the projection matrix
     * @return Matrix4f for projection
     */
    public Matrix4f getProjectionMat() {
        return this.projectionMat;
    }

}
