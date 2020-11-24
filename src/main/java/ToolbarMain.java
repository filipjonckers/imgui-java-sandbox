import imgui.ImColor;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;
import imgui.type.ImBoolean;

public class ToolbarMain {
    public ToolbarMain() {
    }

    public void render() {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGui.setNextWindowPos(mainViewport.getWorkPosX(), mainViewport.getWorkPosY() + 300, ImGuiCond.Once);
        ImGui.setNextWindowSize(80.0f, 0.0f, ImGuiCond.Once);
        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 5f, 5f);

        ImGui.begin("##Main Toolbar", new ImBoolean(), ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoResize);

        ImGui.getStyle().setFrameRounding(2f);
        ImGui.getStyle().setColor(ImGuiCol.Text, ImColor.rgbToColor(T2Colors.T2_STYLE_600));
        ImGui.getStyle().setColor(ImGuiCol.Button, ImColor.rgbToColor(T2Colors.T2_STYLE_200));
        ImGui.getStyle().setColor(ImGuiCol.ButtonHovered, ImColor.rgbToColor(T2Colors.T2_BLUE_200));

        ImGui.button(FontMaterialDesignIcons.EXIT_TO_APP, -1, 0);
        ImGui.button(FontMaterialDesignIcons.FULLSCREEN, -1, 0);
        ImGui.button(FontMaterialDesignIcons.SETTINGS_APPLICATIONS, -1, 0);
        ImGui.button("DEBUG", -1, 0);
        ImGui.button("SRC", -1, 0);
        ImGui.button("LABEL", -1, 0);
        ImGui.button("MAPS", -1, 0);
        ImGui.button("BRT", -1, 0);
        ImGui.button("QDM", -1, 0);
        ImGui.button("FIM", -1, 0);

        ImGui.end();
        imgui.ImGui.popStyleVar();
    }
}
