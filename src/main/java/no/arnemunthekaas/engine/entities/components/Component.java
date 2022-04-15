package no.arnemunthekaas.engine.entities.components;

import imgui.ImGui;
import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.editor.imgui.ImGuiUtils;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class Component {

    private static int ID_COUNTER = 0;
    private int uid = -1;

    public transient GameObject gameObject;

    /**
     * Update component with delta time
     *
     * @param dt Delta time
     */
    public void update(float dt) {

    }

    /**
     *
     * @param dt
     */
    public void editorUpdate(float dt) {
    }

    /**
     * Start component
     */
    public void start() {

    }

    /**
     *
     */
    public void imgui() {
        try {
            Field[] fields = this.getClass().getDeclaredFields();
            for (Field f : fields) {
                boolean isTransient = Modifier.isTransient(f.getModifiers());
                if (isTransient)
                    continue;

                boolean isPrivate = Modifier.isPrivate(f.getModifiers());
                if (isPrivate)
                    f.setAccessible(true);


                Class type = f.getType();
                Object value = f.get(this);
                String name = f.getName();

                if (type == int.class) {
                    int val = (int) value;
                    f.set(this, ImGuiUtils.dragInt(name, val));

                } else if (type == float.class) {
                    float val = (float) value;
                    f.set(this, ImGuiUtils.dragFloat(name, val));

                } else if (type == boolean.class) {
                    boolean val = (boolean) value;
//                    f.set(this, ImGuiUtils.checkBox(name, val));
                    if (ImGui.checkbox(name + ": ", val)) {
                        f.set(this, !val);
                    }

                } else if (type == Vector2f.class) {
                    Vector2f val = (Vector2f) value;
                    ImGuiUtils.drawVec2Control(name, val);

                } else if (type == Vector3f.class) {
                    Vector3f val = (Vector3f) value;
                    ImGuiUtils.drawVec3Control(name, val);

                } else if (type == Vector4f.class) {
                    Vector4f val = (Vector4f) value;
                    ImGuiUtils.colorPicker4(name, val);
                }

                if (isPrivate)
                    f.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Give a game object a uid
     */
    public void generateID() {
        if(this.uid == -1) {
            this.uid = ID_COUNTER++;
        }
    }

    /**
     * Get unique ID
     * @return
     */
    public int getUid() {
        return uid;
    }

    /**
     *
     * @param maxID
     */
    public static void init(int maxID) {
        ID_COUNTER = maxID;
    }

    /**
     *
     */
    public void destroy() {
    }


}
