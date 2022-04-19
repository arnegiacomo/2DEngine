package no.arnemunthekaas.engine.entities;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;
import no.arnemunthekaas.engine.entities.components.Spritesheet;
import no.arnemunthekaas.engine.entities.components.animation.AnimationState;
import no.arnemunthekaas.engine.entities.components.animation.StateMachine;
import no.arnemunthekaas.utils.AssetPool;
import no.arnemunthekaas.utils.GameConstants;

public class Prefabs {

    /**
     *
     * @param sprite
     * @param sizeX
     * @param sizeY
     * @return
     */
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        return generateSpriteObject(sprite, sizeX, sizeY, "Sprite_Object_Gen");
    }

    /**
     *
     * @param sprite
     * @param sizeX
     * @param sizeY
     * @param name
     * @return
     */
    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY, String name) {
        GameObject gameObject = Window.getScene().createGameObject(name);
        gameObject.transform.scale.x = sizeX;
        gameObject.transform.scale.y = sizeY;

        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSprite(sprite);
        gameObject.addComponent(spriteRenderer);
        return gameObject;
    }

    /**
     *
     * @return
     */
    public static GameObject generatePlayerCharacter() {
        Spritesheet playerSprites = AssetPool.getSpriteSheet("assets/images/spritesheets/oryx_16bit_fantasy_creatures_trans.png");
        GameObject playerCharacter = generateSpriteObject(playerSprites.getSprite(0), GameConstants.GRID_WIDTH, GameConstants.GRID_HEIGHT, "Prefab_Gen");

        AnimationState idle = new AnimationState();
        idle.title = "Idle";
        float defaultFrameTime = 0.5f;
        idle.addFrame(playerSprites.getSprite(22), defaultFrameTime);
        idle.addFrame(playerSprites.getSprite(23), defaultFrameTime);
        idle.addFrame(playerSprites.getSprite(24), defaultFrameTime);
        idle.addFrame(playerSprites.getSprite(25), defaultFrameTime);
        idle.setLoop(true);

        StateMachine stateMachine = new StateMachine();
        stateMachine.addState(idle);
        stateMachine.setDefaultState(idle.title);
        playerCharacter.addComponent(stateMachine);

        return playerCharacter;
    }
}
