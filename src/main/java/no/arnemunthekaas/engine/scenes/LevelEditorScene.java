package no.arnemunthekaas.engine.scenes;

import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;
import no.arnemunthekaas.engine.renderer.Transform;
import no.arnemunthekaas.engine.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        this.camera = new Camera(new Vector2f(-250, 0));

        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 100), new Vector2f(256,256)));
        obj1.addComponent(new SpriteRenderer(AssetPool.getTexture("assets/images/Untitled.png")));
        this.addGameObject(obj1);

        loadResources();

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

    }

    @Override
    public void update(float dt) {
        System.out.println("Fps: " + 1.0f / dt);

        gameObjects.forEach(c -> c.update(dt));

        this.renderer.render();

    }
}
