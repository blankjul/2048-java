package de.julz.game.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public class RandomPlayer extends AbstractPlayer {

	@Override
	public Action next(GameState state, Set<Action> actions) {
		List<Action> list = new ArrayList<Action>(actions);
		int index = new Random().nextInt(list.size());
		return list.get(index);
	}
	

}
