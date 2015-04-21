package de.julz.game.event;

import de.julz.game.model.Action;

public class ActionEvent extends Event {
	
	private Action action;
	
	public ActionEvent(Action a) {
		this.action = a;
	}

	public Action getAction() {
		return action;
	}
	
	public String toString() {
        return String.format("Action %s", action.toString());
    }
	
}
