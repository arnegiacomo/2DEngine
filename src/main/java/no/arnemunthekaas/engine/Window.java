package no.arnemunthekaas.engine;

import no.arnemunthekaas.engine.eventlisteners.KeyListener;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;
import no.arnemunthekaas.engine.imgui.ImGuiLayer;
import no.arnemunthekaas.engine.renderer.DebugDraw;
import no.arnemunthekaas.engine.scenes.LevelEditorScene;
import no.arnemunthekaas.engine.scenes.LevelScene;
import no.arnemunthekaas.engine.scenes.Scene;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import java.awt.*;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

/**
 *  https://www.lwjgl.org/guide
 */
public class Window {
    private int width, height;
    private String title = "2D Engine";
    private long glfwWindow;
    private ImGuiLayer imGuiLayer;

    public float r;
    public float g;
    public float b;
    private float a;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.width = 1920;
        this.height = 1080;
        this.title = "Window";
        this.r = 1;
        this.g = 1;
        this.b = 1;
        this.a = 1;
    }

    /**
     * Changes the scene, based on the Scene Index. 0 = LevelEditorScene. 1 = LevelScene.
     *
     * @param newScene SceneIndex
     */
    public static void changeScene(int newScene) {
        switch (newScene) {
            case 0:
                currentScene = new LevelEditorScene();

                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
                break;
        }

        currentScene.load();
        currentScene.init();
        currentScene.start();
    }

    /**
     * Returns the window object, if the window = null, it creates a new window.
     *
     * @return Window object
     */
    public static Window get() {
        if (Window.window == null)
            Window.window = new Window();

        return Window.window;
    }

    /**
     * Get current window Scene.
     * @return Scene object
     */
    public static Scene getScene() {
        return currentScene;
    }

    /**
     * Get window width
     * @return width
     */
    public static int getWidth() { return get().width;
    }

    /**
     * Get window height
     * @return height
     */
    public static int getHeight() { return get().height;
    }

    /**
     * Set new width
     * @param width new width
     */
    public static void setWidth(int width) {
        get().width = width;
    }

    /**
     * Set new height
     * @param height new height
     */
    public static void setHeight(int height) {
        get().height = height;
    }

    /**
     * Runs the LWJGL window
     */
    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free memory
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        // Setup an error callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW
        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW.");

        // Configure GLFW
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);

        // macOS
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        // Create the window
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL)
            throw new IllegalStateException("Failed to create GLFW window.");

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyListener::keyCallBack);

        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            Window.setWidth(newWidth);
            Window.setHeight(newHeight);
        });

        // Make the OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_ONE, GL_ONE_MINUS_SRC_ALPHA);

        this.imGuiLayer = new ImGuiLayer(glfwWindow);
        this.imGuiLayer.initImGui();

        Window.changeScene(0);
    }

    private void loop() {
        float beginTime = (float) glfwGetTime();
        float endTime;
        float dt = - 1.0f;

        while (!glfwWindowShouldClose(glfwWindow)) {
            // Poll events
            glfwPollEvents();

            DebugDraw.beginFrame();

            glClearColor(r, g, b, a);
            glClear(GL_COLOR_BUFFER_BIT);

            if(dt >= 0) {
                DebugDraw.draw();
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);

            endTime = (float) glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;
        }
        currentScene.saveExit();

    }

}
