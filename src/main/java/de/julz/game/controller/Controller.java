package de.julz.game.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.event.ActionEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.event.GameOverEvent;
import de.julz.game.event.NewGameEvent;
import de.julz.game.event.UpdateEvent;
import de.julz.game.model.Action;
import de.julz.game.model.Game;
import de.julz.game.view.ui.ArrayKeyAdapter;
import de.julz.game.view.ui.MainFrame;

public class Controller implements EventListener {

	private static final Controller singleton = new Controller();
	private final static Logger LOGGER = Logger.getLogger(Controller.class.getName());

	public static Controller getInstance() {
		return singleton;
	}

	private Game game = null;
	private MainFrame view = null;
	private AbstractPlayer player = null;

	
	
	private Controller() {
		game = new Game();
		view = new MainFrame(game.getBoard());
		if (player == null) view.addKeyListener(new ArrayKeyAdapter());

		
	}

	public void start() {
		LOGGER.log(Level.INFO, "Started Game and waiting for ActionEvents");
		EventDispatcher.getInstance().register(view);
		EventDispatcher.getInstance().register(this);
		
		while (player != null && !game.isFinished()) {
			Action a = player.next(game.getBoard());
			EventDispatcher.getInstance().notify(new ActionEvent(a));
			try {
				Thread.sleep(400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} 
		}
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
