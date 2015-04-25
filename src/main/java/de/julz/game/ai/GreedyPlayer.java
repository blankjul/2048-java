package de.julz.game.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.julz.game.ai.evaluation.ExpectiMaxEvaluation;
import de.julz.game.ai.evaluation.PathEvaluation;
import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public class GreedyPlayer extends AbstractPlayer {
	
	private ExpectiMaxEvaluation eval;
	
	public GreedyPlayer() {
		 eval = new ExpectiMaxEvaluation();
		 eval.setMaxDepth(4);
		 eval.setEval(new PathEvaluation());
	}

	@Override
	public Action next(GameState state, Set<Action> actions) {
		
		List<Action> l = new ArrayList<Action>(actions);
		int index = 0;
		double maxScore = Double.NEGATIVE_INFINITY;
		
		for (int i = 0; i < l.size(); i++) {
			Action a = l.get(i);
			GameState next = state.copy();
			next.move(a);
			// GameState next = state.createNextGameState(a);
			double currentScore = eval.getScore(next);
			if (currentScore > maxScore) {
				index = i;
				maxScore = currentScore;
			}
		}
		return l.get(index);


	}
	
	

}
