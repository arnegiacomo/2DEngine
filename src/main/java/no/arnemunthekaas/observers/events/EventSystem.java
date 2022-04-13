package no.arnemunthekaas.observers.events;

import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.observers.Observer;
import no.arnemunthekaas.observers.events.Event;

import java.util.ArrayList;
import java.util.List;

public class EventSystem {

    private static List<Observer> observers= new ArrayList<>();

    /**
     *
     * @param obs
     */
    public static void addObserver(Observer obs) {
        observers.add(obs);
    }

    /**
     *
     * @param obj
     * @param evt
     */
    public static void notify(GameObject obj, Event evt) {
        for(Observer obs : observers)
            obs.onNotify(obj, evt);
    }
}
