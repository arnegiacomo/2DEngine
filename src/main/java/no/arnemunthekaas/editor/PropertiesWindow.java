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

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;

public class PropertiesWindow {

    private List<GameObject> activeGameObjects;
    private GameObject activeGameObject;
    private PickingTexture pickingTexture;

    /**
     *
     * @param pickingTexture
     */
    public PropertiesWindow(PickingTexture pickingTexture) {
        this.activeGameObjects = new ArrayList<>();
        this.pickingTexture = pickingTexture;
    }

    /**
     *
     */
    public void imgui() {
        if(activeGameObjects.size() == 1 && activeGameObjects.get(0) != null) {
            activeGameObject = activeGameObjects.get(0);
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
        return this.activeGameObjects.size() == 1 ? this.activeGameObjects.get(0) : null;
    }

    /**
     * Set active game object
     * @param go
     */
    public void setActiveGameObject(GameObject go) {
        if (go != null) {
            clearSelected();
            this.activeGameObjects.add(go);
        }
    }

    /**
     * Returns all active game objects
     * @return
     */
    public List<GameObject> getActiveGameObjects() {
        return this.activeGameObjects;
    }

    /**
     *
     */
    public void clearSelected() {
        this.activeGameObjects.clear();
    }

    /**
     *
     * @param go
     */
    public void addActiveGameObject(GameObject go) {
        this.activeGameObjects.add(go);
    }

    /**
     *
     * @return
     */
    public PickingTexture getPickingTexture() {
        return this.pickingTexture;
    }
}
