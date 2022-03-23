package no.arnemunthekaas.engine.entities.components;

import no.arnemunthekaas.engine.entities.GameObject;

public abstract class Component {

    public GameObject gameObject;

    /**
     * Update component with delta time
     *
     * @param dt Delta time
     */
    public void update(float dt) {

    }

    /**
     * Start component
     */
    public void start() {

    }

    /**
     *
     */
    public void imgui() {
    }
}
