package de.julz.game.ai.mcts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.GameState;
import de.julz.game.util.Helper;

/**
 * This class represents a TreeNode. Important is that there is the possibility
 * to simulate next steps of a node. This has the consequence that there are
 * always father and children states.
 */
public class MCTSNode extends GenericNode<Object> {

	/** father node, if null it's the root */
	private MCTSNode father = null;

	/** children store */
	private HashMap<Action, MCTSNode> children = new HashMap<Action, MCTSNode>();

	/** the value for the exploration term */
	private double c = Math.sqrt(2);

	/** number of visits */
	private int visited = 0;

	/** random generator */
	private Random r = new Random();

	/** very small value */
	public static double epsilon = 0.0000000001d;

	/** reward of this node */
	public double Q = 0d;

	/** the uct value */
	public double uct = 0d;



	/**
	 * Constructs a MCTS Node.
	 * 
	 * @param father
	 */
	public MCTSNode(MCTSNode father) {
		super();
		this.father = father;
	}

	/**
	 * Construct a MCTS Node. no state observation is needed it's open loop!
	 * 
	 * @param father
	 *            previous node
	 * @param lastAction
	 *            last action to come to this node
	 */
	public MCTSNode(MCTSNode father, Action lastAction) {
		this(father);
		// create the new path
		if (father != null)
			history = new ArrayList<Action>(father.getPath());
		history.add(lastAction);

	}

	/**
	 * Check if there are Action that were not expanded.
	 * 
	 * @param state
	 * @return false if there could be new children
	 */
	public boolean isFullyExpanded(Set<Action> allAction) {
		Set<Action> Action = new HashSet<de.julz.game.model.Action>(allAction);
		Action.removeAll(children.keySet());
		if (Action.isEmpty()) {
			return true;
		}
		return false;
	}

	/**
	 * Removes the first action
	 */
	public void removeFirstAction() {
		if (!history.isEmpty()) {
			history.remove(0);
		}
	}

	/**
	 * Returns a child node, speified by the given action.
	 */
	@Override
	public GenericNode<Object> getChild(Action lastAction) {
		if (this.children.containsKey(lastAction))
			return children.get(lastAction);
		MCTSNode child = new MCTSNode(this, lastAction);
		this.children.put(lastAction, child);
		return child;
	}

	/**
	 * Returns a set of all children stored in this node.
	 */
	@Override
	public Set<GenericNode<Object>> getAllChildren() {
		Set<GenericNode<Object>> result = new HashSet<GenericNode<Object>>();
		for (Action a : children.keySet()) {
			MCTSNode n = children.get(a);
			result.add(n);
		}
		return result;
	}

	/**
	 * Returns a randomly chosen child of the node that was not expanded before.
	 * 
	 * @param allAction
	 * @return
	 */
	public GenericNode<Object> getRandomUnexpandedChildren(
			Set<Action> allAction) {
		Set<Action> Action = new HashSet<Action>(allAction);
		Action.removeAll(children.keySet());

		if (Action.isEmpty()) {
			return null;
		}
		Action rndAction = Helper.getRandomEntry(Action);
		GenericNode<Object> child = getChild(rndAction);

		return child;
	}

	/**
	 * Retruns the fahter of this node.
	 * 
	 * @return
	 */
	public MCTSNode getFather() {
		return father;
	}

	/**
	 * Sets the father of this node.
	 * 
	 * @param father
	 */
	public void setFather(MCTSNode father) {
		this.father = father;
	}

	/**
	 * Returns a already visited child node. It is chosen using the ucb formula
	 * to iterate trough the tree.
	 * 
	 * @param n
	 * @return
	 */
	public MCTSNode bestChild(MCTSNode n) {

		// always the best child is saved here
		MCTSNode bestChild = null;
		double bestValue = Double.NEGATIVE_INFINITY;

		for (GenericNode<Object> childGeneric : getAllChildren()) {

			MCTSNode child = (MCTSNode) childGeneric;

			double exploitation = child.Q
					/ (child.visited + epsilon * r.nextDouble());
			double exploration = Math.sqrt(Math.log(n.visited + 1)
					/ (child.visited));
			double tiebreak = r.nextDouble() * epsilon;

			child.uct = exploitation + c * exploration + tiebreak;

			if (child.uct >= bestValue) {
				bestChild = child;
				bestValue = child.uct;
			}
		}
		return bestChild;
	}

	/**
	 * Get the number how often this node was visited.
	 * 
	 * @return
	 */
	public int getVisited() {
		return visited;
	}

	/**
	 * Increments the number of visits.
	 */
	public void addVisited() {
		this.visited += 1;
	}

	/**
	 * Set the number of visits.
	 * 
	 * @param visited
	 */
	public void setVisited(int visited) {
		this.visited = visited;
	}

	/**
	 * Generates a String representation of this node.
	 */
	@Override
	public String toString() {
		return String.format(
				"level:%s  | lastAction:%s | visited:%s | Q:%s ",
				getLevel(), getLastAction(), visited, Q);
	}



	/**
	 * Prints the node and all of his children to the console. levelLimit
	 * specifies the recursion depth.
	 * 
	 * @param levelLimit
	 */
	public void print(int levelLimit) {
		print(this, 0, levelLimit);
	}

	/**
	 * Prints a MCTS node to the console, given the actual level and the maximal
	 * level. It is a recursive function which calls itself for every children
	 * of the actual node.
	 * 
	 * @param node
	 * @param level
	 * @param levelLimit
	 */
	private void print(MCTSNode node, int level, int levelLimit) {
		if (level > levelLimit)
			return;
		for (int i = 0; i < level; i++) {
			System.out.print('\t');
		}
		System.out.println(node);
		for (GenericNode<Object> childGeneric : node.getAllChildren()) {
			MCTSNode child = (MCTSNode) childGeneric;
			print(child, level + 1, levelLimit);
		}
	}

	/**
	 * Simulates all Action which are stored in the path of this node.
	 * 
	 * @param state
	 * @return
	 */
	public GameState simulate(GameState state) {
		for (Action a : history) {
			state = state.next(a);
		}
		return state;
	}

}
