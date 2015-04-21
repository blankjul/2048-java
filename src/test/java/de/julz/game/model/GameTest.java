package de.julz.game.model;

import static org.junit.Assert.assertEquals;

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
	
	@Test
	public void next() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Game g = new Game(board);
		g.next(Action.DOWN);
		Board next = new Board(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}});
		assertEquals(g.getBoard().getEmptyFields().size(), next.getEmptyFields().size() - 1);
	}
	

}
