package no.arnemunthekaas.engine.prefabs;

import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.entities.components.SpriteRenderer;
import no.arnemunthekaas.engine.renderer.Transform;
import org.joml.Vector2f;

public class Prefabs {

    public static GameObject generateSpriteObject(Sprite sprite, float sizeX, float sizeY) {
        GameObject gameObject = new GameObject("Sprite_Object_Gen", new Transform(new Vector2f(), new Vector2f(sizeX, sizeY)), 0);
        SpriteRenderer spriteRenderer = new SpriteRenderer();
        spriteRenderer.setSprite(sprite);
        gameObject.addComponent(spriteRenderer);
        return gameObject;
    }
}
