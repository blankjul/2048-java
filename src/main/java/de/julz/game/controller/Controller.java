package de.julz.game.controller;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import de.julz.game.Game2048;
import de.julz.game.ai.AbstractPlayer;
import de.julz.game.event.ActionEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.event.GameOverEvent;
import de.julz.game.event.NewGameEvent;
import de.julz.game.event.UpdateEvent;
import de.julz.game.model.Game;
import de.julz.game.view.ui.ArrayKeyAdapter;
import de.julz.game.view.ui.MainFrame;

public class Controller implements EventListener {

	private static final Controller singleton = new Controller();

	
	public static Controller getInstance() {
		return singleton;
	}

	private Game game = null;
	private MainFrame view = null;
	private AbstractPlayer player;

	private Controller() {
		player = Game2048.player;
		game = new Game(player, Game2048.visual);
		
		if (Game2048.visual) {
			view = new MainFrame(game.getBoard());
			EventDispatcher.getInstance().register(view);
			if (player == null) view.addKeyListener(new ArrayKeyAdapter());
		}
	}
	
	private void initLogger() {
		if (!Game2048.logging) {
			Logger log = LogManager.getLogManager().getLogger("");
			for (Handler h : log.getHandlers()) {
				h.setLevel(Level.OFF);
			}
		}
	}

	public void start() {
		initLogger();
		EventDispatcher.getInstance().register(this);
		
		do  {
			if (player != null) {
				game.play();
			}
		} while (Game2048.visual);
		
	}
		

	public void handle(Event event) {
		if (event instanceof NewGameEvent) {
			game.reset();
			EventDispatcher.getInstance().notify(new UpdateEvent(game));
		} else if (event instanceof ActionEvent) {
			ActionEvent actionEvent = (ActionEvent) event;
			game.next(actionEvent.getAction());
			EventDispatcher.getInstance().notify(new UpdateEvent(game));
			if (game.isFinished()) {
				EventDispatcher.getInstance().notify(new GameOverEvent());
				EventDispatcher.getInstance().notify(new UpdateEvent(game));
			}
		}
	}

}
