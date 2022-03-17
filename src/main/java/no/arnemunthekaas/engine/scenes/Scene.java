package no.arnemunthekaas.engine.scenes;

import no.arnemunthekaas.engine.camera.Camera;

public abstract class Scene {

    protected Camera camera;

    public Scene() {

    }

    /**
     * Update the scene in game loop using delta time
     *
     * @param dt delta time
     */
    public abstract void update(float dt);


    /**
     *
     */
    public void init() {

    }

}
