package de.julz.game.model;

import java.util.Set;

public abstract class AbstractPlayer {

	public abstract Action next(GameState state, Set<Action> actions);
	
}
