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
public class NumberTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#Number(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testNumber()  {
		Number n1 = new Number("123.65");
		assertEquals("123", n1.getIntegerPart());
		assertEquals("65", n1.getDecimalPart());
		Number n2 = new Number("7.28");
		assertEquals("7", n2.getIntegerPart());
		assertEquals("28", n2.getDecimalPart());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#add(com.github.nateowami.solve4x.solver.Number, com.github.nateowami.solve4x.solver.Number)}.
	 * @ 
	 */
	@Test
	public void testAdd()  {
		assertEquals("3", Number.add(new Number("1"), new Number("2")).getAsString());
		assertEquals("3.737", Number.add(new Number("1.637"), new Number("2.1")).getAsString());
		assertEquals("-3", Number.add(new Number("-16"), new Number("13")).getAsString());
		assertEquals("-12.8594073465", Number.add(new Number("3.1415926535"), new Number("-16.001")).getAsString());
		assertEquals("23.084", Number.add(new Number("0.024"), new Number("23.06")).getAsString());
		assertEquals("25", Number.add(new Number("23"), new Number("2")).getAsString());
		assertEquals("0.99", Number.add(new Number("-1.01"), new Number("2")).getAsString());
		assertEquals("19.62", Number.add(new Number("6.6"), new Number("13.02")).getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("-234.026", new Number("-234.026").getAsString());
		assertEquals("234.6", new Number("234.6").getAsString());
		assertEquals("-8.87", new Number("-8.87").getAsString());
		assertEquals("1.0", new Number("1.0").getAsString());
		assertEquals("1", new Number("1").getAsString());
		assertEquals("6.03", new Number("6.03").getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#isNumber(java.lang.String)}.
	 */
	@Test
	public void testIsNumber() {
		assertTrue(Number.parseable("1"));
		assertTrue(Number.parseable("0"));
		assertTrue(Number.parseable("0.1"));
		assertTrue(Number.parseable("1.63"));
		assertTrue(Number.parseable("3.141592"));
		assertFalse(Number.parseable(""));
		assertFalse(Number.parseable("1.9.639"));
		assertFalse(Number.parseable(".1111"));
		assertFalse(Number.parseable("12."));
		assertFalse(Number.parseable("j8.7"));
		assertFalse(Number.parseable("8.8s"));
	}
	
}
