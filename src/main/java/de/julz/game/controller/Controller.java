package de.julz.game.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.ai.HumanPlayer;
import de.julz.game.event.ActionEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.event.GameOverEvent;
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

	private Game game;
	private MainFrame view;
	private AbstractPlayer player;
	private boolean isHumanPlayer = false;

	private Controller() {
		game = new Game();
		player = new HumanPlayer();
		isHumanPlayer = player instanceof HumanPlayer;
		
		view = new MainFrame(game.getBoard());
		if (isHumanPlayer) { 
			view.addKeyListener(new ArrayKeyAdapter());
		}
		
		EventDispatcher.getInstance().register(view);
		
		

	}

	public void start() {
		LOGGER.log(Level.INFO, "Started Game and waiting for ActionEvents");
		EventDispatcher.getInstance().register(this);
		
		while (!isHumanPlayer && !game.isFinished()) {
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
		if (event instanceof ActionEvent) {
			ActionEvent actionEvent = (ActionEvent) event;
			boolean hasNextMove = game.next(actionEvent.getAction());
			if (!hasNextMove) {
				EventDispatcher.getInstance().notify(new GameOverEvent(game));
			}
			else EventDispatcher.getInstance().notify(new UpdateEvent(game));
		} 
	}

}
