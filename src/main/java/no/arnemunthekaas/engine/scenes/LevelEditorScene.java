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
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.image.PackedColorModel;

public class LevelEditorScene extends Scene {

    private Spritesheet sprites;

    MouseControls mouseControls = new MouseControls();

    public LevelEditorScene() {

    }

    @Override
    public void init() {
        loadResources();
        this.camera = new Camera(new Vector2f(-250, 0));
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");

        if(levelLoaded) {
            this.activeGameObject = gameObjects.get(0);
            return;
        }

        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(200,200)), 1);
        SpriteRenderer spr1 = new SpriteRenderer();
        spr1.setColor(new Vector4f(1,0,0,0.5f));
        obj1.addComponent(spr1);
        obj1.addComponent(new Rigidbody());
        this.addGameObject(obj1);

        GameObject obj2 = new GameObject("Obj2", new Transform(new Vector2f(200, 200), new Vector2f(200,200)), 0);
        SpriteRenderer spr2 = new SpriteRenderer();
        spr2.setSprite(sprites.getSprite(8));
        obj2.addComponent(spr2);
        this.addGameObject(obj2);



    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"),
                24, 24, 204, 0));

    }


    float t = 0.0f;
    @Override
    public void update(float dt) {

        float x = ((float) Math.sin(t) * 200.0f) + 600;
        float y = ((float) Math.cos(t) * 200.0f) + 400;

        t += 0.05f;
        DebugDraw.addLine2D(new Vector2f(600,400), new Vector2f(x,y), new Vector3f(0,0,1), 10);

        // System.out.println("Fps: " + 1.0f / dt);
        mouseControls.update(dt);

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
            float spriteWidth = sprite.getWidth() * 2; // TODO SCALING, VIDEO WAS *4 BUT I CHANGED TO *2
            float spriteHeight = sprite.getHeight() * 2; // TODO SCALING, VIDEO WAS *4 BUT I CHANGED TO *2
            int id = sprite.getTexID();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[0].x, texCoords[0].y, texCoords[2].x, texCoords[2].y)) {
                GameObject gameObject = Prefabs.generateSpriteObject(sprite, spriteWidth, spriteHeight);
                // Attach to cursor
                mouseControls.pickUpObject(gameObject);

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
