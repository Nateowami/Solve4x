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

import static org.junit.Assert.*;

import org.junit.Test;

import static com.github.nateowami.solve4x.algorithm.AlgorithmTests.*;

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

		assertEquals("2.4*10⁷⁸⁹", new Number("2.4*10⁷⁸⁹").render());
		assertEquals("0.79*10⁴", new Number("0.79*10⁴").render());
		assertEquals("3.0*10⁷", new Number("3.0*10⁷").render());
		assertEquals("7.639*10²", new Number("7.639*10²").render());
		assertEquals("0.0072*10³", new Number("0.0072*10³").render());
		assertEquals("47.093*10⁷⁸", new Number("47.093*10⁷⁸").render());
		
		new Number("0.4");
		new Number("0");
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#add(Number, Number, RoundingRule)}.
	 * @ 
	 */
	@Test
	public void testAdd()  {
		//setup constants
		RoundingRule all = RoundingRule.ALWAYS,
				forSci = RoundingRule.FOR_SCIENTIFIC_NOTATION,
				forSciAndDec = RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS;
		
		
		assertEquals("3", Number.add(new Number("1"), new Number("2"), all).render());
		assertEquals("3.737", Number.add(new Number("1.637"), new Number("2.1"), forSci).render());
		
		assertEquals("-3", Number.add(
				(Number) AlgebraicParticle.getInstance("-16"),
				(Number) AlgebraicParticle.getInstance("13"), forSciAndDec ).render());
		
		assertEquals("-12.859", Number.add(
				(Number) AlgebraicParticle.getInstance("3.1415926535"),
				(Number) AlgebraicParticle.getInstance("-16.001"), all).render());
		//add more like this ^		
		
		assertEquals("23.08", Number.add(new Number("0.024"), new Number("23.06"), forSciAndDec).render());
		assertEquals("23.08", Number.add(new Number("0.024"), new Number("23.06"), all).render());
		
		assertEquals("25", Number.add(new Number("23"), new Number("2"), all).render());
		assertEquals("25", Number.add(new Number("23"), new Number("2"), forSci).render());
		assertEquals("25", Number.add(new Number("23"), new Number("2"), forSciAndDec).render());
		
		assertEquals("1", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), all).render());
		assertEquals("0.99", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), forSci).render());
		assertEquals("1", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), forSciAndDec).render());

		assertEquals("1.0", Number.add((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2.0"), all).render());
		
		assertEquals("19.6", Number.add(new Number("6.6"), new Number("13.02"), all).render());
		assertEquals("19.7", Number.add(new Number("6.6"), new Number("13.05"), all).render());
		
		//TEST CUSTOM ROUNDING RULE
		assertEquals("19.6", Number.add(new Number("6.6"), new Number("13.02"), new RoundingRule(1)).render());
		assertEquals("19.62", Number.add(new Number("6.6"), new Number("13.02"), new RoundingRule(2)).render());
		assertEquals("19.62", Number.add(new Number("6.6"), new Number("13.02"), new RoundingRule(5)).render());
		assertEquals("-465.9", Number.add(new Number("9.4"), new Number("-475.3"), new RoundingRule(2)).render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#multiply(Number, Number, RoundingRule)}.
	 * @ 
	 */
	@Test
	public void testMultiply()  {
		//setup constants
		RoundingRule all = RoundingRule.ALWAYS,
				forSci = RoundingRule.FOR_SCIENTIFIC_NOTATION,
				forSciAndDec = RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS;
		
		
		assertEquals("2", Number.multiply(new Number("1"), new Number("2"), all).render());
		assertEquals("3.4377", Number.multiply(new Number("1.637"), new Number("2.1"), forSci).render());
		assertEquals("8.742", Number.multiply(new Number("2.35"), new Number("3.72"), forSci).render());
		
		assertEquals("-208", Number.multiply(
				(Number) AlgebraicParticle.getInstance("-16"),
				(Number) AlgebraicParticle.getInstance("13"), forSciAndDec ).render());
		
		assertEquals("-50.269"
				+ "", Number.multiply(
				(Number) AlgebraicParticle.getInstance("3.1415926535"),
				(Number) AlgebraicParticle.getInstance("-16.001"), all).render());
		//multiply more like this ^		
		
		assertEquals("0.55", Number.multiply(new Number("0.024"), new Number("23.06"), forSciAndDec).render());
		assertEquals("0.55", Number.multiply(new Number("0.024"), new Number("23.06"), all).render());
		
		assertEquals("5*10¹", Number.multiply(new Number("23"), new Number("2"), all).render());
		assertEquals("46", Number.multiply(new Number("23"), new Number("2"), forSci).render());
		assertEquals("46", Number.multiply(new Number("23"), new Number("2"), forSciAndDec).render());
		
		assertEquals("-2", Number.multiply((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), all).render());
		assertEquals("-2.02", Number.multiply((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), forSci).render());
		assertEquals("-2", Number.multiply((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2"), forSciAndDec).render());

		assertEquals("-2.0", Number.multiply((Number) AlgebraicParticle.getInstance("-1.01"), new Number("2.0"), all).render());
		
		assertEquals("86", Number.multiply(new Number("6.6"), new Number("13.02"), all).render());
		assertEquals("86", Number.multiply(new Number("6.6"), new Number("13.05"), all).render());
		
		//TEST CUSTOM ROUNDING RULE
		assertEquals("85.9", Number.multiply(new Number("6.6"), new Number("13.02"), new RoundingRule(1)).render());
		assertEquals("85.93", Number.multiply(new Number("6.6"), new Number("13.02"), new RoundingRule(2)).render());
		assertEquals("85.932", Number.multiply(new Number("6.6"), new Number("13.02"), new RoundingRule(3)).render());
		assertEquals("-4467.82", Number.multiply(new Number("9.4"), new Number("-475.3"), new RoundingRule(2)).render());
		assertEquals("-4468.6", Number.multiply(new Number("9.4"), new Number("-475.38"), new RoundingRule(1)).render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#divide(Number, Number, RoundingRule).
	 * @ 
	 */
	@Test
	public void testDivide() {
		assertEquals(a("0.67"), Number.divide((Number)a("2"), (Number)a("3"), RoundingRule.FOR_SCIENTIFIC_NOTATION));
		assertEquals(a("0.7").render(), Number.divide((Number)a("2.0"), (Number)a("3"), RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS).render());
		assertEquals(a("3.0*10⁷"), Number.divide((Number)a("2.3*10⁸"), (Number)a("7.6"), RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS));
		assertEquals("3.3*10⁻⁸", Number.divide((Number)a("7.6"), (Number)a("2.3*10⁸"), RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS).render());
		assertEquals("0.265", Number.divide((Number)a("13"), (Number)a("49"), new RoundingRule(3)).render());
		assertEquals("2.08", Number.divide((Number)a("47.9"), (Number)a("23"), RoundingRule.FOR_SCIENTIFIC_NOTATION).render());
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
		assertEquals("1.0", Number.toNumber(new Number("1.0").toBigDecimal()).render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#render()}.
	 * @ 
	 */
	@Test
	public void testrender()  {
		assertEquals("-234.026", AlgebraicParticle.getInstance("-234.026").render());
		assertEquals("234.6", new Number("234.6").render());
		assertEquals("-8.87", AlgebraicParticle.getInstance("-8.87").render());
		assertEquals("1.0", new Number("1.0").render());
		assertEquals("1", new Number("1").render());
		assertEquals("6.03", new Number("6.03").render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Number#isNumber(java.lang.String)}.
	 */
	@Test
	public void testparsable() {
		assertTrue(Number.parsable("1"));
		assertTrue(Number.parsable("23"));
		assertTrue(Number.parsable("0"));
		assertTrue(Number.parsable("0.1"));
		assertTrue(Number.parsable("1.63"));
		assertTrue(Number.parsable("3.141592"));

		assertTrue(Number.parsable("2.4*10⁷⁸⁹"));
		assertTrue(Number.parsable("0.79*10⁴"));
		assertTrue(Number.parsable("3.0*10⁷"));
		assertTrue(Number.parsable("7.639*10²"));
		assertTrue(Number.parsable("0.0072*10³"));
		assertTrue(Number.parsable("47.093*10⁷⁸"));
		
		assertFalse(Number.parsable(""));
		assertFalse(Number.parsable("05"));
		assertFalse(Number.parsable("00.4"));
		assertFalse(Number.parsable("1.9.639"));
		assertFalse(Number.parsable(".1111"));
		assertFalse(Number.parsable("12."));
		assertFalse(Number.parsable("j8.7"));
		assertFalse(Number.parsable("8.8s"));
		assertFalse(Number.parsable("7.98*10"));
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
