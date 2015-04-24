package de.julz.game.ai.evaluation;

import de.julz.game.model.GameState;

public class EmptyFieldEvalution extends Evaluation{

	

	@Override
	public double getScore(GameState state) {
		return state.getBoard().getEmptyFields().size();
	}

	

}
