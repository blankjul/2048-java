package de.julz.game.model;

import java.util.concurrent.Callable;

import de.julz.game.Game2048;
import de.julz.game.ai.AbstractPlayer;
import de.julz.game.event.ActionEvent;
import de.julz.game.event.EventDispatcher;

public class Game implements Callable<GameState>{

	// the current board
	private GameState currentState;

	private boolean visual;

	private AbstractPlayer player;

	public Game() {
		reset();
	}

	public Game(AbstractPlayer player, boolean visual) {
		this();
		this.visual = visual;
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

	public void play() {
		while (!isFinished()) {
			Action a = player.next(getCurrentState(), getCurrentState().getPossibleMoves());
			currentState = currentState.next(a);
			if (visual)
				EventDispatcher.getInstance().notify(new ActionEvent(a));
				try {
					Thread.sleep(Game2048.visualDelay);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
		}
	}

	public GameState call() throws Exception {
		play();
		return currentState;
	}
	
	
}
