package de.julz.game.model;

import static org.junit.Assert.*;

import org.junit.Test;

public class BoardTest {

	@Test
	public void Empty_Nothing() {
		Board board = new Board();
		
		board.move(Action.UP);
		board.move(Action.RIGHT);
		board.move(Action.DOWN);
		board.move(Action.LEFT);
		
		assertArrayEquals(new int[Board.FIELD_SIZE][4], board.getArray());
		assertEquals(16, board.getEmptyFields().size());
		assertEquals(0, board.getNonEmptyFields().size());
	}
	
	@Test
	public void Move_Left() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		board.move(Action.LEFT);
		assertArrayEquals(new int[][]{{2,0,0,0},{2,2,0,0},{5,0,0,0},{1,0,0,0}}, board.getArray());
	}
	
	@Test
	public void Move_Up() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		board.move(Action.UP);
		assertArrayEquals(new int[][]{{2,1,1,2},{4,4,0,0},{1,0,0,0},{0,0,0,0}}, board.getArray());
	}
	
	@Test
	public void Move_Right() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		board.move(Action.RIGHT);
		assertArrayEquals(new int[][]{{0,0,0,2},{0,0,2,2},{0,0,0,5},{0,0,0,1}}, board.getArray());
	}
	
	@Test
	public void Move_Down() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		board.move(Action.DOWN);
		assertArrayEquals(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}}, board.getArray());
	}
	
	@Test
	public void next() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		board.next(Action.DOWN);
		Board next = new Board(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}});
		assertEquals(board.getEmptyFields().size(), next.getEmptyFields().size() - 1);
	}
	
	

}
