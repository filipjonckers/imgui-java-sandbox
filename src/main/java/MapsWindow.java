import imgui.ImGuiViewport;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiTabBarFlags;
import imgui.flag.ImGuiTreeNodeFlags;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;

public class MapsWindow {
    private int[] b = {50};

    public MapsWindow() {

    }

    public void render() {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGui.setNextWindowSize(600, 300, ImGuiCond.Once);
        ImGui.setNextWindowPos(mainViewport.getWorkPosX() + 10, mainViewport.getWorkPosY() + 10, ImGuiCond.Once);

        ImGui.begin("Maps", ImGuiWindowFlags.AlwaysAutoResize);

        if (ImGui.beginTabBar("Maps", ImGuiTabBarFlags.None)) {
            if (ImGui.beginTabItem("MAPS")) {
                loadMapsTab();
                ImGui.endTabItem();
            }
            if (ImGui.beginTabItem("TSA")) {
                loadTsaTab();
                ImGui.endTabItem();
            }
            if (ImGui.beginTabItem("LOA")) {
                loadLoaTab();
                ImGui.endTabItem();
            }
            if (ImGui.beginTabItem("DEBUG")) {
                loadDebugTab();
                ImGui.endTabItem();
            }
            ImGui.endTabBar();
        }

        ImGui.end();
    }

    private void loadMapsTab() {
        ImGui.button("ACC");
        ImGui.sameLine();
        ImGui.button("APP");
        ImGui.sameLine();
        ImGui.button("EBBR");
        ImGui.sameLine();
        ImGui.button("EBAW");
        ImGui.sameLine();
        ImGui.button("EBCI");
        ImGui.sameLine();
        ImGui.button("EBLG");
        ImGui.sameLine();
        ImGui.button("EBOS");
        ImGui.sameLine();
        ImGui.button("FIC");
        ImGui.sameLine();

        ImGui.spacing();

        if (ImGui.collapsingHeader("GEO", ImGuiTreeNodeFlags.AllowItemOverlap)) {
            ImGui.sameLine(200);
            ImGui.sliderInt("", b, 0, 100);
            ImGui.checkbox("Border", true);
            ImGui.checkbox("Coast", true);
            ImGui.checkbox("Rivers", false);
        }
        if (ImGui.collapsingHeader("AERODROMES", ImGuiTreeNodeFlags.AllowItemOverlap)) {
            ImGui.sameLine(200);
            ImGui.sliderInt("", b, 0, 100);
            ImGui.checkbox("Major aerodromes", true);
            ImGui.checkbox("Small aerodromes", false);
            ImGui.checkbox("Heliports", false);
            ImGui.checkbox("MIL airports", false);
        }
        if (ImGui.collapsingHeader("NAV", ImGuiTreeNodeFlags.AllowItemOverlap)) {
            ImGui.sameLine(200);
            ImGui.sliderInt("", b, 0, 100);
            ImGui.checkbox("VOR", true);
            ImGui.checkbox("Major points", true);
            ImGui.checkbox("Other points", false);
            ImGui.checkbox("Lower airways", true);
            ImGui.checkbox("Upper airways", false);
        }

        if (ImGui.collapsingHeader("RUNWAY", ImGuiTreeNodeFlags.AllowItemOverlap)) {
            ImGui.sameLine(200);
            ImGui.sliderInt("", b, 0, 100);
            ImGui.checkbox("EBBR RWY 25L/R centerlines", true);
            ImGui.checkbox("EBBR RWY 19 centerline", true);
            ImGui.checkbox("EBBR RWY 01 centerline", true);
            ImGui.checkbox("EBBR RWY 07L VOR/DME centerline", false);
            ImGui.checkbox("EBBR RWY 07L RNP centerline", false);
            ImGui.checkbox("EBBR RWY 07R RNP centerline", false);
        }
    }

    private void loadTsaTab() {
        ImGui.text("TSA tab");
    }

    private void loadLoaTab() {
        ImGui.text("LOA tab");
    }

    private void loadDebugTab() {
        ImGui.text("DEBUG tab");
    }
}
