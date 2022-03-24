package no.arnemunthekaas.engine.scenes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(200,200)), 1);
        SpriteRenderer spr1 = new SpriteRenderer();
        spr1.setColor(new Vector4f(1,0,0,0.5f));
        obj1.addComponent(spr1);
        this.addGameObject(obj1);
        this.activeGameObject = obj1;

        GameObject obj2 = new GameObject("Obj2", new Transform(new Vector2f(200, 200), new Vector2f(200,200)), 0);
        SpriteRenderer spr2 = new SpriteRenderer();
        spr2.setSprite(sprites.getSprite(8));
        obj2.addComponent(spr2);
        this.addGameObject(obj2);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String serialised = gson.toJson(obj1);
        GameObject obj = gson.fromJson(serialised, GameObject.class);
        System.out.println(obj1);
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
