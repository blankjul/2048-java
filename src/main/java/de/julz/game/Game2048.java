package de.julz.game;

import de.julz.game.model.AbstractPlayer;

public class Game2048 {

	public static boolean visual = true;

	/*
	 * CirclePlayer, ExpectMaxPlayer, NoHardCodePlayer, GreedyPlayer
	 */
	public final static String PLAYER = "de.julz.game.ai.GreedyPlayer";

	public static AbstractPlayer player;

	public static void main(String[] args) {

		Class<?> clazz;
		try {
			clazz = Class.forName(PLAYER);
			Object obj = clazz.newInstance();
			player = (AbstractPlayer) obj;
			Controller.getInstance().start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

}
