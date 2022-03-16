package no.arnemunthekaas.engine.listener;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Class handling the engines KeyListener, all methods are static and uses the get() method so that there
 * can only be one instance of this class. See https://www.glfw.org/docs/3.3/input_guide.html
 */
public class KeyListener {
    private static KeyListener instance;
    private boolean keyPressed[] = new boolean[GLFW_KEY_LAST + 2]; // <- How many keys the program supports

    private KeyListener() {

    }

    /**
     * Returns the KeyListener instance, if null creates new KeyListener
     * This is so that there is one and only one KeyListener at all times.
     *
     * @return KeyListener instance
     */
    public static KeyListener get() {
        if (KeyListener.instance == null)
            KeyListener.instance = new KeyListener();
        return KeyListener.instance;
    }

    /**
     * The callback function receives the keyboard key, platform-specific scancode, key action and modifier bits.
     *
     * @param window   window reference
     * @param key      key that is being done an action on
     * @param scancode platform-specific scancode
     * @param action   key action
     * @param mods     modifier
     */
    public static void keyCallBack(long window, int key, int scancode, int action, int mods) {
        if (key == GLFW_KEY_UNKNOWN) return; // To prevent IndexOutOfBoundsException when pressing a key undefined in GLFW. See https://www.glfw.org/docs/3.3/group__keys.html

        if(action == GLFW_PRESS)
            get().keyPressed[key] = true;
        else if (action == GLFW_RELEASE)
            get().keyPressed[key] = false;
    }

    // Static getters and setters for single KeyListener Instance

}
