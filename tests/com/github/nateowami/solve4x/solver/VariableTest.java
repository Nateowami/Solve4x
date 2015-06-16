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
public class VariableTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#render()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("z", new Variable("z").render());
		assertEquals("Q", new Variable("Q").render());
		assertEquals("t", new Variable("t").render());
		assertEquals("S", new Variable("S").render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#Variable(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testVariable()  {
		try{
			new Variable(null);
			fail("No exception thrown when null passed to constructor");
		}
		catch(ParsingException e){};
		try {
			new Variable("");
			fail("No exception thrown when empty string passed to constructor");
		}
		catch(ParsingException e){};
		try {
			new Variable("zy");
			fail("No exception thrown when string with length of 2 passed to constructor");
		}
		catch(ParsingException e){};
		try {
			new Variable("2");
			fail("No exception thrown when non-alphabetic char passed to constructor");
		}
		catch(ParsingException e){};
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#getVar()}.
	 * @ 
	 */
	@Test
	public void testGetVar()  {
		assertEquals('y', new Variable("y").getVar());
		assertEquals('S', new Variable("S").getVar());
		assertEquals('r', new Variable("r").getVar());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#isVariable()}.
	 */
	@Test
	public void testParseable()  {
		assertTrue(Variable.parseable("n"));
		assertTrue(Variable.parseable("y"));
		assertTrue(Variable.parseable("X"));
		assertFalse(Variable.parseable("2"));
		assertFalse(Variable.parseable("Ü"));
		assertFalse(Variable.parseable("ó"));
	}

}
