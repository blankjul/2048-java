package de.julz.game.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board {

	/**
	 * The Field size is always four which means there are 16 fields.
	 */
	final public static int FIELD_SIZE = 4;

	/**
	 * The 3D array which contains all the values.
	 */
	private int[][] board = new int[FIELD_SIZE][FIELD_SIZE];

	/**
	 *  boolean if the board transposition is enabled
	 */
	private boolean transpose = false;

	/**
	 * boolean if the board is mirror inverted or not
	 */
	private boolean inverted = false;
	
	
	/**
	 * Create an empty board.
	 */
	public Board() {
	}

	/**
	 * Create a board with predefined values.
	 * @param board that is initialized
	 */
	public Board(int[][] board) {
		this();
		setArray(board);
	}

	/**
	 * Returns the value at the specific column and row and considers if the
	 * board is transposed or inverted.
	 */
	public int get(int row, int column) {
		if (inverted)
			column = Math.abs(column - 3);
		if (transpose) {
			int tmp = row;
			row = column;
			column = tmp;
		}
		return board[row][column];
	}

	/**
	 * Sets the value at the column or position and considers if the board is
	 * transposed or inverted.
	 */
	protected void set(int row, int column, int value) {
		if (inverted)
			column = Math.abs(column - 3);
		if (transpose) {
			int tmp = row;
			row = column;
			column = tmp;
		}
		board[row][column] = value;
	}


	protected void transpose() {
		transpose = !transpose;
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



	protected Set<Position> getFields(FilterInterface filterFunction) {
		Set<Position> emptyFields = new HashSet<Position>();
		for (int i = 0; i < FIELD_SIZE; i++) {
			for (int j = 0; j < FIELD_SIZE; j++) {
				if (filterFunction.filter(this.get(i, j)))
					emptyFields.add(new Position(i, j));
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

	@Override
	public boolean equals(Object other) {
		if (other == null)
			return false;
		if (other == this)
			return true;
		if (!(other instanceof Board))
			return false;
		Board board = (Board) other;
		return Arrays.deepEquals(getArray(), board.getArray());
	}

}
