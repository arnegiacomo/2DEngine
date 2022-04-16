package no.arnemunthekaas.editor;

import imgui.ImGui;
import imgui.flag.ImGuiTreeNodeFlags;
import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.entities.GameObject;

import java.util.List;

public class SceneHierarchyWindow {

    public void imgui() {
        ImGui.begin("Scene Hierarchy");

        List<GameObject> gameObjects = Window.getScene().getGameObjects();
        int index = 0;
        for(GameObject obj : gameObjects) {

            if(!obj.doSerialization())
                continue;

            ImGui.pushID(index);
            boolean treeNodeOpen = ImGui.treeNodeEx(obj.name,
                    ImGuiTreeNodeFlags.DefaultOpen | ImGuiTreeNodeFlags.FramePadding |
                    ImGuiTreeNodeFlags.OpenOnArrow | ImGuiTreeNodeFlags.SpanAvailWidth,
                    obj.name);

            ImGui.popID();

            if(treeNodeOpen)
                ImGui.treePop();

            index++;
        }
        ImGui.end();
    }
}
