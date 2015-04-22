package de.julz.game;

import java.util.Set;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.event.CloseEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.event.NewGameEvent;
import de.julz.game.event.UpdateEvent;
import de.julz.game.model.Action;
import de.julz.game.model.Game;
import de.julz.game.model.GameState;
import de.julz.game.view.ui.KeyInput;
import de.julz.game.view.ui.MainFrame;

public class Controller implements EventListener {

	private static final Controller singleton = new Controller();

	public static Controller getInstance() {
		return singleton;
	}

	public static enum Status {
		GAME_OVER, PLAYING, START_NEW_GAME, CLOSE
	}

	public KeyInput input = null;
	private Game game = null;
	private MainFrame view = null;
	private AbstractPlayer player = null;
	private int bestScore;
	private Resources resources;
	private Status status;

	private Controller() {

		input = new KeyInput();

		resources = new Resources();
		resources.load();

		player = Game2048.player;
		game = new Game(player);

		if (Game2048.visual) {
			view = new MainFrame(game.getBoard());
			view.addKeyListener(input);
			EventDispatcher.getInstance().register(view);
		}

		status = Status.PLAYING;

		bestScore = resources.getScore();
	}

	public void start() {

		// register controller as client
		EventDispatcher.getInstance().register(this);

		GameState lastState = game.getCurrentState();
		EventDispatcher.getInstance().notify(new UpdateEvent(game.getCurrentState(), bestScore));

		while (true) {

			if (status == Status.START_NEW_GAME) {
				game = new Game(player);
				status = Status.PLAYING;
				EventDispatcher.getInstance().notify(new UpdateEvent(game.getCurrentState(), bestScore));
			
			} else if (status == Status.PLAYING) {

				Set<Action> nextMoves = lastState.getPossibleMoves();
				Action a = player.next(lastState, nextMoves);

				// check if this move is allowed
				if (!nextMoves.contains(a))
					continue;

				// get the next game state
				GameState state = game.next(a);

				// check if an update event should be fired
				if (!lastState.equals(state)) {
					checkBestScore();
					EventDispatcher.getInstance().notify(new UpdateEvent(game.getCurrentState(), bestScore));
				}

				// save the last state
				game.setCurrentState(state);
				lastState = state;

				// set to game over if this is the case
				if (game.isFinished())
					status = Status.GAME_OVER;
			}
		}

	}

	private void checkBestScore() {
		if (game.getScore() > bestScore) {
			bestScore = game.getScore();
			resources.setScore(bestScore);
		}
	}

	public void handle(Event event) {
		if (event instanceof NewGameEvent) {
			status = Status.START_NEW_GAME;
		}
		else if (event instanceof CloseEvent) {
			resources.serialize();
			System.exit(0); 
		}
	}

	public void resetScore() {
		bestScore = 0;
		resources.setScore(0);
	}

}
