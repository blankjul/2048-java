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
	private Board board;

	/**
	 * The current store of the player
	 */
	private int score;

	/**
	 * The last action that was executed
	 */
	private Action lastAction;

	/**
	 * private random generator
	 */
	private static Random rand = new Random();

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
		if (getPossibleMoves(board,false,false)) nextMoves.add(Action.LEFT);
		if (getPossibleMoves(board,true,false)) nextMoves.add(Action.RIGHT);
		if (getPossibleMoves(board,false,true)) nextMoves.add(Action.UP);
		if (getPossibleMoves(board,true,true)) nextMoves.add(Action.DOWN);
		return nextMoves;
	}

	private boolean getPossibleMoves(Board b, boolean inverted, boolean transposed) {
		// for each row
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			int last = -1;
			// for each field in that column
			for (int j = 0; j < Board.FIELD_SIZE; j++) {
				int value = b.get(i, j, inverted, transposed);
				// if it is a merge (equality but not zero) or there is a value after a zero
				if ((value != 0 && last == value) || (last == 0 && value != 0))
					return true;
				last = value;
			}
		}
		return false;
	}

	
	
	/**
	 * Performs one action. This includes the move and the new random field.
	 * Returns true if there is a next move and the game is not finished.
	 */
	public void next(Action action) {
		lastAction = action;
		if (action != null && action != Action.NIL) {
			move(action);
			setRandomPositionNonEmpty();
		}
	}
	
	
	
	/**
	 * Move the current board to the up, right, down or left. If the Action.Nil
	 * is the parameter the instance itself is returned.
	 * 
	 * @param action
	 *            which gives the direction to what direction the board should
	 *            be moved.
	 * @return the next GameState after this move
	 */
	public void move(Action a) {
		move(this.board,a);
	}
	
	
	private void move(Board board, Action action) {

		if (action == null || action == Action.NIL) return;

		boolean inverted = false;
		boolean transposed = false;

		if (action == Action.UP || action == Action.DOWN)
			transposed = true;
		if (action == Action.RIGHT || action == Action.DOWN)
			inverted = true;

		// for each row
		for (int i = 0; i < Board.FIELD_SIZE; i++) {
			int index = 0;
			int lastValue = 0;
			// for each field in that column
			for (int j = 0; j < Board.FIELD_SIZE; j++) {

				// get the value at the specific field
				int value = board.get(i, j, inverted, transposed);
				board.set(i, j, 0, inverted, transposed);

				// move the current value to the last
				if (value != 0) {
					// merge
					if (value == lastValue) {
						board.set(i, index - 1, lastValue + 1, inverted, transposed);
						score += Math.pow(2, lastValue + 1);
						lastValue = 0;
						// move left
					} else {
						board.set(i, index, value, inverted, transposed);
						lastValue = value;
						++index;
					}
				}
			}
		}
	}
	
	
	public GameState createNextGameState(Action a) {
		GameState nextState = copy();
		nextState.move(a);
		return nextState;
	}
	
	

	/**
	 * Also creates a new GameState by setting one field random for 0.9 to 2 and
	 * for 0.1 to 4.
	 * 
	 * If the board has no empty field the is no new GameState and the object
	 * itself is returned.
	 * 
	 * @return a new GameState with one more random value.
	 */
	public void setRandomPositionNonEmpty() {

		List<Position> emptyFields = new ArrayList<Position>(board.getEmptyFields());
		if (emptyFields.isEmpty())
			return;

		int index = rand.nextInt(emptyFields.size());
		Position pos = emptyFields.get(index);

		int nextValue = rand.nextFloat() < 0.9 ? 1 : 2;
		board.set(pos.X(), pos.Y(), nextValue);
	}
	
	
	/**
	 * Deep copy the whole GameState.
	 */
	public GameState copy() {
		return new GameState(board.copy(), score, lastAction);
	}
	

	
	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof GameState))
			return false;
		GameState state = (GameState) other;
		if (board.equals(state.getBoard()))
			return true;
		else
			return false;
	}

	@Override
	public int hashCode() {
		return board.hashCode();
	}

	public boolean hastNextState() {
		return !getPossibleMoves().isEmpty();
	}

}
