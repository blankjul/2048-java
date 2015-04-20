package de.julz.game.model;

public class Game {
	
	
	final private Board board;
	
	boolean next = true;
	
	public Game() {
		board = new Board();
		board.init();
	}
	
	public boolean isFinished () {
		return !next;
	}

	public Board getBoard() {
		return board;
	}
	
	public void next(Action action) {
		next = board.next(action);
	}
	
}
