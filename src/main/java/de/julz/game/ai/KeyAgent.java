package de.julz.game.ai;

import de.julz.game.model.Action;
import de.julz.game.model.Board;

public class KeyAgent extends Agent {

	
	@Override
	public Action next(Board board) {
		return Action.LEFT;
	}
	
	

}
