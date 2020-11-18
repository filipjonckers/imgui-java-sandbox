import imgui.ImColor;
import imgui.ImGuiViewport;
import imgui.flag.*;
import imgui.internal.ImGui;

public class MapsWindow {
    private final static int WINDOW_WIDTH = 400;
    private final static int WINDOW_HEIGHT = 300;
    private final static String MAP_GROUP_ENABLED = "#22682d";
    private final static String BRIGHTNESS_FRAMEBG = "#333333";
    private int[] brt1 = {80};
    private int[] brt2 = {100};
    private int[] brt3 = {70};
    private int[] brt4 = {90};

    public enum MapGroup {
        ACC("ACC", true),
        APP("APP", true),
        EBBR("EBBR", true),
        EBAW("EBAW", false),
        EBCI("EBCI", false),
        EBLG("EBLG", false),
        EBOS("EBOS", false),
        FIC("FIC", true);

        private final String name;
        private boolean state;

        MapGroup(String name, boolean state) {
            this.name = name;
            this.state = state;
        }
    }

    public MapsWindow() {

    }

    public void render() {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGui.setNextWindowSize(WINDOW_WIDTH, WINDOW_HEIGHT, ImGuiCond.Once);
        ImGui.setNextWindowPos(mainViewport.getWorkPosX() + 10, mainViewport.getWorkPosY() + 10, ImGuiCond.Once);

        ImGui.begin("Maps");

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
        ImGui.columns(6, "MapGroups2", false);
        boolean popColorRequired;
        for (MapGroup group : MapGroup.values()) {
            if (group.state) {
                ImGui.pushStyleColor(ImGuiCol.Button, ImColor.rgbToColor(MAP_GROUP_ENABLED));
                popColorRequired = true;
            } else {
                popColorRequired = false;
            }
            if (ImGui.button(group.name, -1, 0f)) { // same width - default height
                group.state = !group.state;
            }
            if (popColorRequired) {
                ImGui.popStyleColor();
            }
            ImGui.nextColumn();
        }
        ImGui.columns(1);
        ImGui.separator();
        ImGui.spacing();


        ImGui.beginChild("MapSelection", 0, 0, false, ImGuiWindowFlags.AlwaysVerticalScrollbar);

        boolean active;
        active = ImGui.collapsingHeader("GEO", ImGuiTreeNodeFlags.AllowItemOverlap);
        ImGui.sameLine(200);
        ImGui.setNextItemWidth(-20);
        ImGui.pushStyleColor(ImGuiCol.FrameBg, ImColor.rgbToColor(BRIGHTNESS_FRAMEBG));
        ImGui.sliderInt("##brt1", brt1, 60, 100);
        ImGui.popStyleColor();
        if (active) {
            ImGui.checkbox("Border", true);
            ImGui.checkbox("Coast", true);
            ImGui.checkbox("Rivers", false);
        }

        active = ImGui.collapsingHeader("AERODROMES", ImGuiTreeNodeFlags.AllowItemOverlap);
        ImGui.sameLine(200);
        ImGui.setNextItemWidth(-20);
        ImGui.pushStyleColor(ImGuiCol.FrameBg, ImColor.rgbToColor(BRIGHTNESS_FRAMEBG));
        ImGui.sliderInt("##brt2", brt2, 60, 100);
        ImGui.popStyleColor();
        if (active) {
            ImGui.checkbox("Major aerodromes", true);
            ImGui.checkbox("Small aerodromes", false);
            ImGui.checkbox("Heliports", false);
            ImGui.checkbox("MIL airports", false);
        }

        active = ImGui.collapsingHeader("NAV", ImGuiTreeNodeFlags.AllowItemOverlap);
        ImGui.sameLine(200);
        ImGui.setNextItemWidth(-20);
        ImGui.pushStyleColor(ImGuiCol.FrameBg, ImColor.rgbToColor(BRIGHTNESS_FRAMEBG));
        ImGui.sliderInt("##brt3", brt3, 60, 100);
        ImGui.popStyleColor();
        if (active) {
            ImGui.checkbox("VOR", true);
            ImGui.checkbox("Major points", true);
            ImGui.checkbox("Other points", false);
            ImGui.checkbox("Lower airways", true);
            ImGui.checkbox("Upper airways", false);
        }

        active = ImGui.collapsingHeader("RUNWAY", ImGuiTreeNodeFlags.AllowItemOverlap);
        ImGui.sameLine(200);
        ImGui.setNextItemWidth(-20);
        ImGui.pushStyleColor(ImGuiCol.FrameBg, ImColor.rgbToColor(BRIGHTNESS_FRAMEBG));
        ImGui.sliderInt("##brt4", brt4, 60, 100);
        ImGui.popStyleColor();
        if (active) {
            ImGui.checkbox("EBBR RWY 25L/R centerlines", true);
            ImGui.checkbox("EBBR RWY 19 centerline", true);
            ImGui.checkbox("EBBR RWY 01 centerline", true);
            ImGui.checkbox("EBBR RWY 07L VOR/DME centerline", false);
            ImGui.checkbox("EBBR RWY 07L RNP centerline", false);
            ImGui.checkbox("EBBR RWY 07R RNP centerline", false);
        }

        ImGui.endChild();
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
