package de.julz.game.ai.mcts;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.GameState;

/**
 * Special node that could save one generic object!
 */
public class GenericNode<T>  {

	
	/** the current state observation */
	protected GameState state;

	/** path to this value */
	protected ArrayList<Action> history = new ArrayList<Action>();

	/** information that could be saved for that object */
	protected T info;

	/**
	 * Create a generic node with no state observation.
	 */
	public GenericNode() {
	}

	/**
	 * Create a Node that has an state observation
	 * 
	 * @param state
	 *            current state observation.
	 */
	public GenericNode(GameState state) {
		super();
		this.state = state;
	}

	/**
	 * Create a node that has a state observation and a path.
	 * 
	 * @param state
	 * @param history
	 */
	public GenericNode(GameState state, ArrayList<Action> history) {
		this(state);
		this.history = history;
	}

	/**
	 * Returns all the children that could be expanded from the Action set. For
	 * advancing the simulator instance is used!
	 * 
	 * @param Actionet
	 *            Action that should be used for expanding.
	 * @return all children expanded by that Action.
	 */
	public Set<GenericNode<T>> getChildren(Collection<Action> Actionet) {

		// create result list and reserve memory for the temporary state object
		Set<GenericNode<T>> nodes = new HashSet<GenericNode<T>>();

		// for each possible action
		for (Action action : Actionet) {
			GenericNode<T> child = getChild(action);
			nodes.add(child);
		}

		return nodes;
	}

	/**
	 * Returns that child if the action is performed.
	 * 
	 * @param action
	 * @return child node.
	 */
	public GenericNode<T> getChild(Action action) {
		// create the next state
		GameState next = this.state.next(action);

		// create the new path
		ArrayList<Action> pathOfChild = new ArrayList<Action>(getPath());
		pathOfChild.add(action);

		GenericNode<T> child = new GenericNode<T>(next, pathOfChild);
		return child;
	}

	/**
	 * Return all children with all available Action!
	 * 
	 * @return
	 */
	public Set<GenericNode<T>> getAllChildren() {
		return getChildren(state.getPossibleMoves());
	}

	/**
	 * return the current level from this node.
	 * 
	 * @return level.
	 */
	public int getLevel() {
		return history.size();
	}

	/**
	 * Returns the first action. That means the action from the root node to the
	 * second one one the path to achieve this node.
	 * 
	 * @return
	 */
	public Action getFirstAction() {
		if (history.isEmpty())
			return Action.NIL;
		else
			return history.get(0);
	}

	/**
	 * Returns simply the last action that was advanced.
	 * 
	 * @return
	 */
	public Action getLastAction() {
		if (history.isEmpty())
			return Action.NIL;
		else
			return history.get(history.size() - 1);
	}

	/**
	 * Return the path of this node.
	 * 
	 * @return
	 */
	public ArrayList<Action> getPath() {
		return history;
	}



	/**
	 * Get the specified info object.
	 * 
	 * @return
	 */
	public T getInfo() {
		return info;
	}


	/**
	 * Returns the state observation.
	 * 
	 * @return
	 */
	public GameState getState() {
		return state;
	}

	/**
	 * Sets the state observation.
	 * 
	 * @param state
	 */
	public void setState(GameState state) {
		this.state = state;
	}

	/**
	 * Sets the path of this node.
	 * 
	 * @param path
	 */
	public void setHistory(ArrayList<Action> path) {
		this.history = path;
	}
}
