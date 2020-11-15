import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Platform;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {
    private int width;
    private int height;
    private String title;

    private static Window window = null;
    private long glfwWindow;
    private ImGuiLayer imguiLayer;

    private Window() {
        this.width = 1024;
        this.height = 768;
        this.title = "IMGUI JWGL Sandbox";
    }

    public static Window get() {
        if (Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public void run() {
        System.out.println("Starting LWJGL " + Version.getVersion());
        init();
        loop();
        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Error: cannot initialize GLFW");
        }

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        glfwWindowHint(GLFW_FLOATING, GLFW_FALSE); // always on top
        glfwWindowHint(GLFW_TRANSPARENT_FRAMEBUFFER, GLFW_TRUE);

        if (Platform.get() == Platform.MACOSX) {
            glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
            glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
            glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
            glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
            glfwWindowHint(GLFW_SCALE_TO_MONITOR, GLFW_TRUE);
            glfwWindowHint(GLFW_COCOA_RETINA_FRAMEBUFFER, GLFW_TRUE); // use full resolution framebuffer on retina display
            glfwWindowHintString(GLFW_COCOA_FRAME_NAME, "LWJGL-IMGUI Sandbox");
            glfwWindowHint(GLFW_COCOA_GRAPHICS_SWITCHING, GLFW_TRUE); // support switching to integrated GPU (improved performance, higher power consumption
        }

        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Error: unable to create GLFW window");
        }

        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePositionCallback);
        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);
        glfwSetKeyCallback(glfwWindow, KeyboardListener::keyboardCallback);
        glfwSetWindowSizeCallback(glfwWindow, (window, width, height) -> Window.setSize(width, height));

        glfwMakeContextCurrent(glfwWindow);
        glfwSwapInterval(1); // enable vsync - lock onto monitor refresh rate
        glfwShowWindow(glfwWindow);
        GL.createCapabilities(); // extremely important !

        this.imguiLayer = new ImGuiLayer(glfwWindow);
        this.imguiLayer.initImGui();
    }

    public void loop() {
        FrameTimer.initFrameTimer();

        while (!glfwWindowShouldClose(glfwWindow)) {
            glfwPollEvents();

            glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (KeyboardListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Event: space pressed");
            }

            if (MouseListener.isMouseButtonDown(GLFW_MOUSE_BUTTON_LEFT)) {
                System.out.println("Event: Left mouse button pressed");
            }

            this.imguiLayer.update(FrameTimer.getFps());

            glfwSwapBuffers(glfwWindow);

            FrameTimer.getMeasurement();
            System.out.println("FPS: " + FrameTimer.getFps());
        }
    }

    public static int getWidth() {
        return get().width;
    }

    public static int getHeight() {
        return get().height;
    }

    public static void setWidth(int width) {
        get().width = width;
    }

    public static void setHeight(int height) {
        get().height = height;
    }

    public static void setSize(int width, int height) {
        Window.setWidth(width);
        Window.setHeight(height);
    }
}
