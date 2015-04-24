package de.julz.game.ai;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import de.julz.game.ai.evaluation.PathEvaluation;
import de.julz.game.model.Board;
import de.julz.game.model.GameState;

public class HeuristicPathTest {

	

	
	@Test
	public void getDistanceTest() {
		
		Board b1 = Board.fromJSON(   "[[3,2,1,0],"
									+ "[0,0,0,0],"
									+ "[0,0,0,0],"
									+ "[0,0,0,0]]");
		
		
		Board b2 =  Board.fromJSON(   "[[1,0,0,0],"
								     + "[0,0,2,0],"
								     + "[1,0,0,0],"
								     + "[0,0,0,0]]");
	

		
		assertEquals(5, PathEvaluation.getDistance(b1, b2));
		
	}
	
	@Test
	public void getOptimalGameStatesTest() {
		
		Board b =  Board.fromJSON("[[1,0,0,0],"
							     + "[0,0,2,0],"
							     + "[1,0,0,0],"
							     + "[0,0,0,0]]");
		GameState state = new GameState(b);
		
		
	
		Set<GameState> expected = new HashSet<GameState>();
		expected.add(new GameState(Board.fromJSON("[[3,2,1,0],"
											     + "[0,0,0,0],"
											     + "[0,0,0,0],"
											     + "[0,0,0,0]]")));
		

		Set<GameState> result = PathEvaluation.getOptimalStates(state);
		assertEquals(expected, result);
		
	}
	
	

}
