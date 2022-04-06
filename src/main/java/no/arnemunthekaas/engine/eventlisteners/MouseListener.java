package no.arnemunthekaas.engine.eventlisteners;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.utils.GameConstants;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector4f;

import static org.lwjgl.glfw.GLFW.*;

/**
 * Class handling the engines MouseListener, all methods are static and uses the get() method so that there
 * can only be one instance of this class. See https://www.glfw.org/docs/3.3/input_guide.html
 */
public class MouseListener {
    private static MouseListener instance;
    private double scrollX, scrollY;
    double xPos, yPos, lastY, lastX, worldX, worldY, lastWorldX, lastWorldY;
    private boolean mouseButtonPressed[] = new boolean[GLFW_MOUSE_BUTTON_LAST+1]; // <- how many mouse buttons program supports. See https://www.glfw.org/docs/3.3/group__buttons.html
    private boolean isDragging;

    private int mouseButtonDown = 0;

    private Vector2f gameViewportPos = new Vector2f();
    private Vector2f gameViewportSize = new Vector2f();

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.xPos = 0.0;
        this.yPos = 0.0;
        this.lastX = 0.0;
        this.lastY = 0.0;
    }

    /**
     * Returns the MouseListener instance, if null creates new MouseListener
     * This is so that there is one and only one MouseListener at all times.
     *
     * @return MouseListener instance
     */
    public static MouseListener get() {
        if (MouseListener.instance == null)
            MouseListener.instance = new MouseListener();
        return MouseListener.instance;
    }

    /**
     * @param window window reference
     * @param xPos   new x-position for mouse
     * @param yPos   new y-position for mouse
     */
    public static void mousePosCallback(long window, double xPos, double yPos) {
        if(get().mouseButtonDown > 0) {
            get().isDragging = true;
        }

        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().lastWorldX = get().worldX;
        get().lastWorldY = get().worldY;
        get().xPos = xPos;
        get().yPos = yPos;
        calcOrthoX();
        calcOrthoY();
    }

    /**
     * @param window window reference
     * @param button mouse button action is performed on
     * @param action what the mouse action is, e.g. press/release
     * @param mods   modifier keys, e.g. ctrl or shift
     */
    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().mouseButtonDown++;
            if (button < get().mouseButtonPressed.length)
                get().mouseButtonPressed[button] = true;
        } else if (action == GLFW_RELEASE) {
            get().mouseButtonDown--;
            if (button < get().mouseButtonPressed.length) {
                get().mouseButtonPressed[button] = false;
                get().isDragging = false;
            }
        }
    }

    /**
     * @param window  window reference
     * @param xOffset new xOffset
     * @param yOffset new yOffset
     */
    public static void mouseScrollCallback(long window, double xOffset, double yOffset) {
        get().scrollX = xOffset;
        get().scrollY = yOffset;
    }

    /**
     * Updates after every frame
     */
    public static void endFrame() {
        get().scrollX = 0;
        get().scrollY = 0;
        get().lastX = get().xPos;
        get().lastY = get().yPos;
        get().lastWorldX = get().worldX;
        get().lastWorldY = get().worldY;
    }

    // Static getters and setters for single MouseListener Instance

    /**
     * Get MouseListener x-position
     *
     * @return xPos
     */
    public static float getX() {
        return (float) get().xPos;
    }

    /**
     * Get MouseListener y-position
     *
     * @return yPos
     */
    public static float getY() {
        return (float) get().yPos;
    }

    /**
     * Get MouseListener change in x-position
     *
     * @return dX
     */
    public static float getDx() {
        return (float) (get().lastX - get().xPos);
    }

    /**
     * Get MouseListener change in y-position
     *
     * @return dY
     */
    public static float getDy() {
        return (float) (get().lastY - get().yPos);
    }

    /**
     * Get MouseListener change in world x-position
     * @return dX
     */
    public static float getWorldDx() {
        return  (float)(get().lastWorldX-get().worldX);
    }

    /**
     * Get MouseListener change in world y-position
     * @return dY
     */
    public static float getWorldDy() {
        return  (float)(get().lastWorldY-get().worldY);
    }

    /**
     * Get MouseListener scroll in x-axis
     *
     * @return scrollX
     */
    public static float getScrollX() {
        return (float) get().scrollX;
    }

    /**
     * Get MouseListener scroll in y-axis
     *
     * @return scrollY
     */
    public static float getScrollY() {
        return (float) get().scrollY;
    }

    /**
     * Get MouseListener dragging
     *
     * @return isDragging
     */
    public static boolean isDragging() {
        return get().isDragging;
    }

    /**
     * Set game viewport position
     * @param gameViewportPos
     */
    public static void setGameViewportPos(Vector2f gameViewportPos) {
        get().gameViewportPos.set(gameViewportPos);
    }

    /**
     * Set game viewport size
     * @param gameViewportSize
     */
    public static void setGameViewportSize(Vector2f gameViewportSize) {
        get().gameViewportSize.set(gameViewportSize);
    }

    /**
     * Checks if a bytton is pressed
     *
     * @param button Button to check if pressed
     * @return true if pressed
     */
    public static boolean mouseButtonDown(int button) {
        return get().mouseButtonPressed[button]; // Could check for IndexOutOfBoundsException, but won't for possible debugging scenarios
    }

    /**
     * Get mouse x-position in world coordinates
     * @return
     */
    public static float getOrthoX() {
        return (float) get().worldX;
    }

    /**
     * Get mouse y-position in world coordinates
     * @return
     */
    public static float getOrthoY() {
        return (float) get().worldY;
    }

    private static void calcOrthoX() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (currentX / get().gameViewportSize.x) * 2.0f - 1.0f;
        Vector4f tmp = new Vector4f(currentX, 0, 0, 1);

        Camera camera = Window.getScene().getCamera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseViewMat().mul(camera.getInverseProjectionMat(), viewProjection);
        tmp.mul(viewProjection);
        get().worldX = tmp.x;
    }

    private static void calcOrthoY() {
        float currentY = getY() - get().gameViewportPos.y;
        currentY = -((currentY / get().gameViewportSize.y) * 2.0f - 1.0f);
        Vector4f tmp = new Vector4f(0, currentY, 0, 1);

        Camera camera = Window.getScene().getCamera();
        Matrix4f viewProjection = new Matrix4f();
        camera.getInverseViewMat().mul(camera.getInverseProjectionMat(), viewProjection);
        tmp.mul(viewProjection);
        get().worldY = tmp.y;
    }

    /**
     * Returns mouse x-position on screen (viewport)
     * @return
     */
    public static float getScreenX() {
        float currentX = getX() - get().gameViewportPos.x;
        currentX = (currentX / get().gameViewportSize.x) * (float) GameConstants.SCREEN_WIDTH;


        return currentX;
    }

    /**
     * Returns mouse y-position on screen (viewport)
     * @return
     */
    public static float getScreenY() {
        float currentY = getY() - get().gameViewportPos.y;
        currentY =  (float) GameConstants.SCREEN_HEIGHT -((currentY / get().gameViewportSize.y) * (float) GameConstants.SCREEN_HEIGHT);

        return currentY;
    }
}
