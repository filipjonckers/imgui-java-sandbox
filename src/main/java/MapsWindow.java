import imgui.ImGuiViewport;
import imgui.flag.ImGuiCond;
import imgui.internal.ImGui;

public class MapsWindow {
    public MapsWindow() {

    }

    public void render() {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGui.setNextWindowSize(600, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(mainViewport.getWorkPosX() + 10, mainViewport.getWorkPosY() + 10, ImGuiCond.Once);

        ImGui.begin("Maps");

        ImGui.end();
    }
}
