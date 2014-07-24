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

import com.github.nateowami.solve4x.solver.ConstantFraction;

/**
 * @author Nateowami
 */
public class ConstantFractionTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#ConstantFraction(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testConstantFraction() throws MalformedInputException {
		ConstantFraction c1 = new ConstantFraction("2/3");
		assertTrue(c1.getTop().getAsString().equals("2") && c1.getBottom().getAsString().equals("3"));
		ConstantFraction c2 = new ConstantFraction("(2)/(3)");
		assertTrue(c2.getTop().getAsString().equals("2") && c2.getBottom().getAsString().equals("3"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#isConstantFraction(java.lang.String)}.
	 */
	@Test
	public void testIsConstantFraction() {
		assertTrue(ConstantFraction.isConstantFraction("(2)/(3)"));
		assertTrue(ConstantFraction.isConstantFraction("45/-6"));
		assertTrue(ConstantFraction.isConstantFraction("(-5)/(23)"));
		assertFalse(ConstantFraction.isConstantFraction("2/+56"));
		assertFalse(ConstantFraction.isConstantFraction("2/5+6"));
		assertFalse(ConstantFraction.isConstantFraction("25*6"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#getTop()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetTop() throws MalformedInputException {
		ConstantFraction c1 = new ConstantFraction("47/16");
		assertTrue(c1.getTop().getAsString().equals("47"));
		ConstantFraction c2 = new ConstantFraction("-8/16");
		assertTrue(c2.getTop().getAsString().equals("-8"));
		ConstantFraction c3 = new ConstantFraction("2/-5");
		assertTrue(c3.getTop().getAsString().equals("2"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#getBottom()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetBottom() throws MalformedInputException {
		ConstantFraction c1 = new ConstantFraction("47/16");
		assertTrue(c1.getBottom().getAsString().equals("16"));
		ConstantFraction c2 = new ConstantFraction("-8/16");
		assertTrue(c2.getBottom().getAsString().equals("16"));
		ConstantFraction c3 = new ConstantFraction("2/-5");
		assertTrue(c3.getBottom().getAsString().equals("-5"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#add(com.github.nateowami.solve4x.solver.ConstantFraction, com.github.nateowami.solve4x.solver.ConstantFraction)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testAdd() throws MalformedInputException {
		ConstantFraction c1 = new ConstantFraction("1/5");
		ConstantFraction c2 = new ConstantFraction("(3)/5");
		ConstantFraction c3 = new ConstantFraction("2/-5");
		ConstantFraction c4 = new ConstantFraction("-16/-5");
		System.out.println("here tis: " + ConstantFraction.add(c1, c2).getAsString());
		assertTrue(ConstantFraction.add(c1, c2).getAsString().equals("4/5"));
		assertTrue(ConstantFraction.add(c3,c4).getAsString().equals("-14/-5"));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#subtract(com.github.nateowami.solve4x.solver.ConstantFraction, com.github.nateowami.solve4x.solver.ConstantFraction)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testSubtract() throws MalformedInputException {
		ConstantFraction c1 = new ConstantFraction("1/5");
		ConstantFraction c2 = new ConstantFraction("3/5");
		ConstantFraction c3 = new ConstantFraction("2/-5");
		ConstantFraction c4 = new ConstantFraction("-16/-5");
		assertTrue(ConstantFraction.subtract(c1, c2).getAsString().equals("-2/5"));
		assertTrue(ConstantFraction.subtract(c3,c4).getAsString().equals("18/-5"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.ConstantFraction#multiply(com.github.nateowami.solve4x.solver.ConstantFraction, com.github.nateowami.solve4x.solver.ConstantFraction)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testMultiply() throws MalformedInputException {
		ConstantFraction c1 = new ConstantFraction("1/5");
		ConstantFraction c2 = new ConstantFraction("3/5");
		ConstantFraction c3 = new ConstantFraction("2/(5)");
		ConstantFraction c4 = new ConstantFraction("-16/-5");
		System.out.println("+++++++++++++++++++++"+ConstantFraction.multiply(c1, c2).getAsString());
		assertTrue(ConstantFraction.multiply(c1, c2).getAsString().equals("3/25"));
		assertTrue(ConstantFraction.multiply(c3,c4).getAsString().equals("32/25"));
	}
	
}
