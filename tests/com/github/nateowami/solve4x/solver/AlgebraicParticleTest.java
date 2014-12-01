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
public class AlgebraicParticleTest {

	//a list of all subclasses of AlgebraicParticle, which can be passed to
	//AlgebraicParticle.parseable() and AlgebraicParticle.getInstance()
	private static final Class[] all = new Class[]{Variable.class, Number.class, Root.class, Fraction.class, MixedNumber.class, Term.class, Expression.class};
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#sign()}.
	 * @ 
	 */
	@Test
	public void testSign()  {
		assertFalse(AlgebraicParticle.getInstance("-2x", new Class[]{Term.class}).sign());
		assertFalse(AlgebraicParticle.getInstance("-v", all).sign());
		assertTrue(AlgebraicParticle.getInstance("₄√(4y+x⁶)", all).sign());
		System.out.println(AlgebraicParticle.getInstance("(2x)", all));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#exponent()}.
	 */
	@Test
	public void testExponent() {
		assertEquals(1, AlgebraicParticle.getInstance("5", new Class[]{Number.class}).exponent());
		assertEquals(12, AlgebraicParticle.getInstance("5¹²", new Class[]{Number.class}).exponent());
		assertEquals(2, AlgebraicParticle.getInstance("(4y+6)²", new Class[]{Expression.class}).exponent());
		assertEquals(4, AlgebraicParticle.getInstance("-y⁴", new Class[]{Variable.class}).exponent());
		
		assertEquals(1, AlgebraicParticle.getInstance("x(4+y)⁴", new Class[]{Term.class}).exponent());
		assertEquals(2, AlgebraicParticle.getInstance("5(4y+6)²", new Class[]{Term.class}).exponent());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#getInstance(java.lang.String, java.lang.Class[])}.
	 * @ 
	 */
	@Test
	public void testGetInstance()  {
		assertEquals("-2x⁹(4+2)", AlgebraicParticle.getInstance("-2x⁹(4+2)", new Class[]{Term.class}).getAsString());
		
		AlgebraicParticle a1 = AlgebraicParticle.getInstance("2x+6²", all);
		assertTrue(a1 instanceof Expression);
		assertEquals(1, a1.exponent());
		
		AlgebraicParticle a2 = AlgebraicParticle.getInstance("(2x+6)²", all);
		assertTrue(a2 instanceof Expression);
		assertEquals(2, a2.exponent());
		
		AlgebraicParticle a3 = AlgebraicParticle.getInstance("(2x+6²)", all);
		assertTrue(a3 instanceof Expression);
		assertEquals(1, a3.exponent());
		
		AlgebraicParticle a4 = AlgebraicParticle.getInstance("2x²", all);
		assertTrue(a4 instanceof Term);
		assertEquals(1, a4.exponent());
		
		AlgebraicParticle a5 = AlgebraicParticle.getInstance("(2x)²", all);
		assertTrue(a5 instanceof Term);
		assertEquals(2, a5.exponent());
		
		AlgebraicParticle a6 = AlgebraicParticle.getInstance("(2x²)", all);
		assertTrue(a6 instanceof Term);
		assertEquals(1, a6.exponent());
		
		AlgebraicParticle a7 = AlgebraicParticle.getInstance("5²", all);
		assertTrue(a7 instanceof Number);
		assertEquals(2, a7.exponent());
		
		try {AlgebraicParticle.getInstance("(2)/(4)²", all); fail();}catch(ParsingException e){}
		
		try {AlgebraicParticle.getInstance("2(3)/(6)²", all); fail();}catch(ParsingException e){}
		
		try {AlgebraicParticle.getInstance("√(3)²", all); fail();}catch(ParsingException e){}
		
		AlgebraicParticle a8 = AlgebraicParticle.getInstance("x²", all);
		assertTrue(a8 instanceof Variable);
		assertEquals(2, a8.exponent());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#parseable(java.lang.String, java.lang.Class<com.github.nateowami.solve4x.solver.AlgebraicParticle>[])}.
	 */
	@Test
	public void testParseable() {
		//Term
		assertTrue(AlgebraicParticle.parseable("2x", new Class[]{Term.class}));
		assertTrue(AlgebraicParticle.parseable("2x6y", new Class[]{Term.class}));
		assertTrue(AlgebraicParticle.parseable("2x⁹", new Class[]{Term.class}));
		assertTrue(AlgebraicParticle.parseable("-2x", new Class[]{Term.class}));
		//Number
		assertTrue(AlgebraicParticle.parseable("2", new Class[]{Number.class}));
		assertTrue(AlgebraicParticle.parseable("-4x⁹", new Class[]{Term.class}));
		//That no class can parse an empty string
		assertFalse(AlgebraicParticle.parseable("", all));
	}
	
}
