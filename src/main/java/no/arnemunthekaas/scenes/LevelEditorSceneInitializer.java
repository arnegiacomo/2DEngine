package no.arnemunthekaas.scenes;

import imgui.ImGui;
import imgui.ImVec2;
import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.audio.Sound;
import no.arnemunthekaas.engine.entities.components.*;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.animation.StateMachine;
import no.arnemunthekaas.engine.entities.components.gizmos.GizmoSystem;
import no.arnemunthekaas.engine.entities.Prefabs;
import no.arnemunthekaas.engine.entities.physics2d.components.Box2DCollider;
import no.arnemunthekaas.engine.entities.physics2d.components.Rigidbody2D;
import no.arnemunthekaas.engine.entities.physics2d.enums.BodyType;
import no.arnemunthekaas.utils.AssetPool;
import no.arnemunthekaas.utils.GameConstants;
import org.joml.Vector2f;
import org.joml.Vector2i;

import java.io.File;
import java.util.Collection;

import static org.lwjgl.glfw.GLFW.*;


public class LevelEditorSceneInitializer extends SceneInitializer {

    private Spritesheet sprites;
    private GameObject levelEditorComponents;

    public LevelEditorSceneInitializer() {
    }

    @Override
    public void init(Scene scene) {
        sprites = AssetPool.getSpritesheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");
        Spritesheet gizmos = AssetPool.getSpritesheet("assets/images/gizmos.png");

        levelEditorComponents = scene.createGameObject("LevelEditor");
        levelEditorComponents.setNoSerialization();

        levelEditorComponents.addComponent(new MouseControls());
        levelEditorComponents.addComponent(new KeyControls());
        levelEditorComponents.addComponent(new GridLines());
        levelEditorComponents.addComponent(new EditorCamera(scene.getCamera()));
        levelEditorComponents.addComponent(new GizmoSystem(gizmos));

        scene.addGameObject(levelEditorComponents);
    }

