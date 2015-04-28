package de.julz.game.ai;

import static org.junit.Assert.*;

import org.junit.Test;

import de.julz.game.ai.evaluation.SnakeEvaluation;
import de.julz.game.model.Board;
import de.julz.game.model.GameState;

public class SnakeEvaluationTest {

	@Test
	public void Optimal_Test() {
		Board board = Board.fromJSON( "[[1,0,0,1],"
								     + "[1,1,4,5],"
								     + "[1,3,0,0],"
								     + "[2,0,0,0]]");
		
		Board expected = Board.fromJSON(  "[[5,4,3,2],"
									     + "[0,0,0,1],"
									     + "[0,0,0,0],"
									     + "[0,0,0,0]]");
		assertEquals(expected, SnakeEvaluation.getOptimalState(new GameState(board)).getBoard());
	}
	

	@Test
	public void Score_Test() {

		Board board = Board.fromJSON(  "[[5,4,3,2],"
									     + "[0,0,0,1],"
									     + "[0,0,0,0],"
									     + "[0,0,0,0]]");
		assertEquals(5, new SnakeEvaluation().getScore(new GameState(board)), 0.001);

	}
	
	@Test
	public void Score_Test_2() {

		Board board = Board.fromJSON(  "[[3,1,0,0],"
									     + "[2,0,0,0],"
									     + "[0,0,0,0],"
									     + "[0,0,0,0]]");
		assertEquals(1, new SnakeEvaluation().getScore(new GameState(board)), 0.001);

	}
}
