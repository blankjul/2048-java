package de.julz.game.ai.evaluation;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Set;
import java.util.TreeSet;

import de.julz.game.model.Board;
import de.julz.game.model.GameState;

public class SnakeEvaluation extends Evaluation {

	@Override
	public double getScore(GameState state) {
		
		Board optimal = getOptimalState(state).getBoard();
		Board b = state.getBoard();
		double result = 0;
		
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				if (i == 1 || i == 3) {
					j = Math.abs(j - 3);
				}
				int value = b.get(i,j);
				if (value != 0 && value == optimal.get(i, j)) result++;
				else return result;
			}
		}
		
		return result;
	}

	public static GameState getOptimalState(GameState state) {

		Set<Integer> set = new TreeSet<Integer>();
		Board b = state.getBoard();
		int[][] result = new int[Board.FIELD_SIZE][Board.FIELD_SIZE];

		// hash all the value
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				set.add(b.get(i, j));
			}
		}

		LinkedList<Integer> l = new LinkedList<Integer>(set);
		Collections.reverse(l);

		// hash all the value
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				if (l.isEmpty()) break;
				if (i == 1 || i == 3) {
					j = Math.abs(j - 3);
				}
				result[i][j] = l.poll();
			}
		}

		return new GameState(new Board(result));
	}

}