    @Override
    public void loadResources(Scene scene) {

        // Load shaders
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.getShader("assets/shaders/pickingShader.glsl");
        AssetPool.getShader("assets/shaders/debugLine2D.glsl");

        // Load Images
        AssetPool.addSpriteSheet("assets/images/gizmos.png", new Spritesheet(AssetPool.getTexture("assets/images/gizmos.png"),24,48,3,0));
        AssetPool.addSpriteSheet("assets/images/missingtex.png", new Spritesheet(AssetPool.getTexture("assets/images/missingtex.png"),32,32,1,0));

        // Load Spritesheets
        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png"),
                24, 24, 204, 0));
        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_creatures_trans.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_creatures_trans.png"),
                24, 24, 204, 0));

        AssetPool.addSpriteSheet("assets/images/spritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheet.png"),
                16, 16, 14, 0));
        AssetPool.addSpriteSheet("assets/images/bigSpritesheet.png", new Spritesheet(AssetPool.getTexture("assets/images/bigSpritesheet.png"),
                16, 32, 42, 0));

        AssetPool.addSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_items_trans.png", new Spritesheet(AssetPool.getTexture("assets/images/spritesheets/oryx_16bit_fantasy_items_trans.png"),
                16, 16, 300, 0));

        // Load Sounds
        AssetPool.addSound("assets/audio/assets_sounds_1-up.ogg", false);
        AssetPool.addSound("assets/audio/assets_sounds_jump-small.ogg", false);
        AssetPool.addSound("assets/audio/assets_sounds_jump-super.ogg", false);
        AssetPool.addSound("assets/audio/assets_sounds_bump.ogg", false);
        AssetPool.addSound("assets/audio/assets_sounds_break_block.ogg", false);

        for(GameObject go : scene.getGameObjects()) {
            if(go.getComponent(SpriteRenderer.class) != null) {
                SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
                if (spr.getTexture() != null) {
                    spr.setTexture(AssetPool.getTexture(spr.getTexture().getFilepath()));
                }
            }

            if(go.getComponent(StateMachine.class) != null) {
                StateMachine stateMachine = go.getComponent(StateMachine.class);
                stateMachine.refreshTextures();
            }
        }

    }

    @Override
    public void imgui() {
        ImGui.begin("Level Editor Components");
        levelEditorComponents.imgui();
        ImGui.end();

        ImGui.begin("Objects");

        if (ImGui.beginTabBar("WindowTabBar")) {
            if (ImGui.beginTabItem("Tiles")) {
                ImVec2 windowPos = new ImVec2();
                ImGui.getWindowPos(windowPos);
                ImVec2 windowSize = new ImVec2();
                ImGui.getWindowSize(windowSize);
                ImVec2 itemSpacing = new ImVec2();
                ImGui.getStyle().getItemSpacing(itemSpacing);

                float windowX2 = windowPos.x + windowSize.x;
                for (int i = 0; i < sprites.size(); i++) {
                    // TODO: Skip all sprites that will not have box colliders
                    Sprite sprite = sprites.getSprite(i);
                    float spriteWidth = sprite.getWidth() * 2;
                    float spriteHeight = sprite.getHeight() * 2;
                    int id = sprite.getTexID();
                    Vector2f[] texCoords = sprite.getTexCoords();

                    ImGui.pushID(i);
                    if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                        GameObject gameObject = Prefabs.generateSpriteObject(sprite, GameConstants.GRID_WIDTH, GameConstants.GRID_HEIGHT);
                        Rigidbody2D rigidbody2D = new Rigidbody2D();
                        rigidbody2D.setBodyType(BodyType.Static);
                        gameObject.addComponent(rigidbody2D);
                        Box2DCollider box2DCollider = new Box2DCollider();
                        box2DCollider.setHalfSize(new Vector2f(GameConstants.GRID_WIDTH, GameConstants.GRID_HEIGHT));
                        gameObject.addComponent(box2DCollider);
                        gameObject.addComponent(new Ground());

                        if(i == 1 || i == 2)
                            gameObject.addComponent(new Breakable());

                        levelEditorComponents.getComponent(MouseControls.class).pickUpObject(gameObject);

                    }
                    ImGui.popID();

                    ImVec2 lastButtonPos = new ImVec2();
                    ImGui.getItemRectMax(lastButtonPos);
                    float lastButtonX2 = lastButtonPos.x;
                    float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;

                    if (i + 1 < sprites.size() && nextButtonX2 < windowX2)
                        ImGui.sameLine();

                }
                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Prefabs")) {
                Spritesheet playerSprites = AssetPool.getSpritesheet("assets/images/spritesheets/oryx_16bit_fantasy_creatures_trans.png");
                Sprite sprite = playerSprites.getSprite(22);
                float spriteWidth = sprite.getWidth() * 2;
                float spriteHeight = sprite.getHeight() * 2;
                int id = sprite.getTexID();
                Vector2f[] texCoords = sprite.getTexCoords();
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject gameObject = Prefabs.generatePlayerCharacter();
                    // Attach to cursor
                    levelEditorComponents.getComponent(MouseControls.class).pickUpObject(gameObject);

                }

                ImGui.sameLine();

                Spritesheet items = AssetPool.getSpritesheet("assets/images/spritesheets/oryx_16bit_fantasy_tiles.png");
                sprite = items.getSprite(4);
                id = sprite.getTexID();
                texCoords = sprite.getTexCoords();
                if (ImGui.imageButton(id, spriteWidth, spriteHeight, texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {
                    GameObject object = Prefabs.generateQuestionBlock();
                    levelEditorComponents.getComponent(MouseControls.class).pickUpObject(object);
                }

                ImGui.sameLine();

                ImGui.endTabItem();
            }

            if (ImGui.beginTabItem("Sounds")) {
                Collection<Sound> sounds = AssetPool.getAllSounds();
                for (Sound sound : sounds) {
                    File tmp = new File(sound.getFilepath());
                    if (ImGui.button(tmp.getName())) {
                        if (!sound.isPlaying())
                            sound.play();
                        else
                            sound.stop();
                    }

                    if (ImGui.getContentRegionAvailX() > 100)
                        ImGui.sameLine();
                }

                ImGui.sameLine();

                ImGui.endTabItem();
            }

            ImGui.endTabBar();
        }


        ImGui.end();
    }
}
