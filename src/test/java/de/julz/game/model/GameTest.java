package de.julz.game.model;

import org.junit.Assert;
import org.junit.Test;

public class GameTest {

	@Test
	public void Finished_Full_True() {
		Board board = new Board(new int[][]{{1,2,3,4},{4,3,2,1},{1,2,3,4},{4,3,2,1}});
		Game g = new Game(board);
		Assert.assertTrue(g.isFinished());
	}
	
	
	@Test
	public void Finished_Full_False() {
		Board board = new Board(new int[][]{{1,2,3,4},{1,3,2,1},{1,2,3,4},{4,3,2,1}});
		Game g = new Game(board);
		Assert.assertFalse(g.isFinished());
	}
	

	

}
