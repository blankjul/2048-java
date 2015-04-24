package de.julz.game.ai;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import de.julz.game.ai.mcts.GenericNode;
import de.julz.game.ai.mcts.MCTSNode;
import de.julz.game.ai.mcts.MostVisitedNodeComparator;
import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;
import de.julz.game.util.Helper;

/**
 * A MCTS strategy which builds the tree and searches in this tree using
 * default-and tree policy and some other methods.
 * 
 *
 */
public class MCTSPlayer extends AbstractPlayer {

	/** maximal depth of the tree */
	public int maxDepth = 4;

	/** this is the discounting factor. it's one so disabled default */
	public double gamma = 0.9;

	/** game tree that should be expanded by this class */
	public MCTSNode root;
	
	private double highestReward = 0;

	private final static Logger LOGGER = Logger.getLogger(MCTSPlayer.class.getName());
	
	
	@Override
	public Action next(GameState state, Set<Action> actions) {
		
		this.root = new MCTSNode(null);
		
		LOGGER.log(Level.INFO, "Expanding");
		for (int i = 0; i < 1000; i++) {
			expand(state, actions);
		}
		
		
		System.out.println(this);
		Action action = act();
		//System.out.println(action);
		
		return action;
	}

	
	/**
	 * Expand method to compute the whole MCTS algorithm. If time is left, the
	 * tree policy is executed to iterate trough the tree, the default policy to
	 * simulate Action from the new node and the backpropagation to compute the
	 * reward values.
	 */
	public boolean expand(GameState state, Set<Action> actions) {


		// tree policy by iteration through the tree
		MCTSNode n = treePolicy(root, actions);

		// default policy by looking for a random path
		double reward = defaultPolicy(n, state);
		if (reward > highestReward) highestReward = reward;

		// backpropagate the reward
		backPropagation(n, reward / highestReward);
		
		return true;
	}

	/**
	 * Backpropagation from the simulated node n to the root. Change the reward
	 * of all nodes which be visited according to the given reward.
	 * 
	 * @param n
	 *            at this node starts the backpropagation
	 * @param reward
	 *            reward of the simulated node
	 */
	private void backPropagation(MCTSNode n, double reward) {
		// while the actual node n is not the root
		while (n != null) {
			n.addVisited();
			n.Q += reward;
			reward *= gamma;
			n = n.getFather();
		}
	}

	
	
	/**
	 * The default policy simulates (advances) the given node and computes a
	 * reward value.
	 * 
	 * @param n
	 * @param state
	 * @return
	 */
	private double defaultPolicy(MCTSNode n, GameState state) {

		int heuristic = 0;

		// add random simulation
		int level = n.getLevel();
		while (level <= maxDepth) {
			Action a = Helper.getRandomEntry(state.getPossibleMoves());
			GameState next = state.createNextGameState(a);
			
			if (!state.hastNextState()) heuristic += -10;
			else heuristic += next.getScore() - state.getScore();
			
			state = next;
			++level;
		}
		
		
		

		return heuristic;
	}

	/**
	 * The tree policy iterates trough the tree, starting at the node n, until
	 * one node is not fully expanded. When a node is not fully expanded, it
	 * returns a random unexpected children of this node.
	 * 
	 * @param n
	 * @param actions
	 * @return
	 */
	private MCTSNode treePolicy(MCTSNode n, Set<Action> actions) {
		// get the MCTSNode and the state observation
		while (n.getLevel() <= maxDepth) {
			if (!n.isFullyExpanded(actions)) {
				MCTSNode child = (MCTSNode) n.getRandomUnexpandedChildren(actions);
				return child;
			} else {
				MCTSNode result = n.bestChild(n);
				n = result;
			}
		}
		return n;
	}
	

	/**
	 * After the expand method was executed, this method is called to chose the
	 * action which will be executed.
	 */
	public Action act() {
		// get all the children and compare the visited integer
		List<MCTSNode> nodeList = new ArrayList<MCTSNode>();
		for (GenericNode<Object> childGeneric : root.getAllChildren()) {
			MCTSNode child = (MCTSNode) childGeneric;
			nodeList.add(child);
		}
		MostVisitedNodeComparator comp = new MostVisitedNodeComparator();
		Collections.sort(nodeList, comp);

		// get all the best nodes. Could be that more than one have the max
		// values
		if (nodeList.size() == 0)
			return Action.NIL;
		else {
			// find the first point where they are not equal
			int i = 0;
			for (; i < nodeList.size() - 1; i++) {
				if (comp.compare(nodeList.get(i), nodeList.get(i + 1)) != 0)
					break;
			}
			nodeList = nodeList.subList(0, i + 1);
			
			
			// now the nodeList contains all nodes with the maximal equal result
			MCTSNode bestNode = Helper.getRandomEntry(nodeList);
				
			return bestNode.getFirstAction();
		}

	}
	
	/**
	 * Convert parameters to a String object.
	 */
	@Override
	public String toString() {
		String s = "";
		s += "---------------------\n";
		root.print(2);
		s += "---------------------";
		return s;
	}
	
	


}
