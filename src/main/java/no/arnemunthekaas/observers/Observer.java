package no.arnemunthekaas.observers;

import no.arnemunthekaas.engine.entities.GameObject;
import no.arnemunthekaas.observers.events.Event;

public interface Observer {


    void onNotify(GameObject obj, Event evt);
}
