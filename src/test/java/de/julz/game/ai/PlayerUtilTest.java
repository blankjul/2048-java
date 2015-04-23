package de.julz.game.ai;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import de.julz.game.model.Board;
import de.julz.game.model.GameState;

public class PlayerUtilTest {

	

	@Test
	public void Board_Hash() {
		
		Board b1 =  Board.fromJSON("[[1,2,3,4],"
			     + "[4,3,2,1],"
			     + "[1,2,3,4],"
			     + "[4,3,0,0]]");
		
		Board b2 =  Board.fromJSON("[[1,2,3,4],"
			     + "[4,3,2,1],"
			     + "[1,2,3,4],"
			     + "[4,3,0,0]]");
		
		assertEquals(b1.hashCode(), b2.hashCode());
	}
	
	
	@Test
	public void allNextStatesTest() {
		
		Board b =  Board.fromJSON("[[1,2,3,4],"
							     + "[4,3,2,1],"
							     + "[1,2,3,4],"
							     + "[4,3,0,0]]");
		
		Map<GameState,Double> expected = new HashMap<GameState,Double>();
		expected.put(new GameState(Board.fromJSON("[[1,2,3,4],"
											     + "[4,3,2,1],"
											     + "[1,2,3,4],"
											     + "[4,3,1,0]]")),
											        0.45 );
		expected.put(new GameState(Board.fromJSON("[[1,2,3,4],"
											     + "[4,3,2,1],"
											     + "[1,2,3,4],"
											     + "[4,3,0,1]]")),
											        0.45 );
		
		expected.put(new GameState(Board.fromJSON("[[1,2,3,4],"
											     + "[4,3,2,1],"
											     + "[1,2,3,4],"
											     + "[4,3,2,0]]")),
											        0.05 );
		
		expected.put(new GameState(Board.fromJSON("[[1,2,3,4],"
											     + "[4,3,2,1],"
											     + "[1,2,3,4],"
											     + "[4,3,0,2]]")),
											        0.05 );
		Map<GameState,Double> result = Util.allNextStates(new GameState(b));
		assertEquals(expected, result);
		
	}
	
	

}
