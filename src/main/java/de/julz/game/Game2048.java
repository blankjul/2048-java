package de.julz.game;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.ai.RandomPlayer;

public class Game2048 {

	/**
     * Milliseconds allowed per controller action.
     */
    public static int ACTION_TIME = 40;
    
	public static boolean visual = true;
	
	public static AbstractPlayer player = new RandomPlayer();

	public static void main(String[] args) {
		Controller.getInstance().start();
	}

}
