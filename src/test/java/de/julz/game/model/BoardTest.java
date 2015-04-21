package de.julz.game.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class BoardTest {

	@Test
	public void Empty_Nothing() {
		Board board = new Board();
		
		Board result = board.move(Action.UP);
		
		assertArrayEquals(new int[Board.FIELD_SIZE][4], result.getArray());
		assertEquals(16, board.getEmptyFields().size());
		assertEquals(0, board.getNonEmptyFields().size());
	}
	
	@Test
	public void Move_Left() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Board result = board.move(Action.LEFT);
		assertArrayEquals(new int[][]{{2,0,0,0},{2,2,0,0},{5,0,0,0},{1,0,0,0}}, result.getArray());
	}
	
	@Test
	public void Move_Up() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Board result = board.move(Action.UP);
		assertArrayEquals(new int[][]{{2,1,1,2},{4,4,0,0},{1,0,0,0},{0,0,0,0}}, result.getArray());
	}
	
	@Test
	public void Move_Right() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Board result = board.move(Action.RIGHT);
		assertArrayEquals(new int[][]{{0,0,0,2},{0,0,2,2},{0,0,0,5},{0,0,0,1}}, result.getArray());
	}
	
	@Test
	public void Move_Down() {
		Board board = new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}});
		Board result = board.move(Action.DOWN);
		assertArrayEquals(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}}, result.getArray());
	}
	

	
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
	
	
	@Test
	public void Possible_Moves_None() {
		Board board = new Board(new int[][]{{1,2,3,4},{4,3,2,1},{1,2,3,4},{4,3,2,1}});
		Set<Action> actions = board.getPossibleMoves();
		assertTrue(actions.isEmpty());
	}
	
	@Test
	public void Possible_Moves_Left_And_Right() {
		Board board = new Board(new int[][]{{1,2,3,3},{4,3,2,1},{1,2,3,4},{4,3,2,1}});
		Set<Action> actions = board.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.LEFT, Action.RIGHT))));
	}
	
	@Test
	public void Possible_Moves_Up_And_Down() {
		Board board = new Board(new int[][]{{1,2,3,4},{1,3,2,1},{1,2,3,4},{4,3,2,1}});
		Set<Action> actions = board.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.UP, Action.DOWN))));
	}
	
	@Test
	public void Possible_Moves_Only_Right() {
		Board board = new Board(new int[][]{{1,2,0,0},{4,3,0,0},{1,2,0,0},{4,3,0,0}});
		Set<Action> actions = board.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.RIGHT))));
	}

}
