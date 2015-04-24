package de.julz.game.ai;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.Board;
import de.julz.game.model.GameState;
import de.julz.game.model.Position;

public class Util {
	
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

}
