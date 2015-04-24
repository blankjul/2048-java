package de.julz.game.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.julz.game.ai.evaluation.Evaluation;
import de.julz.game.ai.evaluation.ScoreEvalution;
import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public class NoHardCodePlayer extends AbstractPlayer {

	private static final int NUM_RUNS = 10;
	
	private Evaluation eval = new ScoreEvalution();
	
	@Override
	public Action next(GameState state, Set<Action> actions) {
		
		double bestScore = -1;
		Action result = Action.NIL;
		
		// for all moves
		for (Action a : actions) {
			
			// initialize values
			int score = 0;
			
			// multiRandomRun
			for (int i = 0; i < NUM_RUNS; i++) {
				
				// randomRun -> perform the first move
				GameState nextState = state.createNextGameState(a);
				
				// roll out randomly to the end
				while (nextState.hastNextState()) {
					
					// get a random next action
					List<Action> list = new ArrayList<Action>(nextState.getPossibleMoves());
					int index = new Random().nextInt(list.size());
					Action nextAction = list.get(index);
					
					nextState = nextState.createNextGameState(nextAction);
				}
				score += eval.getScore(nextState);
				//score += nextState.getScore();
				
			}
			
			// look if we have a new best average
			double avg = score / NUM_RUNS;
			if (avg > bestScore) {
				result = a;
				bestScore = avg;
			}
		}
		
		return result;

	}

}