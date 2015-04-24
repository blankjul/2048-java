package de.julz.game.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Set;

import de.julz.game.ai.evaluation.Evaluation;
import de.julz.game.ai.evaluation.PathEvaluation;
import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;
import de.julz.game.util.Helper;

/**
 * Main class for the evolutionary agent (3rd controller).
 * 
 *
 */
public class EvolutionPlayer extends AbstractPlayer {

	/** probability of a mutation */
	final private double MUTATE_PROBABILITY = 0.7;

	/** how many entries should the population has */
	private int populationSize = 20;

	/** number of the best to save for the next generation */
	private int numFittest = 10;

	/** number of generations that were applied. */
	private int numGeneration = 1000;

	/** The path size */
	private int pathLength = 40;
	
	/** the current population */
	private ArrayList<EvolutionaryNode> population = null;
	
	private Evaluation eval = new PathEvaluation();


	@Override
	public Action next(GameState state, Set<Action> actions) {
		
		population = new ArrayList<EvolutionaryNode>();
		
		
		for (int i = 0; i < populationSize; i++) {
			population.add(EvolutionaryNode.random(state, pathLength,eval));
		}
		
		for (int i = 0; i < numGeneration; i++) {
			nextGen();
			//System.out.println("------------------");
			//System.out.println("GENERATION: " + (i + 1));
			//System.out.println("------------------");
			//print();
		}
		
		return population.get(0).getFirstAction();
	}



	/**
	 * Create the next generation.
	 */
	public void nextGen() {

		ArrayList<EvolutionaryNode> nextPool = new ArrayList<EvolutionaryNode>();

		// save the fittest
		Collections.sort(population);

		for (int i = 0; i < numFittest && i < population.size(); i++) {
			EvolutionaryNode evo = population.get(i);
			nextPool.add(evo);
		}

		// create the next generation
		while (nextPool.size() < populationSize) {

			Random r = new Random();
			EvolutionaryNode selected = Helper.getRandomEntry(nextPool);

			// result that will be returned
			EvolutionaryNode result = null;

			// mutate
			if (r.nextDouble() < MUTATE_PROBABILITY) {
				result = selected.mutate();
				// crossover
			} else {

				// select a second one that is not the first!
				ArrayList<EvolutionaryNode> tmp = new ArrayList<EvolutionaryNode>();
				for (EvolutionaryNode candidate : nextPool) {
					if (candidate != selected)
						tmp.add(candidate);
				}
				EvolutionaryNode second = Helper.getRandomEntry(tmp);

				result = selected.crossover(second);
			}
			nextPool.add(result);
		}

		population = nextPool;
	}

	/**
	 * print 3 generations.
	 */
	public void print() {
		print(3);
	}

	/**
	 * print generations.
	 * 
	 * @param top
	 */
	public void print(int top) {
		Collections.sort(population);
		for (int i = 0; i < population.size() && i < top; i++) {
			if (i == 0)
				System.out.print("--> ");
			System.out.println(population.get(i));
		}
	}

}
