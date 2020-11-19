package archived;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class KeyboardListener {
    private boolean[] keyPressedState;

    private static KeyboardListener instance;
    private static final int MAX_KEYS = 350; // maximum number of keys available in LWJGL

    private KeyboardListener() {
        keyPressedState = new boolean[MAX_KEYS];
    }

    public static KeyboardListener getInstance() {
        if (KeyboardListener.instance == null) {
            KeyboardListener.instance = new KeyboardListener();
        }
        return instance;
    }

    public static void keyboardCallback(long window, int key, int scancode, int action, int mods) {
        if (key > getInstance().keyPressedState.length) {
            return;
        }

        switch (action) {
            case GLFW_PRESS:
                getInstance().keyPressedState[key] = true;
                break;
            case GLFW_RELEASE:
                getInstance().keyPressedState[key] = false;
                break;
        }
    }

    public static boolean isKeyPressed(int key) {
        if (key > getInstance().keyPressedState.length) {
            return false;
        }
        return getInstance().keyPressedState[key];
    }
}
