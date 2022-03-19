package no.arnemunthekaas.engine.entities;

public abstract class Component {

    public GameObject gameObject;

    /**
     * Update component with delta time
     *
     * @param dt Delta time
     */
    public abstract void update(float dt);

    /**
     * Start component
     */
    public void start() {

    }

}
