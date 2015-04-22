package de.julz.game.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * This class represents a game state which is completely immutable.
 * 
 * You can get a new game state by executing the methods move(Action a),
 * next(Action a) and setRandomPositionNonEmpty().
 *
 */
public class GameState {

	/**
	 * The current board at this state
	 */
	final private Board board;

	/**
	 * The current store of the player
	 */
	final private int score;

	/**
	 * The last action that was executed
	 */
	final private Action lastAction;

	/**
	 * private random generator
	 */
	final private static Random rand = new Random();

	/**
	 * Create a new GameState with an empty board.
	 */
	public GameState() {
		this(new Board());
	}

	/**
	 * Create a new GameState with a specific board.
	 * 
	 * @param board
	 *            to consist in this state
	 */
	public GameState(Board board) {
		this.board = board;
		this.score = 0;
		this.lastAction = null;
	}

	/**
	 * Create a GameState with values for a specific situation.
	 * 
	 * @param board
	 *            board to consist in this state
	 * @param score
	 *            current score
	 * @param lastAction
	 *            last action which were executed
	 */
	public GameState(Board board, int score, Action lastAction) {
		this.board = board;
		this.score = score;
		this.lastAction = lastAction;
	}

	/**
	 * @return the current board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @return the current score
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return the last executed action
	 */
	public Action getLastAction() {
		return lastAction;
	}

	/**
	 * Return all possible moves in this state which will lead new GameState.
	 * Action.NIL will not exist in the result set, because you always will have
	 * the same GameState when you executed it.
	 * 
	 * @return Set of action that could be executed on the board of the current
	 *         state
	 */
	public Set<Action> getPossibleMoves() {
		// else set calculate the values by looking at movements of all moves.
		Set<Action> nextMoves = new HashSet<Action>();
		for (Action action : Action.values()) {
			if (action != Action.NIL && !move(action).getBoard().equals(this.board))
				nextMoves.add(action);
		}
		return nextMoves;
	}

	/**
	 * Performs one action. This includes the move and the new random field.
	 * Returns true if there is a next move and the game is not finished.
	 */
	public GameState next(Action action) {
		// perform the move
		GameState state = move(action).setRandomPositionNonEmpty();
		return state;
	}

	/**
	 * Move the current board to the up, right, down or left.
	 * 
	 * @param action
	 *            which gives the direction to what direction the board should
	 *            be moved.
	 * @return the next GameState after this move
	 */
	public GameState move(Action action) {

		Board src = this.board;
		Board dest = new Board();
		
		traverseForMove(src, action);
		traverseForMove(dest, action);

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
		traverseForMove(src, action);
		traverseForMove(dest, action);

		return new GameState(dest, score, action);
	}

	
	
	/**
	 * Also creates a new GameState by setting one field random for 0.9 to 2 and for 0.1 to 4.
	 * If the board has no empty field the is no new GameState and the object itself is returned.
	 * 
	 * @return a new GameState with one more random value.
	 */
	public GameState setRandomPositionNonEmpty() {

		Board b = new Board(board.getArray());

		List<Position> emptyFields = new ArrayList<Position>(b.getEmptyFields());
		if (emptyFields.isEmpty())
			return this;
		
		int index = rand.nextInt(emptyFields.size());
		Position pos = emptyFields.get(index);

		int nextValue = rand.nextFloat() < 0.9 ? 1 : 2;
		b.set(pos.X(), pos.Y(), nextValue);
		return new GameState(b, score, lastAction);
	}
	

	// traverse for the move action
	private void traverseForMove(Board board, Action action) {
		// traverse the board temporarily that it is always a move to the left
		if (action == Action.UP || action == Action.DOWN) board.transpose();
		if (action == Action.RIGHT || action == Action.DOWN)board.invert();
	}

}
