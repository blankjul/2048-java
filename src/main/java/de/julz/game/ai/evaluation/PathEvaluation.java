package de.julz.game.ai.evaluation;

import java.util.HashSet;
import java.util.Set;

import de.julz.game.model.Board;
import de.julz.game.model.GameState;

public class PathEvaluation extends Evaluation {

	public static Set<GameState> getOptimalStates(GameState state) {

		Set<GameState> result = new HashSet<GameState>();
		int countNonEmptyTiles = state.getBoard().getNonEmptyFields().size();

		int[][] array = new int[Board.FIELD_SIZE][Board.FIELD_SIZE];

		int counter = countNonEmptyTiles;
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++, --counter) {
				if (counter == 0)
					break;
				array[i][j] = counter;
			}
		}

		result.add(new GameState(new Board(array), state.getScore(), state.getLastAction()));
		return result;
	}

	public static double getDistance(Board b1) {

		double[][] weights = new double[][] { { 10, 8, 7, 6.5 }, { .5, .7, 1, 3 }, { -.5, -1.5, -1.8, -2 }, { -3.8, -3.7, -3.5, -3 } };

		int result = 0;
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				double value = Math.pow(2, b1.get(i, j)) * weights[i][j];
				result += value;
			}
		}
		return result;
	}

	public static double getGeomtricWeighted(Board b, Board weights) {
		double result = 0;
		
		int lastValue = -1;
		boolean perfect = true;
		
		int i = Board.FIELD_SIZE - 1;
		for (int j = Board.FIELD_SIZE - 1; j >= 0; j--) {
			if (b.get(i, j) == 0) return result;
			double exp = (lastValue - 1 == b.get(i, j) && perfect) ? 4 : 2;
			perfect = exp != 2;
			double value = b.get(i, j) * Math.pow(exp, weights.get(i, j));
			result += value;
			lastValue = b.get(i, j);
		}
		
		i = Board.FIELD_SIZE - 2;
		for (int j = 0; j < Board.FIELD_SIZE; j++) {
			if (b.get(i, j) == 0) return result;
			double exp = (lastValue - 1 == b.get(i, j) && perfect) ? 4 : 2;
			perfect = exp != 2;
			double value = b.get(i, j) * Math.pow(exp, weights.get(i, j));
			result += value;
			lastValue = b.get(i, j);
		}
		
		i = Board.FIELD_SIZE - 2;
		for (int j = Board.FIELD_SIZE - 1; j >= 0; j--) {
			if (b.get(i, j) == 0) return result;
			double exp = (lastValue - 1 == b.get(i, j) && perfect) ? 4 : 2;
			perfect = exp != 2;
			double value = b.get(i, j) * Math.pow(exp, weights.get(i, j));
			result += value;
			lastValue = b.get(i, j);
		}
		
		i = Board.FIELD_SIZE - 3;
		for (int j = 0; j < Board.FIELD_SIZE; j++) {
			if (b.get(i, j) == 0) return result;
			double exp = (lastValue - 1 == b.get(i, j) && perfect) ? 4 : 2;
			perfect = exp != 2;
			double value = b.get(i, j) * Math.pow(exp, weights.get(i, j));
			result += value;
			lastValue = b.get(i, j);
		}
		
		return result;
	}

	@Override
	public double getScore(GameState state) {
		if (!state.hastNextState())
			return 0;
		Board weights1 = Board.fromJSON("[[3,2,1,0]," + "[4,5,6,7]," + "[11,10,9,8]," + "[12,13,14,15]]");
		double value1 = PathEvaluation.getGeomtricWeighted(state.getBoard(), weights1) * state.getBoard().getEmptyFields().size();

		Board weights2 = Board.fromJSON("[[3,4,11,12]," + "[2,5,10,13]," + "[1,6,9,14]," + "[0,7,8,15]]");
		double value2 = PathEvaluation.getGeomtricWeighted(state.getBoard(), weights2) * state.getBoard().getEmptyFields().size();

		double result = Math.max(value1, value2);

		return result;
	}

}
