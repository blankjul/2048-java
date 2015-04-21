package de.julz.game.event;

import de.julz.game.model.Game;

public class GameOverEvent extends UpdateEvent {
	
	public GameOverEvent(Game game) {
		super(game);
	}
	
}
