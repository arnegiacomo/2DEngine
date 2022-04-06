package no.arnemunthekaas.engine.editor;

import imgui.ImGui;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.UnPickable;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;
import no.arnemunthekaas.engine.renderer.PickingTexture;
import no.arnemunthekaas.engine.scenes.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {

    private GameObject activeGameObject;
    private PickingTexture pickingTexture;

    private float debounce = 0.2f;

    /**
     *
     * @param pickingTexture
     */
    public PropertiesWindow(PickingTexture pickingTexture) {
        this.pickingTexture = pickingTexture;
    }

    /**
     *
     * @param dt
     * @param currentScene
     */
    public void update(float dt, Scene currentScene) {
        debounce -= dt;

        if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT) && debounce < 0) {
            int x = (int) MouseListener.getScreenX();
            int y = (int) MouseListener.getScreenY();
            int gameObjectID = pickingTexture.readPixel(x, y);
            GameObject go = currentScene.getGameObject(gameObjectID);

            if( go != null && go.getComponent(UnPickable.class) == null)
                activeGameObject = go;
            else if( go == null && !MouseListener.isDragging())
                activeGameObject = null;
            this.debounce = 0.2f;
        }
    }

    /**
     *
     */
    public void imgui() {
        if(activeGameObject != null) {
            ImGui.begin("Properties");
            activeGameObject.imgui();
            ImGui.end();
        }
    }

    /**
     * Returns active game object
     * @return
     */
    public GameObject getActiveGameObject() {
        return activeGameObject;
    }
}
