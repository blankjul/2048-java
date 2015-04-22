package de.julz.game.event;

import de.julz.game.model.GameState;

public class UpdateEvent extends Event {
	
	private GameState state;
	
	private int bestScore;
	
	public UpdateEvent(GameState state, int bestScore) {
		this.state = state;
		this.bestScore = bestScore;
	}

	public GameState getGame() {
		return state;
	}
	
	public String toString() {
        return String.format("Event to Update UI to \n%s", state.getBoard().toString());
    }

	public int getBestScore() {
		return bestScore;
	}
	
	
}
