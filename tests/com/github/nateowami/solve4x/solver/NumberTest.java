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
		assertEquals("123", n1.getInteger());
		assertEquals("65", n1.getDecimal());
		Number n2 = new Number("7.28");
		assertEquals("7", n2.getInteger());
		assertEquals("28", n2.getDecimal());
		new Number("0.4");
		new Number("0");
		
		//make sure some things can't be parsed
		try{
			new Number("05");
			fail("05 should not be parseable as a number.");
		}
		catch(ParsingException e){};
		try{
			new Number("00.4");
			fail("00.4 should not be parseable as a number.");
		}
		catch(ParsingException e){};
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#add(com.github.nateowami.solve4x.solver.Number, com.github.nateowami.solve4x.solver.Number)}.
	 * @ 
	 */
	@Test
	public void testAdd()  {
		assertEquals("3", Number.add(new Number("1"), new Number("2")).getAsString());
		assertEquals("3.737", Number.add(new Number("1.637"), new Number("2.1")).getAsString());
		assertEquals("-3", Number.add(
				(Number) AlgebraicParticle.getInstance("-16", new Class[]{Number.class}),
				(Number) AlgebraicParticle.getInstance("13", new Class[]{Number.class})).getAsString());
		assertEquals("-12.8594073465", Number.add(
				(Number) AlgebraicParticle.getInstance("3.1415926535", new Class[]{Number.class}),
				(Number) AlgebraicParticle.getInstance("-16.001", new Class[]{Number.class})).getAsString());
		assertEquals("23.084", Number.add(new Number("0.024"), new Number("23.06")).getAsString());
		assertEquals("25", Number.add(new Number("23"), new Number("2")).getAsString());
		assertEquals("0.99", Number.add((Number) AlgebraicParticle.getInstance("-1.01", new Class[]{Number.class}), new Number("2")).getAsString());
		assertEquals("19.62", Number.add(new Number("6.6"), new Number("13.02")).getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("-234.026", AlgebraicParticle.getInstance("-234.026", new Class[]{Number.class}).getAsString());
		assertEquals("234.6", new Number("234.6").getAsString());
		assertEquals("-8.87", AlgebraicParticle.getInstance("-8.87", new Class[]{Number.class}).getAsString());
		assertEquals("1.0", new Number("1.0").getAsString());
		assertEquals("1", new Number("1").getAsString());
		assertEquals("6.03", new Number("6.03").getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#isNumber(java.lang.String)}.
	 */
	@Test
	public void testParseable() {
		assertTrue(Number.parseable("1"));
		assertTrue(Number.parseable("23"));
		assertTrue(Number.parseable("0"));
		assertTrue(Number.parseable("0.1"));
		assertTrue(Number.parseable("1.63"));
		assertTrue(Number.parseable("3.141592"));
		
		assertFalse(Number.parseable(""));
		assertFalse(Number.parseable("05"));
		assertFalse(Number.parseable("00.4"));
		assertFalse(Number.parseable("1.9.639"));
		assertFalse(Number.parseable(".1111"));
		assertFalse(Number.parseable("12."));
		assertFalse(Number.parseable("j8.7"));
		assertFalse(Number.parseable("8.8s"));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#GCF(int, int)}.
	 */
	@Test
	public void testGCF() {
		assertEquals(4, Number.GCF(12, 8));
		assertEquals(1, Number.GCF(7, 43));
		assertEquals(8, Number.GCF(8, 32));
	}
	
}
