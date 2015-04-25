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

	public static double getGeomtricWeighted(Board b1) {
	Board weights = Board.fromJSON( "[[3,2,1,0],"
							     + "[4,5,6,7],"
							     + "[11,10,9,8],"
							     + "[12,13,14,15]]");
		int result = 0;
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				double value = Math.pow(2,b1.get(i, j)) * Math.pow(2, weights.get(i, j));
				result += value;
			}
		}
		return result;
	}

	@Override
	public double getScore(GameState state) {
		return PathEvaluation.getGeomtricWeighted(state.getBoard()) * state.getBoard().getEmptyFields().size();
	}

}
