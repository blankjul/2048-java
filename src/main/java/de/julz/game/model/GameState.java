package de.julz.game.model;

import java.util.HashSet;
import java.util.Set;

public class GameState{
	
	private Board board;
	
	private int score;
	
	private Action lastAction;
	
	public GameState(Board board) {
		this.board = board;
		this.score = 0;
		this.lastAction = null;
	}

	
	public GameState(Board board, int score, Action lastAction) {
		this.board = board;
		this.score = score;
		this.lastAction = lastAction;
	}


	public Board getBoard() {
		return board;
	}

	public int getScore() {
		return score;
	}

	public Action getLastAction() {
		return lastAction;
	}
	
	
	private void traverseForMove(Board src, Board dest, Action action) {
		// traverse the board temporarily that it is always a move to the left
		if (action == Action.UP || action == Action.DOWN) {
			src.transpose();
			dest.transpose();
		}

		if (action == Action.RIGHT || action == Action.DOWN) {
			src.invert();
			dest.invert();
		}
	}
	
	
	/**
	 * Performs one action. This includes the move and the new random field.
	 * Returns true if there is a next move and the game is not finished.
	 */
	public GameState next(Action action) {
		// perform the move
		GameState state = move(action);
		state.getBoard().setRandomPositionNonEmpty();
		return state;
	}


	public Set<Action> getPossibleMoves() {
		// else set calculate the values by looking at movements of all moves.
		Set<Action> nextMoves = new HashSet<Action>();
		for (Action action : Action.values()) {
			if (!move(action).getBoard().equals(this.board))
				nextMoves.add(action);
		}
		return nextMoves;
	}

	
	/**
	 * Move the current board to the up, right, down or left.
	 */
	public GameState move(Action action) {

		Board src = this.board;
		Board dest = new Board();
		traverseForMove(src, dest, action);
		
		int score = this.getScore();

		// for each row
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			int index = 0;
			int lastValue = 0;
			// for each field in that column
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				// move the current value to the last
				if (src.get(i, j) != 0) {
					// merge
					if (src.get(i, j) == lastValue) {
						dest.set(i, index - 1, lastValue + 1);
						score += Math.pow(2, lastValue + 1);
						lastValue = 0;
						// move left
					} else {
						dest.set(i, index, src.get(i, j));
						lastValue = src.get(i, j);
						++index;
					}
				}
			}
			for (int x = index; x < Board.FIELD_SIZE; x++) {
				dest.set(i, x, 0);
			}
		}

		// traverse the board temporarily that it is always a move to the left
		traverseForMove(src, dest, action);

		return new GameState(dest, score, action);
	}




	
}
