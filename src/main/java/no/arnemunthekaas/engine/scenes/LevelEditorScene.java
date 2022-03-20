package no.arnemunthekaas.engine.scenes;

import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;
import no.arnemunthekaas.engine.entities.components.Spritesheet;
import no.arnemunthekaas.engine.renderer.Transform;
import no.arnemunthekaas.engine.utils.AssetPool;
import org.joml.Vector2f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private Spritesheet sprites;

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));

//        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(408,244)));
//        obj1.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"))));
//        this.addGameObject(obj1);

//        GameObject obj2 = new GameObject("Obj2", new Transform(new Vector2f(300, 200), new Vector2f(564,592)));
//        obj2.addComponent(new SpriteRenderer(new Sprite(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_fx_trans.png"))));
//        this.addGameObject(obj2);
//
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");

        obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(24,24)));
        obj1.addComponent(new SpriteRenderer(sprites.getSprite(0)));
        this.addGameObject(obj1);
//
//        GameObject obj4 = new GameObject("Obj2", new Transform(new Vector2f(300, 200), new Vector2f(24,24)));
//        obj4.addComponent(new SpriteRenderer(spritesheet.getSprite(132)));
//        this.addGameObject(obj4);
//
//        GameObject obj5 = new GameObject("Obj3", new Transform(new Vector2f(300, 100), new Vector2f(24,24)));
//        obj5.addComponent(new SpriteRenderer(spritesheet.getSprite(15)));
//        this.addGameObject(obj5);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"),
                24, 24, 204, 0));

    }

    private int spriteIndex = 0;
    private float spriteFlipTime = 0.2f;
    private float spriteFlipTimeLeft = 0.0f;
    @Override
    public void update(float dt) {
        spriteFlipTimeLeft -= dt;
        if (spriteFlipTimeLeft <= 0) {
            spriteFlipTimeLeft = spriteFlipTime;
            spriteIndex++;
            if (spriteIndex > 200) {
                spriteIndex = 0;
            }
            obj1.getComponent(SpriteRenderer.class).setSprite(sprites.getSprite(spriteIndex));
        }

        // System.out.println("Fps: " + 1.0f / dt);

        gameObjects.forEach(c -> c.update(dt));

        this.renderer.render();

    }
}
