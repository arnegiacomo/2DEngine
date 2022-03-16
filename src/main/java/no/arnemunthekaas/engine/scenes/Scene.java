package no.arnemunthekaas.engine.scenes;

public abstract class Scene {

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
