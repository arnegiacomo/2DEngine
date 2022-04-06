package no.arnemunthekaas.engine.entities.components.gizmos;

import no.arnemunthekaas.engine.editor.PropertiesWindow;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;

public class ScaleGizmo extends Gizmo {

    /**
     * 
     * @param scaleSprite
     * @param propertiesWindow
     */
    public ScaleGizmo(Sprite scaleSprite, PropertiesWindow propertiesWindow) {
        super(scaleSprite, propertiesWindow);
    }

    @Override
    public void update(float dt) {


        if(activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.scale.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeGameObject.transform.scale.y -= MouseListener.getWorldDy();
            }
        }

        super.update(dt);
    }
}
