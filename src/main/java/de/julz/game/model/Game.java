package de.julz.game.model;

import java.util.HashSet;
import java.util.Set;

public class Game {

	// the current board
	private Board board;


	public Game() {
		// create the new board
		board = new Board();
		// set two fields non empty at the beginning
		board.setRandomPositionNonEmpty();
		board.setRandomPositionNonEmpty();
	}

	public Game(Board board) {
		this.board = board;
	}

	public boolean isFinished() {
		return getPossibleMoves().isEmpty();
	}

	public Board getBoard() {
		return board;
	}

	/**
	 * Performs one action. This includes the move and the new random field.
	 * Returns true if there is a next move and the game is not finished.
	 */
	public boolean next(Action action) {

		// perform the move
		this.board = move(this.board, action);
		board.setRandomPositionNonEmpty();

		return !isFinished();
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
	 * Move the current board to the up, right, down or left.
	 */
	public Board move(Board src, Action action) {

		Board dest = new Board();
		traverseForMove(src, dest, action);

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
						lastValue = 0;
						// move left
					} else {
						dest.set(i, index, board.get(i, j));
						lastValue = board.get(i, j);
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

		return dest;
	}

	public Set<Action> getPossibleMoves() {
		// else set calculate the values by looking at movements of all moves.
		Set<Action> nextMoves = new HashSet<Action>();
		for (Action action : Action.values()) {
			if (!move(board, action).equals(this.board))
				nextMoves.add(action);
		}
		return nextMoves;
	}


}
