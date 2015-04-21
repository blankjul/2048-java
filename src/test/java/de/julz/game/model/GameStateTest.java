package de.julz.game.model;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class GameStateTest {

	
	@Test
	public void Empty_Nothing() {
		GameState state = new GameState(new Board());
		Board result = state.move(Action.UP).getBoard();
		
		assertArrayEquals(new int[Board.FIELD_SIZE][4], result.getArray());
		assertEquals(16, state.getBoard().getEmptyFields().size());
		assertEquals(0, state.getBoard().getNonEmptyFields().size());
	}
	
	
	@Test
	public void Move_Left() {
		GameState state = new GameState(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = state.move(Action.LEFT).getBoard();
		assertArrayEquals(new int[][]{{2,0,0,0},{2,2,0,0},{5,0,0,0},{1,0,0,0}}, result.getArray());
	}
	
	@Test
	public void Move_Up() {
		GameState state = new GameState(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = state.move(Action.UP).getBoard();
		assertArrayEquals(new int[][]{{2,1,1,2},{4,4,0,0},{1,0,0,0},{0,0,0,0}}, result.getArray());
	}
	
	@Test
	public void Move_Right() {
		GameState state = new GameState(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = state.move(Action.RIGHT).getBoard();
		assertArrayEquals(new int[][]{{0,0,0,2},{0,0,2,2},{0,0,0,5},{0,0,0,1}}, result.getArray());
	}
	
	@Test
	public void Move_Down() {
		GameState state = new GameState(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}}));
		Board result = state.move(Action.DOWN).getBoard();
		assertArrayEquals(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}}, result.getArray());
	}
	
	
	@Test
	public void next() {
		GameState state = new GameState(new Board(new int[][]{{1,0,0,1},{1,1,1,1},{4,4,0,0},{1,0,0,0}})).next(Action.DOWN);
		Board next = new Board(new int[][]{{0,0,0,0},{2,0,0,0},{4,1,0,0},{1,4,1,2}});
		assertEquals(state.getBoard().getEmptyFields().size(), next.getEmptyFields().size() - 1);
	}


	
	@Test
	public void Possible_Moves_None() {
		GameState state = new GameState(new Board(new int[][]{{1,2,3,4},{4,3,2,1},{1,2,3,4},{4,3,2,1}}));
		Set<Action> actions = state.getPossibleMoves();
		assertTrue(actions.isEmpty());
	}
	
	@Test
	public void Possible_Moves_Left_And_Right() {
		GameState state = new GameState(new Board(new int[][]{{1,2,3,3},{4,3,2,1},{1,2,3,4},{4,3,2,1}}));
		Set<Action> actions = state.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.LEFT, Action.RIGHT))));
	}
	
	@Test
	public void Possible_Moves_Up_And_Down() {
		GameState state = new GameState(new Board(new int[][]{{1,2,3,4},{1,3,2,1},{1,2,3,4},{4,3,2,1}}));
		Set<Action> actions = state.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.UP, Action.DOWN))));
	}
	
	@Test
	public void Possible_Moves_Only_Right() {
		GameState state = new GameState(new Board(new int[][]{{1,2,0,0},{4,3,0,0},{1,2,0,0},{4,3,0,0}}));
		Set<Action> actions = state.getPossibleMoves();
		assertTrue(actions.equals(new HashSet<Action>(Arrays.asList(Action.RIGHT))));
	}
	
	
	
	

}
