package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.Window;
import no.arnemunthekaas.engine.camera.Camera;
import no.arnemunthekaas.engine.renderer.DebugDraw;
import no.arnemunthekaas.engine.utils.GameConstants;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class GridLines extends Component{

    // TODO: Fix camera pan zoom and top right corner new lines
    @Override
    public void update(float dt) {
        Camera camera = Window.getScene().getCamera();
        Vector2f cameraPos = camera.position;
        Vector2f projectionSize = camera.getProjectionSize();

        int firstX = ((int) (cameraPos.x / GameConstants.GRID_WIDTH) - 1) * GameConstants.GRID_HEIGHT;
        int firstY = ((int) (cameraPos.y / GameConstants.GRID_HEIGHT) - 1) * GameConstants.GRID_WIDTH;

        int verticalLines = (int) (projectionSize.x * camera.getZoom()/ GameConstants.GRID_WIDTH) + 2;
        int horizontalLines = (int) (projectionSize.y * camera.getZoom()/ GameConstants.GRID_HEIGHT) + 2;

        int width = (int) (projectionSize.x * camera.getZoom()) + GameConstants.GRID_WIDTH * 2;
        int height = (int) (projectionSize.y * camera.getZoom()) + GameConstants.GRID_HEIGHT * 2;

        int maxlines = Math.max(verticalLines, horizontalLines);

        Vector3f color = new Vector3f(0.2f, 0.2f, 0.2f);

        for(int i = 0; i <= maxlines; i++) {
            int x = firstX + (GameConstants.GRID_WIDTH * i);
            int y = firstY + (GameConstants.GRID_HEIGHT * i);

            if(i < horizontalLines)
                DebugDraw.addLine2D(new Vector2f(firstX, y), new Vector2f(firstX + width, y), color);


            if(i < verticalLines)
                DebugDraw.addLine2D(new Vector2f(x, firstY), new Vector2f(x, firstY + height), color);

        }

    }
}
