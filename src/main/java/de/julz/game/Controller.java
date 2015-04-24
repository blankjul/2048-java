package de.julz.game;

import java.util.Set;

import de.julz.game.event.CloseEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.event.GameOverEvent;
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
		GAME_OVER, PLAYING, START_NEW_GAME
	}

	public KeyInput input = null;
	private Game game = null;
	private MainFrame view = null;
	private Resources resources;
	private Status status;

	private Controller() {

		input = new KeyInput();
		resources = new Resources();
		resources.load();

		if (Game2048.visual) {
			view = new MainFrame();
			view.addKeyListener(input);
			EventDispatcher.getInstance().register(view);
		}

		status = Status.START_NEW_GAME;
	}

	public void start() {

		// register controller as client
		EventDispatcher.getInstance().register(this);

		while (true) {
			
			if (status == Status.START_NEW_GAME) {
				
				game = new Game(Game2048.player);
				status = Status.PLAYING;
				EventDispatcher.getInstance().notify(new UpdateEvent(game.getState(), resources.getScore()));
				
			} else if (status == Status.GAME_OVER) {
				
				view.waitForPaint();
				continue;
				
			} else if (status == Status.PLAYING) {
				
				GameState state = game.getState().copy();
				Set<Action> nextMoves = state.getPossibleMoves();
				Action a = Game2048.player.next(state.copy(), nextMoves);

				// check if this move is allowed
				if (!nextMoves.contains(a)) continue;

				// get the next game state
				game.next(a);
				GameState next = game.getState();
				

				// if the state change
				if (!state.equals(next)) {
					// set best score
					resources.setScore(Math.max(resources.getScore(), game.getScore()));
					// fire an update event
					EventDispatcher.getInstance().notify(new UpdateEvent(game.getState(), resources.getScore()));
				}

				// save the last state
				game.setCurrentState(next);
				
				// set to game over if this is the case
				if (game.isFinished()) status = Status.GAME_OVER;
				
			}
		}

	}


	public void handle(Event event) {
		if (event instanceof GameOverEvent) {
			status = Status.GAME_OVER;
		} else if (event instanceof NewGameEvent) {
			status = Status.START_NEW_GAME;
		}
		else if (event instanceof CloseEvent) {
			resources.serialize();
			System.exit(0); 
		}
	}
	


}
