package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.editor.PropertiesWindow;
import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.eventlisteners.KeyListener;
import no.arnemunthekaas.utils.GameConstants;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class KeyControls extends Component{

    @Override
    public void editorUpdate(float dt) {
        PropertiesWindow propertiesWindow = Window.getImGuiLayer().getPropertiesWindow();
        GameObject activeGameObject = propertiesWindow.getActiveGameObject();

        List<GameObject> activeGameObjects = propertiesWindow.getActiveGameObjects();

        if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) && KeyListener.isKeyBeginPress(GLFW_KEY_D) && activeGameObject != null) {
            GameObject newObj = activeGameObject.copy();
            Window.getScene().addGameObject(newObj);
            newObj.transform.position.add(GameConstants.GRID_WIDTH, 0);
            propertiesWindow.setActiveGameObject(newObj);
        } else if (KeyListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL) && KeyListener.isKeyBeginPress(GLFW_KEY_D) && activeGameObjects.size() > 1) {
            List<GameObject> gameObjects = new ArrayList<>(activeGameObjects);
            propertiesWindow.clearSelected();
            for (GameObject go : gameObjects) {
                GameObject copy = go.copy();
                Window.getScene().addGameObject(copy);
                propertiesWindow.addActiveGameObject(copy);
            }
        } else if ((KeyListener.isKeyBeginPress(GLFW_KEY_DELETE) || KeyListener.isKeyBeginPress(GLFW_KEY_BACKSPACE))) {
            activeGameObjects.forEach(go -> go.destroy());
            propertiesWindow.clearSelected();
        }
    }
}
