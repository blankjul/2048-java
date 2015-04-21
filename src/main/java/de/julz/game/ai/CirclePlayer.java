package de.julz.game.ai;

import de.julz.game.model.Action;
import de.julz.game.model.Board;

public class CirclePlayer extends AbstractPlayer {

	int counter = 0;

	@Override
	public Action next(Board board) {
		return Action.values()[counter++ % 4];
	}
	
	

}
