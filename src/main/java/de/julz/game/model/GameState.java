package de.julz.game.model;

public class GameState {
	
	private Board board;
	
	private int score;
	
	private Action lastAction;
	
	public GameState(Board board, int score, Action lastAction) {
		super();
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

	
}
