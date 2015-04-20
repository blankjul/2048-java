package de.julz.game.view;

import de.julz.game.model.Board;


/**
 * General View interface for showing the current board and getting the next Action.
 */
public interface View {
	
	abstract void update(Board board);
	
}
