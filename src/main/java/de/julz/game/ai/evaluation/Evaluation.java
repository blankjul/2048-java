package de.julz.game.ai.evaluation;

import de.julz.game.model.Action;
import de.julz.game.model.GameState;


public abstract class Evaluation implements Comparable<Evaluation>{
	
	protected GameState state;
	
	protected Action lastAction;
	
	public abstract double getScore(GameState state);


	public int compareTo(Evaluation o) {
		return ((Double)getScore(state)).compareTo(o.getScore(state));
	}

	
	public GameState getState() {
		return state;
	}

	public Action getLastAction() {
		return lastAction;
	}

	public void setState(GameState state) {
		this.state = state;
	}

	public void setLastAction(Action lastAction) {
		this.lastAction = lastAction;
	}

}
