package de.julz.game.ai.evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.Board;
import de.julz.game.model.GameState;
import de.julz.game.model.Position;


public class ExpectationEvaluation extends Evaluation{
	
	private int maxDepth;
	
	private Evaluation eval;
	

	public static double getExpectation(GameState state, Action a, int depth, Evaluation eval) {
		
		// get all possible next moves for this state
		Map<GameState, Double> nextStates = ExpectationEvaluation.allNextStates(state, a);

		// calculate the expectation for this action
		double expectation = 0;
		for (Map.Entry<GameState, Double> entry : nextStates.entrySet()) {
			GameState probState = entry.getKey();
			double prob = entry.getValue();
			double evalScore = 0;
			if (depth == 1) evalScore += eval.getScore(probState);
			else {
				for (Action nextAction : probState.getPossibleMoves()) {
					evalScore += getExpectation(probState, nextAction, depth-1, eval);
				}
			}
			expectation += evalScore * prob;
		}
		
		return expectation;
	}
	
	
	public static Map<GameState, Double> allNextStates(GameState state) {
		Map<GameState, Double> result = new HashMap<GameState, Double>();

		// for each new spawning position
		Set<Position> emptyFields = state.getBoard().getEmptyFields();
		for (Position pos : emptyFields) {

			// add the possible state if it's a 2
			int[][] data = state.getBoard().getArray();
			data[pos.X()][pos.Y()] = 1;
			result.put(new GameState(new Board(data),state.getScore(), state.getLastAction()), (1 / (double) emptyFields.size()) * 0.9);

			// add the possible state if it's a 4
			data = state.getBoard().getArray();
			data[pos.X()][pos.Y()] = 2;
			result.put(new GameState(new Board(data),state.getScore(), state.getLastAction()), (1 / (double) emptyFields.size()) * 0.1);
		}
		return result;
	}

	public static Map<GameState, Double> allNextStates(GameState state, Action a) {
		state = state.createNextGameState(a);
		return allNextStates(state);
	}


	@Override
	public double getScore(GameState state) {
		return ExpectationEvaluation.getExpectation(state, lastAction, maxDepth, eval);
	}


	public int getMaxDepth() {
		return maxDepth;
	}


	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}


	public Evaluation getEval() {
		return eval;
	}


	public void setEval(Evaluation eval) {
		this.eval = eval;
	}

}
