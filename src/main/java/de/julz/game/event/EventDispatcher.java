package de.julz.game.event;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;


public class EventDispatcher {
	
	
	// singleton structure
	private static final EventDispatcher singleton = new EventDispatcher();
	private EventDispatcher() {}
	public static EventDispatcher getInstance() {return singleton;};
	
	private Set<EventListener> listeners = new HashSet<EventListener>();
	
	private final static Logger LOGGER = Logger.getLogger(EventDispatcher.class.getName());
	
	public void notify(Event event) {
		LOGGER.log(Level.INFO, "Dispatching event " + event.toString());
		for (EventListener listener : listeners) {
			listener.handle(event);
		}
	}
	
	public boolean register(EventListener listener) {
		return listeners.add(listener);
	}
	
	public boolean remove(EventListener listener) {
		return listeners.remove(listener);
	}
	

	
}
