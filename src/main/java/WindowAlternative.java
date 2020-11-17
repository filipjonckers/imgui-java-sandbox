import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiIO;
import imgui.flag.ImGuiConfigFlags;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImBoolean;
import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.system.Platform;

import java.util.Objects;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class WindowAlternative {
    private static WindowAlternative instance = null;
    private long window;
    private final ImGuiImplGlfw imGuiGlfw = new ImGuiImplGlfw(); // backend
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3(); // renderer

    private static final int WINDOW_WIDTH = 1024;
    private static final int WINDOW_HEIGHT = 768;
    private static final String WINDOW_TITLE = "IMGUI JWGL Sandbox";
    private static final float[] WINDOW_BACKGROUND_RGBA = {0f, 0f, 0f, 1f};
    private static final int VSYNC = 1;
    private static final String GL_VERSION = "#version 330";

    private final MapsWindow mapsWindow = new MapsWindow();

    private WindowAlternative() {
        // only local instance allowed
    }

    public static WindowAlternative getInstance() {
        if (instance == null) {
            instance = new WindowAlternative();
        }
        return instance;
    }

    public void run() {
        System.out.println("Starting LWJGL " + Version.getVersion());
        initGlfw();
        initImgui();
        loop();
        dispose();
    }

    private void initGlfw() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!glfwInit()) {
            throw new IllegalStateException("Error: cannot initialize GLFW");
        }
        initGlfwWindowHints();
        window = glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, WINDOW_TITLE, NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Error: unable to create GLFW window");
        }
        glfwMakeContextCurrent(window);
        glfwSwapInterval(VSYNC); // enable vsync - lock onto monitor refresh rate
        glfwShowWindow(window);
        GL.createCapabilities();
    }

    private void initImgui() {
        ImGui.createContext();
        ImGui.styleColorsDark();

        final ImGuiIO io = ImGui.getIO();
        io.setIniFilename(null); // don't use ini file
        io.addConfigFlags(ImGuiConfigFlags.NavEnableKeyboard);
        io.addConfigFlags(ImGuiConfigFlags.ViewportsEnable); // enable imgui windows outside main window
        io.setConfigViewportsNoTaskBarIcon(true);
        initImguiFonts(io);

        imGuiGlfw.init(window, true);
        imGuiGl3.init(GL_VERSION);
    }

    private void loop() {
        FrameTimer.initFrameTimer();
        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();

            glClearColor(WINDOW_BACKGROUND_RGBA[0], WINDOW_BACKGROUND_RGBA[1], WINDOW_BACKGROUND_RGBA[2], WINDOW_BACKGROUND_RGBA[3]);
            glClear(GL_COLOR_BUFFER_BIT);

            ImGui.getIO().setDeltaTime(FrameTimer.getDeltaTime());

            imGuiGlfw.newFrame();
            ImGui.newFrame();

            render();

            ImGui.render();
            imGuiGl3.renderDrawData(ImGui.getDrawData());

            if (ImGui.getIO().hasConfigFlags(ImGuiConfigFlags.ViewportsEnable)) {
                final long currentContext = glfwGetCurrentContext();
                ImGui.updatePlatformWindows();
                ImGui.renderPlatformWindowsDefault();
                glfwMakeContextCurrent(currentContext);
            }

            glfwSwapBuffers(window);

            FrameTimer.getMeasurement();
            //System.out.println("FPS: " + FrameTimer.getFps() + " deltaTime: " + FrameTimer.getDeltaTime());
        }
    }

    private void render() {
        // draw everything here !
        mapsWindow.render();

        imgui.internal.ImGui.showDemoWindow(new ImBoolean(true));
    }

    private void initGlfwWindowHints() {
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_FALSE);
        glfwWindowHint(GLFW_DECORATED, GLFW_TRUE);
        glfwWindowHint(GLFW_FLOATING, GLFW_FALSE); // always on top

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
    }

    private void initImguiFonts(ImGuiIO io) {
        final ImFontAtlas fontAtlas = io.getFonts();
        final ImFontConfig fontConfig = new ImFontConfig();
        fontConfig.setGlyphRanges(fontAtlas.getGlyphRangesDefault());
        fontAtlas.addFontDefault();
        fontConfig.setMergeMode(true);
        fontConfig.setPixelSnapH(true);
        fontAtlas.addFontFromFileTTF("fonts/monaco.ttf", 12, fontConfig);
        fontConfig.destroy();
    }

    private void dispose() {
        imGuiGl3.dispose();
        imGuiGlfw.dispose();
        ImGui.destroyContext();
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        Objects.requireNonNull(glfwSetErrorCallback(null)).free();
    }
}
