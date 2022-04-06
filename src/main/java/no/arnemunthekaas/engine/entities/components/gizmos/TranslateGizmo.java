package no.arnemunthekaas.engine.entities.components.gizmos;

import no.arnemunthekaas.engine.editor.PropertiesWindow;
import no.arnemunthekaas.engine.entities.components.Sprite;
import no.arnemunthekaas.engine.eventlisteners.MouseListener;

public class TranslateGizmo extends Gizmo {

    /**
     *
     * @param arrowSprite
     * @param propertiesWindow
     */
    public TranslateGizmo(Sprite arrowSprite, PropertiesWindow propertiesWindow) {
        super(arrowSprite, propertiesWindow);
    }

    @Override
    public void update(float dt) {


        if(activeGameObject != null) {
            if (xAxisActive && !yAxisActive) {
                activeGameObject.transform.position.x -= MouseListener.getWorldDx();
            } else if (yAxisActive) {
                activeGameObject.transform.position.y -= MouseListener.getWorldDy();
            }
        }

        super.update(dt);
    }


}
