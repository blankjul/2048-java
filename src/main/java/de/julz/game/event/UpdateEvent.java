package de.julz.game.event;

import de.julz.game.model.GameState;

public class UpdateEvent extends Event {
	
	private GameState state;
	
	public UpdateEvent(GameState state) {
		this.state = state;
	}

	public GameState getGame() {
		return state;
	}
	
	public String toString() {
        return String.format("Event to Update UI to \n%s", state.getBoard().toString());
    }
	
	
}
