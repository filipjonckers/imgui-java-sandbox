package archived;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {
    private double scrollX;
    private double scrollY;
    private double x;
    private double y;
    private double lastX;
    private double lastY;
    private boolean[] mouseButtonPressed;
    private boolean isDragging;

    private static MouseListener instance;

    private static final int MAX_MOUSE_BUTTONS = 3;

    private MouseListener() {
        this.scrollX = 0;
        this.scrollY = 0;
        this.x = 0;
        this.y = 0;
        this.lastX = 0;
        this.lastY = 0;
        this.mouseButtonPressed = new boolean[MAX_MOUSE_BUTTONS];
    }

    private static MouseListener getInstance() {
        if (MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }
        return MouseListener.instance;
    }

    public static void mousePositionCallback(long window, double x, double y) {
        getInstance().lastX = getInstance().x;
        getInstance().lastY = getInstance().y;
        getInstance().x = x;
        getInstance().y = y;

        // detect dragging: mouse moving AND any button pressed
        getInstance().isDragging = false;
        for (int i = 0; i < getInstance().mouseButtonPressed.length; i++) {
            if (getInstance().mouseButtonPressed[i]) {
                getInstance().isDragging = true;
                break;
            }
        }
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        if (button > getInstance().mouseButtonPressed.length) {
            // ignore if button number is outside our supported range
            return;
        }

        switch (action) {
            case GLFW_PRESS:
                getInstance().mouseButtonPressed[button] = true;
                break;
            case GLFW_RELEASE:
                getInstance().mouseButtonPressed[button] = false;
                getInstance().isDragging = false;
                break;
        }
    }

    public static void mouseScrollCallback(long window, double offsetX, double offsetY) {
        getInstance().scrollX = offsetX;
        getInstance().scrollY = offsetY;
    }

    public static void endFrame() {
        getInstance().scrollX = 0;
        getInstance().scrollY = 0;
        getInstance().lastX = getInstance().x;
        getInstance().lastY = getInstance().y;
    }

    public static float getX() {
        return (float) getInstance().x;
    }

    public static float getY() {
        return (float) getInstance().y;
    }

    public static float getDeltaX() {
        return (float) (getInstance().lastX - getInstance().x);
    }

    public static float getDeltaY() {
        return (float) (getInstance().lastY - getInstance().y);
    }

    public static float getScrollX() {
        return (float) getInstance().scrollX;
    }

    public static float getScrollY() {
        return (float) getInstance().scrollY;
    }

    public static boolean isDragging() {
        return getInstance().isDragging;
    }

    public static boolean isMouseButtonDown(final int button) {
        if (button < getInstance().mouseButtonPressed.length) {
            return getInstance().mouseButtonPressed[button];
        }
        return false;
    }
}
