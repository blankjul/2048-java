package de.julz.game.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.julz.game.model.AbstractPlayer;
import de.julz.game.model.Action;
import de.julz.game.model.GameState;

public class GreedyScorePlayer extends AbstractPlayer {

	int counter = 0;

	@Override
	public Action next(GameState state, Set<Action> actions) {
		List<Action> l = new ArrayList<Action>(actions);
		int index = 0;
		int maxScore = -1;
		
		for (int i = 0; i < l.size(); i++) {
			Action a = l.get(i);
			int currentScore = state.createNextGameState(a).getScore();
			if (currentScore > maxScore) {
				index = i;
				maxScore = currentScore;
			}
		}
		return l.get(index);
	}
	
	

}
