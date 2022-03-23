package no.arnemunthekaas.engine.scenes;

import imgui.ImGui;
import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;
import no.arnemunthekaas.engine.entities.components.Spritesheet;
import no.arnemunthekaas.engine.renderer.Texture;
import no.arnemunthekaas.engine.renderer.Transform;
import no.arnemunthekaas.engine.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));

        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");

        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(200,200)), -110);
        obj1.addComponent(new SpriteRenderer(new Vector4f(0, 1, 0, 1)));
        this.addGameObject(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Obj2", new Transform(new Vector2f(0, 200), new Vector2f(200,200)), -10);
        obj2.addComponent(new SpriteRenderer(new Sprite(new Texture("assets/images/spritesheets/blendImage1.png"))));
        this.addGameObject(obj2);



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"),
                24, 24, 204, 0));

    }


    @Override
    public void update(float dt) {

        // System.out.println("Fps: " + 1.0f / dt);

        gameObjects.forEach(c -> c.update(dt));

        this.renderer.render();

    }

    @Override
    public void imgui() {

    }
}
