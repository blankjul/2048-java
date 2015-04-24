package de.julz.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import de.julz.game.ai.MaximalExpectation;
import de.julz.game.ai.NoHardCodePlayer;
import de.julz.game.ai.RandomPlayer;
import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Game;
import de.julz.game.model.GameState;

public class Game2048Simulation {

	final public static List<AbstractPlayer> playerList = new ArrayList<AbstractPlayer>(Arrays.asList(new RandomPlayer(), new NoHardCodePlayer(), new MaximalExpectation()));

	final public static int ITERATIONS = 1000;

	
	public static DoubleSummaryStatistics getPlayerEvaluation(AbstractPlayer player) {
		DoubleSummaryStatistics statScore = new DoubleSummaryStatistics();

		ExecutorService threadPool = Executors.newFixedThreadPool(4);
		CompletionService<GameState> pool = new ExecutorCompletionService<GameState>(threadPool);
		for (int i = 0; i < ITERATIONS; i++) {
			pool.submit(new Game(player));
		}
		for (int i = 0; i < ITERATIONS; i++) {
			try {
				GameState result = pool.take().get();
				statScore.accept(result.getScore());
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		threadPool.shutdown();
		return statScore;
	}
	
	
	public static void main(String[] args) {
		for (AbstractPlayer player : playerList) {
			DoubleSummaryStatistics statScore = getPlayerEvaluation(player);
			System.out.println(String.format("%s, mean: %s, max: %s", player.getClass().getSimpleName(), statScore.getAverage(), statScore.getMax()));
		}

	}

}
