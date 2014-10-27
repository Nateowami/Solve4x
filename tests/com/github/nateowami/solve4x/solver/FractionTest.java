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

import org.junit.Test;

/**
 * @author Nateowami
 */
public class FractionTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#Fraction(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testFraction()  {
		Fraction frac1 = new Fraction("(2)/(3)");
		assertEquals("2", frac1.getTop().getAsString());
		assertEquals("3", frac1.getBottom().getAsString());
		
		Fraction frac2 = new Fraction("(x3+2)/(17)");
		assertEquals("x3+2", frac2.getTop().getAsString());
		assertEquals("17", frac2.getBottom().getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#parseable(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testParseable()  {
		assertTrue(Fraction.parseable("(2)/(3)"));
		assertTrue(Fraction.parseable("(23+xy6)/(43xy+6)"));
		assertTrue(Fraction.parseable("(23xy2)/(x+6-3xy)"));
		
		assertFalse(Fraction.parseable("xy6(4)/(5)"));
		assertFalse(Fraction.parseable("2/3"));
		assertFalse(Fraction.parseable("23xy2/(x+6-3xy"));
		assertFalse(Fraction.parseable("4/15+6"));
		assertFalse(Fraction.parseable("x+6"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getTop()}.
	 * @ 
	 */
	@Test
	public void testGetTop()  {
		assertEquals("2x+6y", new Fraction("(2x+6y)/(3)").getTop().getAsString());
		assertEquals("42y-16", new Fraction("(42y-16)/(87-6)").getTop().getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getBottom()}.
	 * @ 
	 */
	@Test
	public void testGetBottom()  {
		assertEquals("3", new Fraction("(2x+6y)/(3)").getBottom().getAsString());
		assertEquals("87-6yz4", new Fraction("(42y-16)/(87-6yz4)").getBottom().getAsString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("(3)/(xy+2)", new Fraction("(3)/(xy+2)").getAsString());
		assertEquals("(24x-73)/(2+5)", new Fraction("(24x-73)/(2+5)").getAsString());
		assertEquals("(4y((6)/(xy+3)))/(5)", new Fraction("(4y(6)/(xy+3))/(5)").getAsString());
	}

}
