package de.julz.game.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.julz.game.event.ActionEvent;
import de.julz.game.event.Event;
import de.julz.game.event.EventDispatcher;
import de.julz.game.event.EventListener;
import de.julz.game.model.Game;
import de.julz.game.view.View;
import de.julz.game.view.ui.MainFrame;

public class Controller implements EventListener{

	private static final Controller singleton = new Controller();
	private final static Logger LOGGER = Logger.getLogger(Controller.class
			.getName());

	public static Controller getInstance() {
		return singleton;
	}

	private Game g;
	private View v;


	private Controller() {
		g = new Game();
		v = new MainFrame(g.getBoard());

	}

	
	public void start() {
		EventDispatcher.getInstance().register(this);
		LOGGER.log(Level.INFO, "Started Game and waiting for ActionEvents");

	}
	


	public void handle(Event event) {
		
		// if there is an ActionEvent make a move with the board and update view
		if (event instanceof ActionEvent) {
			ActionEvent actionEvent = (ActionEvent) event;
			g.next(actionEvent.getAction());
			v.update(g.getBoard());
		}
		
	}

}
