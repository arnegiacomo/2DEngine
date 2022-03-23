package no.arnemunthekaas.engine.scenes;

import imgui.ImGui;
import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.imgui.ImGuiLayer;
import no.arnemunthekaas.engine.renderer.Renderer;

import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean running = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject;

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
        for(GameObject go : gameObjects) {
            go.start();
            renderer.add(go);
        }
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
            renderer.add(go);
        }
    }

    /**
     * Get Scene Camera
     *
     * @return Camera
     */
    public Camera getCamera() {
        return camera;
    }

    /**
     *
     */
    public void sceneImgui() {
        if(activeGameObject != null) {
            ImGui.begin("Inspector");
            activeGameObject.imgui();
            ImGui.end();
        }

        imgui();
    }

    /**
     *
     */
    public void imgui() {

    }
}
