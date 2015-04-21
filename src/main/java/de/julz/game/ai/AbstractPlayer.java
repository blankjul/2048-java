package de.julz.game.ai;

import de.julz.game.model.Action;
import de.julz.game.model.Board;

public abstract class AbstractPlayer {

	public abstract Action next(Board board);
	
}
