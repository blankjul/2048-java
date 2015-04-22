package de.julz.game;

import de.julz.game.ai.AbstractPlayer;
import de.julz.game.ai.HumanPlayer;

public class Game2048 {

	/**
     * Milliseconds allowed per controller action.
     */
    public static int ACTION_TIME = 40;
    
	
	public static boolean visual = true;
	
	public static int visualDelay = 10;
	
	public static AbstractPlayer player = new HumanPlayer();

	public static void main(String[] args) {
		Controller.getInstance().start();
	}

}
