package de.julz.game.ai.evaluation;

import de.julz.game.model.GameState;

public class ScoreEvalution implements Evaluation{

	public double getScore(GameState state) {
		if (!state.hastNextState()) return -1;
		return state.getScore();
	}

}
