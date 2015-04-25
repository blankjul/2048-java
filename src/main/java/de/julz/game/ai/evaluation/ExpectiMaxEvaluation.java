package de.julz.game.ai.evaluation;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.Board;
import de.julz.game.model.GameState;
import de.julz.game.model.Position;

public class ExpectiMaxEvaluation extends Evaluation {

	private int maxDepth = 1;

	private Evaluation eval = new ScoreEvalution();

	public static double expectiminmax(GameState state, int depth, Evaluation eval, boolean me) {
		
		if (!state.hastNextState()) return -10000000;
		else if (depth == 0) return eval.getScore(state);
			
		if (!me) {
			double alpha = Double.POSITIVE_INFINITY;
			for (Map.Entry<GameState, Double> entry : ExpectiMaxEvaluation.allNextStates(state).entrySet()) {
				GameState probState = entry.getKey();
				double prob = entry.getValue();
				double value = prob * expectiminmax(probState, depth - 1, eval, true);
				if (value < alpha)
					alpha = value;
			}
			return alpha;
		} else {
			double alpha = Double.NEGATIVE_INFINITY;
			for (Action a : state.getPossibleMoves()) {
				GameState probState = state.copy();
				probState.move(a);
				double value = expectiminmax(probState, depth - 1, eval, false);
				if (value > alpha)
					alpha = value;
			}
			return alpha;
		}
	}

	public static double getExpectation(GameState state, int depth, Evaluation eval) {
		return expectiminmax(state, depth, eval, false);
	}
	


	public static Map<GameState, Double> allNextStates(GameState state) {
		Map<GameState, Double> result = new HashMap<GameState, Double>();

		// for each new spawning position
		Set<Position> emptyFields = state.getBoard().getEmptyFields();
		for (Position pos : emptyFields) {

			// add the possible state if it's a 2
			int[][] data = state.getBoard().getArray();
			data[pos.X()][pos.Y()] = 1;
			result.put(new GameState(new Board(data), state.getScore(), state.getLastAction()), (1 / (double) emptyFields.size()) * 0.9);

			// add the possible state if it's a 4
			data = state.getBoard().getArray();
			data[pos.X()][pos.Y()] = 2;
			result.put(new GameState(new Board(data), state.getScore(), state.getLastAction()), (1 / (double) emptyFields.size()) * 0.1);
		}
		return result;
	}

	public static Map<GameState, Double> allNextStates(GameState state, Action a) {
		state = state.createNextGameState(a);
		return allNextStates(state);
	}

	@Override
	public double getScore(GameState state) {
		return ExpectiMaxEvaluation.getExpectation(state, maxDepth, eval);
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
