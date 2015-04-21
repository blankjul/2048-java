package de.julz.game.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BoardTest {

	
	
	@Test
	public void Board_Equals_True() {
		Board board1 = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Board board2 = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		boolean equal = board1.equals(board2);
		assertTrue(equal);
	}
	
	@Test
	public void Board_Equals_False() {
		Board board1 = new Board(new int[][]{{1,0,0,2},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Board board2 = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		boolean equal = board1.equals(board2);
		assertFalse(equal);
	}
	
	
	

}
