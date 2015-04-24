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

	public static int getDistance(Board b1, Board b2) {
		int result = 0;
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				int value = b1.get(i, j) - b2.get(i, j);
				result += (value > 0) ? value : 0;
			}
		}
		return Math.abs(result);
	}

	

	@Override
	public double getScore(GameState state) {
		int min = Integer.MAX_VALUE;
		for (GameState optimal : getOptimalStates(state)) {
			int distance = PathEvaluation.getDistance(optimal.getBoard(), state.getBoard());
			if (distance < min) {
				min = distance;
			}
		}
		return -min;
	}

}
