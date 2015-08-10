/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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
		assertEquals("2", frac1.getTop().render());
		assertEquals("3", frac1.getBottom().render());
		
		Fraction frac2 = new Fraction("(x3+2)/(17)");
		assertEquals("x3+2", frac2.getTop().render());
		assertEquals("17", frac2.getBottom().render());
		
		new Fraction("((4.3)/(2))/(4)");
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#parsable(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testparsable()  {
		assertTrue(Fraction.parsable("(2)/(3)"));
		assertTrue(Fraction.parsable("(23+xy6)/(43xy+6)"));
		assertTrue(Fraction.parsable("(23xy2)/(x+6-3xy)"));
		assertTrue(Fraction.parsable("(4.3)/(2)"));
		assertTrue(Fraction.parsable("((4.3)/(2))/(4)"));
		assertTrue(Fraction.parsable("(4.3)/(2)"));
		
		assertFalse(Fraction.parsable("xy6(4)/(5)"));
		assertFalse(Fraction.parsable("2/3"));
		assertFalse(Fraction.parsable("23xy2/(x+6-3xy"));
		assertFalse(Fraction.parsable("4/15+6"));
		assertFalse(Fraction.parsable("x+6"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getTop()}.
	 * @ 
	 */
	@Test
	public void testGetTop()  {
		assertEquals("2x+6y", new Fraction("(2x+6y)/(3)").getTop().render());
		assertEquals("42y-16", new Fraction("(42y-16)/(87-6)").getTop().render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#getBottom()}.
	 * @ 
	 */
	@Test
	public void testGetBottom()  {
		assertEquals("3", new Fraction("(2x+6y)/(3)").getBottom().render());
		assertEquals("87-6yz4", new Fraction("(42y-16)/(87-6yz4)").getBottom().render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#render()}.
	 * @ 
	 */
	@Test
	public void testrender()  {
		assertEquals("(3)/(xy+2)", new Fraction("(3)/(xy+2)").render());
		assertEquals("(24x-73)/(2+5)", new Fraction("(24x-73)/(2+5)").render());
		assertEquals("(4y((6)/(xy+3)))/(5)", new Fraction("(4y(6)/(xy+3))/(5)").render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#isSimplified()}.
	 * @ 
	 */
	@Test
	public void testIsSimplified()  {
		assertTrue(new Fraction("(2)/(3)").isSimplified());
		assertTrue(new Fraction("(1)/(89)").isSimplified());
		
		assertFalse(new Fraction("(-8)/(9)").isSimplified());
		assertFalse(new Fraction("(1)/(-12)").isSimplified());
		assertFalse(new Fraction("(2)/(8)").isSimplified());
		assertFalse(new Fraction("(3)/(12)").isSimplified());
		assertFalse(new Fraction("(4)/(3.2)").isSimplified());
		assertFalse(new Fraction("(4x)/(3)").isSimplified());
		assertFalse(new Fraction("(12.2)/(3)").isSimplified());
		assertFalse(new Fraction("(4)/(-3)").isSimplified());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Fraction#constant()}.
	 * @ 
	 */
	@Test
	public void testConstant()  {
		assertTrue(new Fraction("(2)/(3)").constant());
		assertTrue(new Fraction("(2.6)/(3)").constant());
		assertTrue(new Fraction("(2)/(-3)").constant());
		assertTrue(new Fraction("(2)/(-243.8)").constant());
		
		assertFalse(new Fraction("(1)/(x)").constant());
		assertFalse(new Fraction("(1)/(4x2)").constant());
		assertFalse(new Fraction("(y)/(14)").constant());
	}

}
