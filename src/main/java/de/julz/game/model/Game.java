package de.julz.game.model;

import java.util.concurrent.Callable;

import de.julz.game.ai.AbstractPlayer;

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
		currentState = new GameState(new Board()).setRandomPositionNonEmpty().setRandomPositionNonEmpty();
	}

	public Board getBoard() {
		return currentState.getBoard();
	}

	public GameState getCurrentState() {
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

	public GameState next(Action action) {
		currentState = currentState.next(action);
		return currentState;
	}

	public GameState play() {
		while (!isFinished()) {
			Action a = player.next(getCurrentState(), getCurrentState().getPossibleMoves());
			currentState = currentState.next(a);
		}
		return currentState;
	}

	public GameState call() throws Exception {
		return play();
	}
	
	
}
