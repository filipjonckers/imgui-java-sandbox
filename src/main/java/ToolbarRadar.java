import imgui.ImColor;
import imgui.ImGuiViewport;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiCond;
import imgui.flag.ImGuiStyleVar;
import imgui.flag.ImGuiWindowFlags;
import imgui.internal.ImGui;
import imgui.type.ImInt;

import java.util.ArrayList;

public class ToolbarRadar {
    private final String[] LEVELS;

    public ToolbarRadar() {
        ArrayList<String> array = new ArrayList<>();
        for (int i = 0; i < 901; i += 10) {
            array.add(String.format("F%03d", i));
        }
        LEVELS = array.toArray(new String[0]);
    }

    public void render() {
        final ImGuiViewport mainViewport = ImGui.getMainViewport();
        ImGui.setNextWindowPos(mainViewport.getWorkPosX(), mainViewport.getWorkPosY(), ImGuiCond.Once);
        ImGui.pushStyleVar(ImGuiStyleVar.FramePadding, 5f, 5f);

        ImGui.begin("##Radar Toolbar", ImGuiWindowFlags.AlwaysAutoResize | ImGuiWindowFlags.NoTitleBar | ImGuiWindowFlags.NoDecoration | ImGuiWindowFlags.NoResize);

        ImGui.getStyle().setFrameRounding(2f);
        ImGui.getStyle().setColor(ImGuiCol.Text, ImColor.rgbToColor(T2Colors.T2_STYLE_600));
        ImGui.getStyle().setColor(ImGuiCol.Button, ImColor.rgbToColor(T2Colors.T2_STYLE_200));
        ImGui.getStyle().setColor(ImGuiCol.ButtonHovered, ImColor.rgbToColor(T2Colors.T2_BLUE_200));

        ImGui.button("FILTER OFF");
        ImGui.sameLine();
        ImGui.setNextItemWidth(80f);
        ImGui.combo("##Bottom1", new ImInt(1), LEVELS, LEVELS.length);
        ImGui.sameLine();
        ImGui.setNextItemWidth(80f);
        ImGui.combo("##Top1", new ImInt(15), LEVELS, LEVELS.length);
        ImGui.sameLine();
        ImGui.setNextItemWidth(80f);
        ImGui.combo("##Bottom2", new ImInt(1), LEVELS, LEVELS.length);
        ImGui.sameLine();
        ImGui.setNextItemWidth(80f);
        ImGui.combo("##Top2", new ImInt(20), LEVELS, LEVELS.length);
        ImGui.sameLine();
        ImGui.button("DOTS");
        ImGui.sameLine();
        ImGui.button("VEL OTH");
        ImGui.sameLine();
        ImGui.button("30");
        ImGui.sameLine();
        ImGui.button("1");
        ImGui.sameLine();
        ImGui.button("2");
        ImGui.sameLine();
        ImGui.button("3");
        ImGui.sameLine();
        ImGui.button("4");

        ImGui.end();
        ImGui.popStyleVar();
    }
}
