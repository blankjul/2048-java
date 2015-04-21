package de.julz.game.model;


/**
 * This is the Action which could be chosen at each game step and
 * has to be returned by the game AI.
 */
public enum Action {
	UP, RIGHT, DOWN, LEFT;
	
	public Action fromInt(int value) {
		return Action.values()[value];
	}
	
}
