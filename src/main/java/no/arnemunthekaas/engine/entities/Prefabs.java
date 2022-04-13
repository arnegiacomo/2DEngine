package no.arnemunthekaas.engine.entities;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;

public class Prefabs {

    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject gameObject = Window.getScene().createGameObject("Sprite_Object_Gen");
        gameObject.transform.scale.x = sizeX;
        gameObject.transform.scale.y = sizeY;

        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSprite(sprite);
        gameObject.addComponent(spriteRenderer);
        return gameObject;
    }
}
