package de.julz.game.model;

import java.util.concurrent.Callable;

public class Game implements Callable<GameState>{

	// the current board
	private GameState currentState;

	private AbstractPlayer player;
	

	public Game() {
		reset();
	}

	public Game(AbstractPlayer player) {
		this();
		this.player = player;
	}

	public Game(Board board) {
		currentState = new GameState(board);
	}

	public void reset() {
		Board b = new Board();
		currentState = new GameState(b);
		currentState.setRandomPositionNonEmpty();
		currentState.setRandomPositionNonEmpty();
	}

	public Board getBoard() {
		return currentState.getBoard();
	}

	public GameState getState() {
		return currentState;
	}

	public void setCurrentState(GameState currentState) {
		this.currentState = currentState;
	}

	public int getScore() {
		return currentState.getScore();
	}

	public boolean isFinished() {
		return !currentState.hastNextState();
	}

	public void next(Action action) {
		currentState.next(action);
	}

	public GameState play() {
		while (!isFinished()) {
			Action a = player.next(currentState, currentState.getPossibleMoves());
			currentState.next(a);
		}
		return currentState;
	}

	public GameState call() throws Exception {
		return play();
	}
	
	
}
