package de.julz.game.event;

import de.julz.game.model.Game;

public class UpdateEvent extends Event {
	
	private Game game;
	
	public UpdateEvent(Game game) {
		this.game = game;
	}

	public Game getGame() {
		return game;
	}
	
	public String toString() {
        return String.format("Event to Update UI to \n%s", game.getBoard().toString());
    }
	
	
}
