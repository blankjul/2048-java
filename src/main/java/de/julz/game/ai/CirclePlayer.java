package de.julz.game.ai;

import java.util.Set;

import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public class CirclePlayer extends AbstractPlayer {

	int counter = 0;

	@Override
	public Action next(GameState state, Set<Action> actions) {
		Action next = Action.values()[counter++ % 4];
		if (!actions.contains(next)) next = Action.NIL;
		return next;
	}
	
	

}
