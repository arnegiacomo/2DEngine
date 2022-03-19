package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.entities.Component;
import no.arnemunthekaas.engine.entities.GameObject;

public class FontRenderer extends Component {

    @Override
    public void start() {
       if (gameObject.getComponent(SpriteRenderer.class) != null)
           System.out.println("Found font renderer!");
    }

    @Override
    public void update(float dt) {

    }
}
