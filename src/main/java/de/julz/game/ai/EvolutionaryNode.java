package de.julz.game.ai;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Set;

import de.julz.game.ai.evaluation.Evaluation;
import de.julz.game.ai.evaluation.ScoreEvalution;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;
import de.julz.game.util.Helper;

/**
 * A Node which is used for evolutionary Algorithm.
 *
 */
public class EvolutionaryNode extends GenericNode<Object> implements
		Comparable<EvolutionaryNode> {

	/** stores the actual score of the node */
	private Double score = null;
	
	private Evaluation eval = new ScoreEvalution();


	/**
	 * Creates an Evolutionary node by specifying the state observation and a
	 * path of Action.
	 * 
	 * @param stateObs
	 * @param path
	 */
	public EvolutionaryNode(GameState stateObs, ArrayList<Action> path) {
		super(stateObs, path);
	}
	
	
	/**
	 * Creates an Evolutionary node by specifying the state observation and a
	 * path of Action.
	 * 
	 * @param stateObs
	 * @param path
	 * @param eval used for getting the score
	 */
	public EvolutionaryNode(GameState stateObs, ArrayList<Action> path, Evaluation eval) {
		this(stateObs, path);
		this.eval = eval;
	}


	/**
	 * Creates a Evolutionary node with a random path of Action.
	 * 
	 * @param stateObs
	 * @param pathLength
	 * @return
	 */
	public static EvolutionaryNode random(GameState stateObs,
			int pathLength, Evaluation eval) {
		Set<Action> moves = stateObs.getPossibleMoves();
		ArrayList<Action> path = new ArrayList<Action>();
		// generate the path -> depends on the path length
		for (int i = 0; i < pathLength; i++) {
			Action random = Helper.getRandomEntry(moves);
			path.add(random);
		}
		EvolutionaryNode evoNode = new EvolutionaryNode(stateObs, path, eval);
		return evoNode;
	}
	


	/**
	 * Performs a crossover.
	 * 
	 * @return
	 */
	public EvolutionaryNode mutate() {
		return crossover(EvolutionaryNode.random(stateObs, getLevel(), getEval()));
	}

	
	/**
	 * Returns a new Evolutionary node by performing a crossover with the given
	 * node.
	 * 
	 * @param p
	 * @return
	 */
	public EvolutionaryNode crossover(EvolutionaryNode p) {
		ArrayList<Action> pathNew = new ArrayList<Action>();
		for (int i = 0; i < path.size(); i++) {
			Action a = (new Random().nextDouble() < 0.5) ? path.get(i)
					: p.getPath().get(i);
			pathNew.add(a);
		}
		return new EvolutionaryNode(stateObs, pathNew);
	}


	/**
	 * Compares two nodes. When there is no difference in the score, the
	 * heuristic value is used for comparison.
	 */
	public int compareTo(EvolutionaryNode o) {
		int result = ((Double) o.getScore()).compareTo((Double) getScore());
		return result;
	}
	
	/**
	 * Returns the score if the node.
	 * 
	 * @return
	 */
	public double getScore() {
		
		double weight = 0.9;
		
		if (score == null) {
			score = 0d;
			GameState copy = stateObs.copy();
			for (int i = 0; i < path.size(); i++) {
				Action a = path.get(i);
				copy.next(a);
				weight *= weight;
				score += (double) eval.getScore(copy) * weight;
			}
		}
		return score;
	}

	/**
	 * Sets the score of the node.
	 * 
	 * @param score
	 */
	public void setScore(double score) {
		this.score = score;
	}
	
	/**
	 * Returns a String representation of the node.
	 */
	@Override
	public String toString() {
		return String.format("Path: %s | score:%s ", Arrays.toString(path.toArray(new Action[path.size()])), score)
			 + super.toString() + stateObs.getBoard().toString();
	}


	public Evaluation getEval() {
		return eval;
	}


	public void setEval(Evaluation eval) {
		this.eval = eval;
	}


}
