package de.julz.game.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Board {

	/**
	 * The Field size is always four which means there are 16 fields.
	 */
	final public static int FIELD_SIZE = 4;
	
	/**
	 * private random generator
	 */
	final private static Random rand = new Random();

	/**
	 * The 3D array which contains all the values.
	 */
	private int[][] board = new int[FIELD_SIZE][FIELD_SIZE];

	// boolean if the board transposition is enabled
	private boolean transpose = false;

	// boolean if the board is mirror inverted or not
	private boolean inverted = false;
	
	
	public Board() {}

	public Board(int[][] board) {
		this();
		setArray(board);
	}
	
	public void init() {
		// set two fields non empty at the beginning
		setRandomPositionNonEmpty();
		setRandomPositionNonEmpty();
	}

	/**
	 * Returns the value at the specific column and row
	 * and considers if the board is transposed or inverted.
	 */
	protected int get(int row, int column) {
		if (inverted) column = Math.abs(column - 3);
		if (transpose) {
			int tmp = row;
			row = column;
			column = tmp;
		}
		return board[row][column];
	}

	/**
	 * Sets the value at the column or position
	 * and considers if the board is transposed or inverted.
	 */
	protected void set(int row, int column, int value) {
		if (inverted) column = Math.abs(column - 3);
		if (transpose) {
			int tmp = row;
			row = column;
			column = tmp;
		}
		board[row][column] = value;
	}

	protected boolean isTransposed() {
		return transpose;
	}

	protected void transpose() {
		transpose = !transpose;
	}

	protected boolean isInverted() {
		return inverted;
	}

	protected void invert() {
		inverted = !inverted;
	}

	/**
	 * Returns the array representation of the board.
	 */
	public int[][] getArray() {
		int[][] result = new int[FIELD_SIZE][FIELD_SIZE];
		for (int i = 0; i < FIELD_SIZE; i++) {
			for (int j = 0; j < FIELD_SIZE; j++) {
				result[i][j] = this.get(i, j);
			}
		}
		return result;
	}

	/**
	 * Set the underlying array with values. Check if the size is correct.
	 */
	private boolean setArray(int[][] board) {
		if (board.length != FIELD_SIZE || board[0].length != FIELD_SIZE)
			return false;
		this.board = board;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < FIELD_SIZE; i++) {
			for (int j = 0; j < FIELD_SIZE; j++) {
				sb.append(board[i][j]);
				sb.append(" ");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

	/**
	 * Move the current board to the up, right, down or left.
	 */
	public void move(Action action) {
		// traverse the board temporarily that it is always a move to the left
		if (action == Action.UP || action == Action.DOWN) transpose();
		if (action == Action.RIGHT || action == Action.DOWN) invert();

		// for each row
		for (int i = 0; i < FIELD_SIZE; i++) {
			int index = 0;
			int lastValue = 0;
			// for each field in that column
			for (int j = 0; j < FIELD_SIZE; j++) {
				// move the current value to the last
				if (this.get(i, j) != 0) {
					// merge
					if (this.get(i, j) == lastValue) {
						this.set(i, index - 1, lastValue + 1);
						lastValue = lastValue + 1;
						// move left
					} else {
						this.set(i, index, this.get(i, j));
						lastValue = this.get(i, j);
						++index;
					}
				}
			}
			for (int x = index; x < FIELD_SIZE; x++) {
				this.set(i, x, 0);
			}
		}
		// transform it back
		if (action == Action.UP || action == Action.DOWN) transpose();
		if (action == Action.RIGHT || action == Action.DOWN) invert();
	}
	
	
	
	/**
	 * Performs one action. This includes the move and the new random field.
	 */
	public void next(Action action) {
		// perform the move
		move(action);
		
		// switch value of empty position to 1 or 2
		setRandomPositionNonEmpty();
		
	}
	

	private void setRandomPositionNonEmpty() {
		List<Position> emptyFields = new ArrayList<Position>(this.getEmptyFields());
		int index = rand.nextInt(emptyFields.size());
		Position pos = emptyFields.get(index);
		
		int nextValue = rand.nextFloat() < 0.9 ? 1 : 2;
		this.set(pos.X(), pos.Y(), nextValue);
		
	}
	
	
	protected Set<Position> getFields(FilterInterface filterFunction) {
		Set<Position> emptyFields = new HashSet<Position>();
		for (int i = 0; i < FIELD_SIZE; i++) {
			for (int j = 0; j < FIELD_SIZE; j++) {
				if (filterFunction.filter(this.get(i,j))) emptyFields.add(new Position(i, j));
			}
		}
		return emptyFields;
	}
	
	
	public Set<Position> getEmptyFields() { 
		return getFields(new FilterInterface() {
			public boolean filter(int value) {
				return value == 0;
			}
		});
	}
	
	public Set<Position> getNonEmptyFields() { 
		return getFields(new FilterInterface() {
			public boolean filter(int value) {
				return value != 0;
			}
		});
	}
	
	public Set<Position> getAllFields() { 
		return getFields(new FilterInterface() {
			public boolean filter(int value) {
				return true;
			}
		});
	}
	

	private interface FilterInterface {
	    public boolean filter(int value);
	}
	
	

}
