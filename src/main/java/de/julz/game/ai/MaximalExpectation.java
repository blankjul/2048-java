package de.julz.game.ai;

import java.util.Map;
import java.util.Set;

import de.julz.game.ai.evaluation.Evaluation;
import de.julz.game.ai.evaluation.ScoreEvalution;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public class MaximalExpectation extends AbstractPlayer {

	private Evaluation eval = new ScoreEvalution();
	
	private int maxDepth = 4;

	@Override
	public Action next(GameState state, Set<Action> actions) {

		Action result = Action.NIL;
		double bestExpectation = -1;

		// for all moves
		for (Action a : actions) {
			
			double expectation = getExpectation(state, a, maxDepth);
			
			// if we found a new minimum
			if (expectation > bestExpectation) {
				result = a;
				bestExpectation = expectation;
			}
		}
		return result;
	}

	public double getExpectation(GameState state, Action a, int depth) {
		
		// get all possible next moves for this state
		Map<GameState, Double> nextStates = Util.allNextStates(state, a);

		// calculate the expectation for this action
		double expectation = 0;
		for (Map.Entry<GameState, Double> entry : nextStates.entrySet()) {
			GameState probState = entry.getKey();
			double prob = entry.getValue();
			double evalScore = 0;
			if (depth == 1) evalScore += eval.getScore(probState);
			else {
				for (Action nextAction : probState.getPossibleMoves()) {
					evalScore += getExpectation(probState, nextAction, depth-1);
				}
			}
			expectation += evalScore * prob;
		}
		
		return expectation;
		
	}

}
