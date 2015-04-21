package de.julz.game;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.ai.GreedyPlayer;
import de.julz.game.controller.Controller;

public class Game2048 {

	public static boolean visual = true;
	
	public static int visualDelay = 10;
	
	public static boolean logging = true;
	
	public static AbstractPlayer player = new GreedyPlayer();

	public static void main(String[] args) {
		Controller.getInstance().start();
	}

}
