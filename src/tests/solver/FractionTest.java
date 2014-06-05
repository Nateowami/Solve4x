/*
    Solve4x - An audio-visual algebra solver
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
package tests.solver;

import static org.junit.Assert.*;

import java.nio.charset.MalformedInputException;

import org.junit.Test;

import com.github.nateowami.solve4x.solver.Fraction;

/**
 * @author Nateowami
 */
public class FractionTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#Fraction(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testFraction() throws MalformedInputException {
		Fraction frac1 = new Fraction("2/3");
		assertTrue(frac1.getTop().getAsString().equals("2"));
		assertTrue(frac1.getBottom().getAsString().equals("3"));
		assertTrue(frac1.isConstant());
		
		Fraction frac2 = new Fraction("(x3+2)/17");
		System.out.println(frac2.getTop().getAsString());
		assertTrue(frac2.getTop().getAsString().equals("x3+2"));
		assertTrue(frac2.getBottom().getAsString().equals("17"));
		assertFalse(frac2.isConstant());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#isFraction(java.lang.String)}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testIsFraction() throws MalformedInputException {
		assertTrue(Fraction.isFraction("2/3"));
		assertTrue(Fraction.isFraction("(23+xy6)/(43xy+6)"));
		assertTrue(Fraction.isFraction("23xy2/(x+6-3xy)"));
		
		assertFalse(Fraction.isFraction("23xy2/(x+6-3xy"));
		assertFalse(Fraction.isFraction("4/15+6"));
		assertFalse(Fraction.isFraction("x+6"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#isConstant()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testIsConstant() throws MalformedInputException {
		Fraction frac1 = new Fraction("15/(3)");
		assertTrue(frac1.isConstant());
		Fraction frac2 = new Fraction("123/18");
		assertTrue(frac2.isConstant());
		Fraction frac3 = new Fraction("x/18");
		assertFalse(frac3.isConstant());
		Fraction frac4 = new Fraction("18/(x+6)");
		assertFalse(frac4.isConstant());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getTop()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetTop() throws MalformedInputException {
		Fraction frac1 = new Fraction("(2x+6y)/(3)");
		assertTrue(frac1.getTop().getAsString().equals("2x+6y"));
		Fraction frac2 = new Fraction("(42y-16)/(87-6)");
		assertTrue(frac2.getTop().getAsString().equals("42y-16"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getBottom()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetBottom() throws MalformedInputException {
		Fraction frac1 = new Fraction("(2x+6y)/(3)");
		assertTrue(frac1.getBottom().getAsString().equals("3"));
		Fraction frac2 = new Fraction("(42y-16)/(87-6yz4)");
		assertTrue(frac2.getBottom().getAsString().equals("87-6yz4"));
	}

}
