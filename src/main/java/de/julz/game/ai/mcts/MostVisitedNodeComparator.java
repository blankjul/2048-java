package de.julz.game.ai.mcts;

import java.util.Comparator;

/**
 * This comparator uses the number of visits of an MCTS node to compare two of
 * them.
 * 
 *
 */
public class MostVisitedNodeComparator implements Comparator<MCTSNode> {

	public int compare(MCTSNode o1, MCTSNode o2) {
		return ((Integer) o2.getVisited()).compareTo(o1.getVisited());
	}


}
