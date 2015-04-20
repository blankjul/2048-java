package de.julz.game.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import de.julz.game.event.ActionEvent;
import de.julz.game.model.Game;
import de.julz.game.view.View;
import de.julz.game.view.ui.MainFrame;

public class Controller {

	private static final Controller singleton = new Controller();
	private final static Logger LOGGER = Logger.getLogger(Controller.class
			.getName());

	public static Controller getInstance() {
		return singleton;
	}

	Game g = new Game();
	View v = new MainFrame(g.getBoard());;

	private Controller() {
	}

	public void start() {

		LOGGER.log(Level.INFO, "Started Game and waiting for ActionEvents");
		while (!g.isFinished()) {
		}

	}

	public void notify(ActionEvent e) {
		LOGGER.log(Level.INFO, "Execute Action: " + e.getAction());
		g.next(e.getAction());
		v.update(g.getBoard());
		LOGGER.log(Level.INFO, "\n" + g.getBoard().toString());
	}

}
