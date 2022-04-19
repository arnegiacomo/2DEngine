package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.entities.components.animation.StateMachine;
import no.arnemunthekaas.engine.eventlisteners.KeyListener;
import no.arnemunthekaas.utils.GameConstants;
import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

public class MouseControls extends Component{

    private GameObject holdingObject;
    private float debounceTime = 0.05f;
    private float debounce = debounceTime;

    // TODO: Fix object duplication on place
    /**
     * Change holding object
     * @param gameObject
     */
    public void pickUpObject(GameObject gameObject) {
        if(this.holdingObject != null)
            this.holdingObject.destroy();
        this.holdingObject = gameObject;
        this.holdingObject.getComponent(SpriteRenderer.class).setColor(new Vector4f(0.8f, 0.8f, 0.8f, 0.5f));
        this.holdingObject.addComponent(new UnPickable());
        Window.getScene().addGameObject(gameObject);
    }

    /**
     *
     */
    public void place() {
        GameObject newObj = this.holdingObject.copy();
        newObj.getComponent(SpriteRenderer.class).setColor(new Vector4f(1, 1, 1, 1));
        if (newObj.getComponent(StateMachine.class) != null)
            newObj.getComponent(StateMachine.class).refreshTextures();

        newObj.removeComponent(UnPickable.class);
        Window.getScene().addGameObject(newObj);
    }

    @Override
    public void editorUpdate(float dt) {
        debounce -= dt;
        if(holdingObject != null && debounce <= 0.0f) {
            float x = MouseListener.getWorldX();
            float y = MouseListener.getWorldY();
            holdingObject.transform.position.x = ((int)Math.floor(x / GameConstants.GRID_WIDTH) * GameConstants.GRID_WIDTH) + GameConstants.GRID_WIDTH / 2.0f;
            holdingObject.transform.position.y = ((int)Math.floor(y / GameConstants.GRID_HEIGHT) * GameConstants.GRID_HEIGHT) + GameConstants.GRID_HEIGHT / 2.0f;

//
            if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
                debounce = debounceTime;
            }

            if(KeyListener.isKeyPressed(GLFW_KEY_ESCAPE) || MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_RIGHT)) {
                holdingObject.destroy();
                holdingObject = null;
            }
        }
    }
}
