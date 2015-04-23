package de.julz.game.ai.evaluation;

import de.julz.game.model.GameState;

public interface Evaluation {
	
	double getScore(GameState state);

}
