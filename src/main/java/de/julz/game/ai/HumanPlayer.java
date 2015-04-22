package de.julz.game.ai;

import java.util.Set;

import de.julz.game.Controller;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;
import de.julz.game.view.ui.KeyInput;

public class HumanPlayer extends AbstractPlayer {


	@Override
	public Action next(GameState state, Set<Action> actions) {
		KeyInput input = Controller.getInstance().input;
		Action next = input.getAction();
		input.reset();
		return next;
	}
	
	

}
