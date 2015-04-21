package de.julz.game.ai;

import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public abstract class AbstractPlayer {

	public abstract Action next(GameState state, Set<Action> actions);
	
}
