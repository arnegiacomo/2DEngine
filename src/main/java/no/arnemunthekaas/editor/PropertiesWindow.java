package no.arnemunthekaas.editor;

import imgui.ImGui;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.engine.entities.components.UnPickable;
import no.arnemunthekaas.engine.entities.physics2d.components.Box2DCollider;
import no.arnemunthekaas.engine.entities.physics2d.components.CircleCollider;
import no.arnemunthekaas.engine.entities.physics2d.components.Rigidbody2D;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;
import no.arnemunthekaas.engine.renderer.PickingTexture;
import no.arnemunthekaas.scenes.Scene;

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

            if (ImGui.beginPopupContextWindow("ComponentAdder")) {
                if(ImGui.menuItem("Add Rigidbody")) {
                    if (activeGameObject.getComponent(Rigidbody2D.class) == null) {
                        activeGameObject.addComponent(new Rigidbody2D());
                    }
                }

                if (ImGui.menuItem("Add Box collider")) {
                    if (activeGameObject.getComponent(Box2DCollider.class) == null &&
                            activeGameObject.getComponent(CircleCollider.class)  == null) {
                        activeGameObject.addComponent(new Box2DCollider());
                    }
                }

                if (ImGui.menuItem("Add Circle collider")) {
                    if (activeGameObject.getComponent(CircleCollider.class) == null &&
                            activeGameObject.getComponent(Box2DCollider.class) == null) {
                        activeGameObject.addComponent(new CircleCollider());
                    }
                }

                ImGui.endPopup();
            }

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

    /**
     * Set active game object
     * @param o
     */
    public void setActiveGameObject(GameObject o) {
        activeGameObject = o;
    }
}
