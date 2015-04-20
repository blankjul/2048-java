package de.julz.game.model;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

public class PositionTest {

	@Test
	public void Position_Hash() {
		HashSet<Position> set = new HashSet<Position>();
		set.add(new Position(0, 0));
		set.add(new Position(0, 0));
		Assert.assertEquals(1, set.size());
	}

}
