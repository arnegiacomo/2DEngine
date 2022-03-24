package no.arnemunthekaas.engine.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.Component;
import no.arnemunthekaas.engine.entities.deserializers.ComponentDeserializer;
import no.arnemunthekaas.engine.entities.deserializers.GameObjectDeserializer;
import no.arnemunthekaas.engine.imgui.ImGuiLayer;
import no.arnemunthekaas.engine.renderer.Renderer;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public abstract class Scene {

    protected Renderer renderer = new Renderer();
    protected Camera camera;
    private boolean running = false;
    protected List<GameObject> gameObjects = new ArrayList<>();
    protected GameObject activeGameObject;
    protected boolean levelLoaded;

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

    /**
     *
     */
    public void saveExit() {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Component.class, new ComponentDeserializer()).registerTypeAdapter(GameObject.class, new GameObjectDeserializer()).create();

        try {
            FileWriter writer = new FileWriter("level.txt");
            writer.write(gson.toJson(this.gameObjects));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    public void load() {
        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Component.class, new ComponentDeserializer()).registerTypeAdapter(GameObject.class, new GameObjectDeserializer()).create();
        String inFile = null;

        try {
            inFile = new String(Files.readAllBytes(Paths.get("level.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(inFile != null) {
            GameObject[] objs = gson.fromJson(inFile, GameObject[].class);
            for (GameObject o : objs) {
                addGameObject(o);
            }
            this.levelLoaded = true;

        }
    }
}
