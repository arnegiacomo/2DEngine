package no.arnemunthekaas.engine.entities;

import no.arnemunthekaas.engine.entities.components.Component;
import no.arnemunthekaas.engine.renderer.Transform;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private String name;
    private List<Component> components;
    public Transform transform;

    /**
     * Create new Game Object with given name
     *
     * @param name Game Object name
     */
    public GameObject(String name) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = new Transform();
    }

    /**
     * Create new Game Object with given name and transform
     *
     * @param name Game Object name
     * @param transform Game Object Transform
     */
    public GameObject(String name, Transform transform) {
        this.name = name;
        this.components = new ArrayList<>();
        this.transform = transform;
    }

    /**
     * Return a reference to a Component in Game Object, specified by given components class.
     *
     * @param componentClass Class for component in question
     * @param <T>
     * @return Component reference in Game Object
     */
    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try {
                    return componentClass.cast(c);
                } catch ( ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }

        return null;
    }

    /**
     * Removes a given component in Game Object given its class.
     *
     * @param componentClass Class for component in question
     * @param <T>
     */
    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i = 0; i < components.size(); i++) {
            Component c = components.get(i);
            if(componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    /**
     * Add component to game object
     *
     * @param c component to add
     */
    public void addComponent(Component c) {
        this.components.add(c);
        c.gameObject = this;
    }

    /**
     * Update all components
     *
     * @param dt delta time
     */
    public void update(float dt) {
        components.forEach(c -> c.update(dt));
    }

    /**
     * Start all components
     */
    public void start() {
        components.forEach(Component::start);
    }
}
