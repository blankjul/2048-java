package de.julz.game.model;


public class Game {

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
		return board.getPossibleMoves().isEmpty();
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
		this.board = board.move(action);
		board.setRandomPositionNonEmpty();
		
		// check all the possible next moves
		
		
		return isFinished();
	}
	

}
