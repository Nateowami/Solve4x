/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2014 Solve4x project

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.nateowami.solve4x.solver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class TermTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getAsString()}.
	 */
	@Test
	public void testGetAsString() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#Term(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testTerm()  {
		assertEquals("2x", new Term("2x").getAsString());
		assertEquals("2x4xy", new Term("2x4xy").getAsString());
		assertEquals("21", new Term("21").getAsString());
		assertEquals("xy(6+2)", new Term("xy(6+2)").getAsString());//FIXME
		assertEquals("4y(-y+6)", new Term("4y(-y+6)").getAsString());
		assertEquals("(4)32", new Term("(4)32").getAsString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#parseable(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testParseable()  {
		assertTrue(Term.parseable("1x"));
		assertFalse(Term.parseable("6(34+9xy2(45x+6)"));
		assertTrue(Term.parseable("6(34+9xy2(45x+6))"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#numOfParts()}.
	 */
	@Test
	public void testNumOfParts() {
		assertEquals(2, new Term("x6").numOfParts());
		assertEquals(4, new Term("4xy6(4)/(5)").numOfParts());
		assertEquals(3, new Term("2y(3+6)").numOfParts());
		assertEquals(1, new Term("((x+6)/(-4y))").numOfParts());
		assertEquals(3, new Term("js(4y+3)").numOfParts());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#getPartAt(int)}.
	 */
	@Test
	public void testGetPartAt() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#toString()}.
	 */
	@Test
	public void testToString() {
		fail("Not yet implemented"); // TODO
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#isTerm()}.
	 */
	@Test
	public void testIsTerm() {
		assertTrue(Term.parseable("2x"));
		assertFalse(Term.parseable("2+6"));
	}

}
