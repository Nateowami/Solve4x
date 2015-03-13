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

import com.github.nateowami.solve4x.config.RoundingRule;
import com.github.nateowami.solve4x.config.RoundingRule.BySignificantFigures;

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

		assertEquals("2.4*10⁷⁸⁹", new Number("2.4*10⁷⁸⁹").getAsString());
		assertEquals("0.79*10⁴", new Number("0.79*10⁴").getAsString());
		assertEquals("3.0*10⁷", new Number("3.0*10⁷").getAsString());
		assertEquals("7.639*10²", new Number("7.639*10²").getAsString());
		assertEquals("0.0072*10³", new Number("0.0072*10³").getAsString());
		assertEquals("47.093*10⁷⁸", new Number("47.093*10⁷⁸").getAsString());
		
		new Number("0.4");
		new Number("0");
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#add(com.github.nateowami.solve4x.solver.Number, com.github.nateowami.solve4x.solver.Number)}.
	 * @ 
	 */
	@Test
	public void testAdd()  {
		//setup constants
		RoundingRule all = RoundingRule.ALWAYS,
				forSci = RoundingRule.FOR_SCIENTIFIC_NOTATION,
				forSciAndDec = RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS;
		
		
		assertEquals("3", Number.add(new Number("1"), new Number("2"), all).getAsString());
		assertEquals("3.737", Number.add(new Number("1.637"), new Number("2.1"), forSci).getAsString());
		
		assertEquals("-3", Number.add(
				(Number) AlgebraicParticle.getInstance("-16"),
				(Number) AlgebraicParticle.getInstance("13"), forSciAndDec ).getAsString());
		
		assertEquals("-12.859", Number.add(
				(Number) AlgebraicParticle.getInstance("3.1415926535"),
				(Number) AlgebraicParticle.getInstance("-16.001"), all).getAsString());
		//add more like this ^		
		
		assertEquals("23.08", Number.add(new Number("0.024"), new Number("23.06"), forSciAndDec).getAsString());
		assertEquals("23.08", Number.add(new Number("0.024"), new Number("23.06"), all).getAsString());
		
		assertEquals("25", Number.add(new Number("23"), new Number("2"), all).getAsString());
		assertEquals("25", Number.add(new Number("23"), new Number("2"), forSci).getAsString());
		assertEquals("25", Number.add(new Number("23"), new Number("2"), forSciAndDec).getAsString());
		
		assertEquals("1", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), all).getAsString());
		assertEquals("0.99", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), forSci).getAsString());
		assertEquals("1", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), forSciAndDec).getAsString());

		assertEquals("1.0", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2.0"), all).getAsString());
		
		assertEquals("19.6", Number.add(new Number("6.6"), new Number("13.02"), all).getAsString());
		assertEquals("19.7", Number.add(new Number("6.6"), new Number("13.05"), all).getAsString());
		
		//TEST CUSTOM ROUNDING RULE
		assertEquals("19.6", Number.add(new Number("6.6"), new Number("13.02"), new RoundingRule(1)).getAsString());
		assertEquals("19.62", Number.add(new Number("6.6"), new Number("13.02"), new RoundingRule(2)).getAsString());
		assertEquals("19.62", Number.add(new Number("6.6"), new Number("13.02"), new RoundingRule(5)).getAsString());
		assertEquals("-465.9", Number.add(new Number("9.4"), new Number("-475.3"), new RoundingRule(2)).getAsString());		
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#toBigDecimal()}.
	 * @ 
	 */
	@Test
	public void testToBigDecimal()  {
		assertEquals("234.026", new Number("234.026").toBigDecimal().toString());
		assertEquals("42", new Number("42").toBigDecimal().toString());
		assertEquals("42", new Number("42.").toBigDecimal().toString());
		assertEquals("0.6", new Number("0.6").toBigDecimal().toString());
		assertEquals("-234.026", ((Number)AlgebraicParticle.getInstance("-234.026")).toBigDecimal().toString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#toNumber()}.
	 * @ 
	 */
	@Test
	public void testToNumber()  {
		assertEquals("1.0", Number.toNumber(new Number("1.0").toBigDecimal()).getAsString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("-234.026", AlgebraicParticle.getInstance("-234.026").getAsString());
		assertEquals("234.6", new Number("234.6").getAsString());
		assertEquals("-8.87", AlgebraicParticle.getInstance("-8.87").getAsString());
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

		assertTrue(Number.parseable("2.4*10⁷⁸⁹"));
		assertTrue(Number.parseable("0.79*10⁴"));
		assertTrue(Number.parseable("3.0*10⁷"));
		assertTrue(Number.parseable("7.639*10²"));
		assertTrue(Number.parseable("0.0072*10³"));
		assertTrue(Number.parseable("47.093*10⁷⁸"));

		
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
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#cloneWithNewSign(boolean)}.
	 */
	@Test
	public void testCloneWithNewSign() {
		assertEquals(Number.NEGATIVE_ONE, new Number("1").cloneWithNewSign(false));
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
