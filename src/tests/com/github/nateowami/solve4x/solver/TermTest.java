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

import static org.junit.Assert.*;

import java.nio.charset.MalformedInputException;

import org.junit.Test;

import com.github.nateowami.solve4x.solver.Term;

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
	 * @throws MalformedInputException 
	 */
	@Test
	public void testTerm() throws MalformedInputException {
		assertEquals(new Term("2x").getAsString(), "2x");
		assertTrue(new Term("2x(4xy)").getAsString().equals("2x(4xy)"));
		assertTrue(new Term("21").getAsString().equals("21"));
		assertTrue(new Term("xy(6)").getAsString().equals("xy(6)"));
		assertTrue(new Term("4y(-y+6)").getAsString().equals("4y(-y+6)"));
		assertTrue(new Term("(4)32").getAsString().equals("(4)32"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Term#numOfParts()}.
	 */
	@Test
	public void testNumOfParts() {
		fail("Not yet implemented"); // TODO
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
	}

}
