package no.arnemunthekaas.scenes;

import imgui.ImGui;
import imgui.ImVec2;
import no.arnemunthekaas.engine.Camera;
import no.arnemunthekaas.engine.entities.components.*;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.gizmos.GizmoSystem;
import no.arnemunthekaas.engine.entities.Prefabs;
import no.arnemunthekaas.utils.AssetPool;
import no.arnemunthekaas.utils.GameConstants;
import org.joml.Vector2f;


public class LevelEditorScene extends Scene {

    private Spritesheet sprites;

    GameObject levelEditorComponents = this.createGameObject("Level editor");

    public LevelEditorScene() {

    }

    @Override
    public void init() {

        loadResources();
        sprites = AssetPool.getSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");
        Spritesheet gizmos = AssetPool.getSpriteSheet("assets/images/gizmos.png");

        this.camera = new Camera(new Vector2f(-250, 0));

        levelEditorComponents.addComponent(new MouseControls());
        levelEditorComponents.addComponent(new GridLines());
        levelEditorComponents.addComponent(new EditorCamera(this.camera));

        levelEditorComponents.addComponent(new GizmoSystem(gizmos));

        levelEditorComponents.start();

//        GameObject obj1 = new GameObject("Obj1", new Transform(new Vector2f(100, 200), new Vector2f(200,200)), 1);
//        SpriteRenderer spr1 = new SpriteRenderer();
//        spr1.setColor(new Vector4f(1,0,0,0.5f));
//        obj1.addComponent(spr1);
//        obj1.addComponent(new Rigidbody());
//        this.addGameObject(obj1);
//
//        GameObject obj2 = new GameObject("Obj1", new Transform(new Vector2f(100, 100), new Vector2f(800,800)), 1);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        Sprite obj2Sprite = new Sprite();
//        obj2Sprite.setTexture(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_creatures_trans.png"));
//        obj2SpriteRenderer.setSprite(obj2Sprite);
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObject(obj2);

    }

    private void loadResources() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getShader("assets/shaders/pickingShader.glsl");
        AssetPool.getShader("assets/shaders/debugLine2D.glsl");

        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"),
                24, 24, 204, 0));

        AssetPool.addSpriteSheet("assets/images/gizmos.png", new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),24,48,3,0));


        for(GameObject go : gameObjects) {
            if(go.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }
        }

    }


    @Override
    public void update(float dt) {

        // System.out.println("Fps: " + 1.0f / dt);
        levelEditorComponents.update(dt);

        this.camera.adjustProjection();


        gameObjects.forEach(c -> c.update(dt));

    }

    @Override
    public void render() {
        this.renderer.render();
    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Components");
        levelEditorComponents.imgui();
        ImGui.end();

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
