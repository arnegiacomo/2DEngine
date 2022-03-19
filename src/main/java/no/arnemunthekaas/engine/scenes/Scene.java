package no.arnemunthekaas.engine.scenes;

import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.GameObject;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Camera camera;
    private boolean running = false;
    protected List<GameObject> gameObjects = new ArrayList<>();

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

    /**
     * Starts scene.
     * Starts all Game Objects and their components.
     */
    public void start() {
        gameObjects.forEach(GameObject::start);
        running = true;
    }


    /**
     * Add Game Object to scene
     *
     * @param go Game Object to add
     */
    public void addGameObject(GameObject go) {
        if(!running)
            gameObjects.add(go);
        else {
            gameObjects.add(go);
            go.start();
        }
    }

}
