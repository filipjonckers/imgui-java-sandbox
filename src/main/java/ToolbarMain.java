import imgui.ImGuiViewport;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;

public class ToolbarMain {
    public ToolbarMain() {
    }

    public void render() {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        // ImGui.setNextWindowSize(0, 0, ImGuiCond.Once);
        ImGui.setNextWindowPos(mainViewport.getWorkPosX(), mainViewport.getWorkPosY() + 300, ImGuiCond.Once);

        ImGui.begin("Main Toolbar", ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoDecoration);
        ImGui.button("QUIT", -1, 0);
        ImGui.button("FULLSCREEN", -1, 0);
        ImGui.button("SETTINGS", -1, 0);
        ImGui.button("DEBUG", -1, 0);
        ImGui.button("SRC", -1, 0);
        ImGui.button("LABEL", -1, 0);
        ImGui.button("MAPS", -1, 0);
        ImGui.button("BRT", -1, 0);
        ImGui.button("QDM", -1, 0);
        ImGui.button("FIM", -1, 0);
        ImGui.end();
    }
}
