package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.utils.GameConstants;
import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class MouseControls extends Component{

    private GameObject holdingObject;

    /**
     * Change holding object
     * @param gameObject
     */
    public void pickUpObject(GameObject gameObject) {
        this.holdingObject = gameObject;
        Window.getScene().addGameObject(gameObject);
    }

    /**
     *
     */
    public void place() {
        this.holdingObject = null;
    }

    @Override
    public void update(float dt) {
        if(holdingObject != null) {
            holdingObject.transform.position.x = MouseListener.getOrthoX();
            holdingObject.transform.position.y = MouseListener.getOrthoY();
            holdingObject.transform.position.x = (int) (holdingObject.transform.position.x / GameConstants.GRID_WIDTH) * GameConstants.GRID_WIDTH;
            holdingObject.transform.position.y = (int) (holdingObject.transform.position.y / GameConstants.GRID_HEIGHT) * GameConstants.GRID_HEIGHT;

            holdingObject.transform.position.x -= holdingObject.transform.position.x <= GameConstants.GRID_WIDTH-1 ? GameConstants.GRID_WIDTH : 0; // TODO : WHY??

            if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                place();
            }
        }
    }
}
