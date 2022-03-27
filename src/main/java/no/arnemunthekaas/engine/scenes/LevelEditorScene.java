package no.arnemunthekaas.engine.scenes;

import imgui.ImGui;
import imgui.ImVec2;
import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.entities.components.*;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.prefabs.Prefabs;
import no.arnemunthekaas.engine.renderer.DebugDraw;
import no.arnemunthekaas.engine.renderer.Transform;
import no.arnemunthekaas.engine.utils.AssetPool;
import no.arnemunthekaas.engine.utils.GameConstants;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites;

    GameObject levelEditorComponents = new GameObject("Level editor", new Transform(), 0);

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        levelEditorComponents.addComponent(new MouseControls());
        levelEditorComponents.addComponent(new GridLines());

        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");

        if(levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

//        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(200,200)), 1);
//        SpriteRenderer spr1 = new SpriteRenderer();
//        spr1.setColor(new Vector4f(1,0,0,0.5f));
//        obj1.addComponent(spr1);
//        obj1.addComponent(new Rigidbody());
//        this.addGameObject(obj1);
//
//        GameObject obj2 = new GameObject("Obj2", new Transform(new Vector2f(200, 200), new Vector2f(200,200)), 0);
//        SpriteRenderer spr2 = new SpriteRenderer();
//        spr2.setSprite(sprites.getSprite(8));
//        obj2.addComponent(spr2);
//        this.addGameObject(obj2);



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"),
                24, 24, 204, 0));

    }

    float x = 0.0f;
    float y = 0.0f;

    @Override
    public void update(float dt) {

        // System.out.println("Fps: " + 1.0f / dt);
        levelEditorComponents.update(dt);

        DebugDraw.addCircle(new Vector2f(x, y), 64, new Vector3f(0,1,0), 1);
        x += 50f *dt;
        y += 50f*dt;

        gameObjects.forEach(c -> c.update(dt));

        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Test window");

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);
        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);
        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for(int i = 0; i < sprites.size(); i++) {
            Sprite sprite = sprites.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                GameObject gameObject = Prefabs.generateSpriteObject(sprite, GameConstants.GRID_WIDTH, GameConstants.GRID_HEIGHT);
                // Attach to cursor
                levelEditorComponents.getComponent(MouseControls.class).pickUpObject(gameObject);

            }
            ImGui.popID();

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;

            if(i + 1 < sprites.size() && nextButtonX2 < windowX2)
                ImGui.sameLine();

        }



        ImGui.end();
    }
}
