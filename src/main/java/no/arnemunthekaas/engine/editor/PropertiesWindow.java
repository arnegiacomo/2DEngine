package no.arnemunthekaas.engine.editor;

import imgui.ImGui;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;
import no.arnemunthekaas.engine.renderer.PickingTexture;
import no.arnemunthekaas.engine.scenes.Scene;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {

    private GameObject activeGameObject;
    private PickingTexture pickingTexture;

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
        if(MouseListener.mouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
            int x = (int) MouseListener.getScreenX();
            int y = (int) MouseListener.getScreenY();
            int gameObjectID = pickingTexture.readPixel(x, y);
            activeGameObject = currentScene.getGameObject(gameObjectID);
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
}
