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

/**
 * @author Nateowami
 */
public class VariableTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#getAsString()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetAsString() throws MalformedInputException {
		assertEquals("z", new Variable("z").getAsString());
		assertEquals("Q", new Variable("Q").getAsString());
		assertEquals("t", new Variable("t").getAsString());
		assertEquals("S", new Variable("S").getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#Variable(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testVariable() throws MalformedInputException {
		try{
			new Variable(null);
			fail("No exception thrown when null passed to constructor");
			new Variable("");
			fail("No exception thrown when empty string passed to constructor");
			new Variable("zy");
			fail("No exception thrown when string with length of 2 passed to constructor");
			new Variable("2");
			fail("No exception thrown when non-alphabetic char passed to constructor");
		}
		catch(MalformedInputException e){};
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#getVar()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetVar() throws MalformedInputException {
		assertEquals('y', new Variable("y").getVar());
		assertEquals('S', new Variable("S").getVar());
		assertEquals('r', new Variable("r").getVar());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Variable#isVariable()}.
	 */
	@Test
	public void testIsVariable() throws MalformedInputException {
		assertTrue(Variable.isVariable("n"));
		assertTrue(Variable.isVariable("y"));
		assertTrue(Variable.isVariable("X"));
		assertFalse(Variable.isVariable("2"));
		assertFalse(Variable.isVariable("Ü"));
		assertFalse(Variable.isVariable("ó"));
	}

}
