package de.julz.game.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
	
	
	@Test
	public void Empty_Nothing() {
		Game g = new Game(new Board());
		Board result = g.move(g.getBoard(), Action.UP);
		
		assertArrayEquals(new int[Board.FIELD_SIZE][4], result.getArray());
		assertEquals(16, g.getBoard().getEmptyFields().size());
		assertEquals(0, g.getBoard().getNonEmptyFields().size());
	}
	
	@Test
	public void Move_Left() {
		Game g = new Game(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = g.move(g.getBoard(), Action.LEFT);
		assertArrayEquals(new int[][]{{2,0,0,0},{2,2,0,0},{5,0,0,0},{1,0,0,0}}, result.getArray());
	}
	
	@Test
	public void Move_Up() {
		Game g = new Game(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = g.move(g.getBoard(), Action.UP);
		assertArrayEquals(new int[][]{{2,1,1,2},{4,4,0,0},{1,0,0,0},{0,0,0,0}}, result.getArray());
	}
	
	@Test
	public void Move_Right() {
		Game g = new Game(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = g.move(g.getBoard(), Action.RIGHT);
		assertArrayEquals(new int[][]{{0,0,0,2},{0,0,2,2},{0,0,0,5},{0,0,0,1}}, result.getArray());
	}
	
	@Test
	public void Move_Down() {
		Game g = new Game(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = g.move(g.getBoard(), Action.DOWN);
		assertArrayEquals(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}}, result.getArray());
	}
	
	@Test
	public void Possible_Moves_None() {
		Game g = new Game(new Board(new int[][]{{1,2,3,4},{4,3,2,1},{1,2,3,4},{4,3,2,1}}));
		Set<Action> actions = g.getPossibleMoves();
		assertTrue(actions.isEmpty());
	}
	
	@Test
	public void Possible_Moves_Left_And_Right() {
		Game g = new Game(new Board(new int[][]{{1,2,3,3},{4,3,2,1},{1,2,3,4},{4,3,2,1}}));
		Set<Action> actions = g.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.LEFT, Action.RIGHT))));
	}
	
	@Test
	public void Possible_Moves_Up_And_Down() {
		Game g = new Game(new Board(new int[][]{{1,2,3,4},{1,3,2,1},{1,2,3,4},{4,3,2,1}}));
		Set<Action> actions = g.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.UP, Action.DOWN))));
	}
	
	@Test
	public void Possible_Moves_Only_Right() {
		Game g = new Game(new Board(new int[][]{{1,2,0,0},{4,3,0,0},{1,2,0,0},{4,3,0,0}}));
		Set<Action> actions = g.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.RIGHT))));
	}
	
	

}
