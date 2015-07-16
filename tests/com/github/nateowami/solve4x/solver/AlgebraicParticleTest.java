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

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#sign()}.
	 */
	@Test
	public void testSign()  {
		assertFalse(AlgebraicParticle.getInstance("-2x").sign());
		assertFalse(AlgebraicParticle.getInstance("-v").sign());
		assertTrue(AlgebraicParticle.getInstance("₄√(4y+x⁶)").sign());
		AlgebraicParticle a = AlgebraicParticle.getInstance("-2+7");
		assertTrue(a.sign());
		assertFalse(((Expression)a).get(0).sign());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#exponent()}.
	 */
	@Test
	public void testExponent() {
		assertEquals(1, AlgebraicParticle.getInstance("5").exponent());
		assertEquals(12, AlgebraicParticle.getInstance("5¹²").exponent());
		assertEquals(2, AlgebraicParticle.getInstance("(4y+6)²").exponent());
		assertEquals(4, AlgebraicParticle.getInstance("-y⁴").exponent());
		
		assertEquals(1, AlgebraicParticle.getInstance("x(4+y)⁴").exponent());
		assertEquals(1, AlgebraicParticle.getInstance("5(4y+6)²").exponent());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#getInstance(java.lang.String, java.lang.Class[])}.
	 * @ 
	 */
	@Test
	public void testGetInstance()  {
		assertEquals("2xy", AlgebraicParticle.getInstance("(2x)y").render());
		assertEquals("-(2x+4)y", AlgebraicParticle.getInstance("-(2x+4)y").render());
		assertEquals("-2x⁹", AlgebraicParticle.getInstance("-2x⁹").render());
		assertEquals("-2x⁹(4+2)", AlgebraicParticle.getInstance("-2x⁹(4+2)").render());
		
		AlgebraicParticle a1 = AlgebraicParticle.getInstance("2x+6²");
		assertTrue(a1 instanceof Expression);
		assertEquals(1, a1.exponent());
		
		AlgebraicParticle a2 = AlgebraicParticle.getInstance("(2x+6)²");
		assertTrue(a2 instanceof Expression);
		assertEquals(2, a2.exponent());
		
		AlgebraicParticle a3 = AlgebraicParticle.getInstance("(2x+6²)");
		assertTrue(a3 instanceof Expression);
		assertEquals(1, a3.exponent());
		
		AlgebraicParticle a4 = AlgebraicParticle.getInstance("2x²");
		assertTrue(a4 instanceof Term);
		assertEquals(1, a4.exponent());
		
		AlgebraicParticle a5 = AlgebraicParticle.getInstance("(2x)²");
		assertTrue(a5 instanceof Term);
		assertEquals(2, a5.exponent());
		
		AlgebraicParticle a6 = AlgebraicParticle.getInstance("(2x²)");
		assertTrue(a6 instanceof Term);
		assertEquals(1, a6.exponent());
		
		AlgebraicParticle a7 = AlgebraicParticle.getInstance("5²");
		assertTrue(a7 instanceof Number);
		assertEquals(2, a7.exponent());
		
		try {AlgebraicParticle.getInstance("(2)/(4)²"); fail();}catch(ParsingException e){}
		
		try {AlgebraicParticle.getInstance("2(3)/(6)²"); fail();}catch(ParsingException e){}
		
		try {AlgebraicParticle.getInstance("√(3)²"); fail();}catch(ParsingException e){}
		
		AlgebraicParticle a8 = AlgebraicParticle.getInstance("x²");
		assertTrue(a8 instanceof Variable);
		assertEquals(2, a8.exponent());
		
		assertTrue(AlgebraicParticle.getInstance("2.34*10²") instanceof Number);
		assertTrue(AlgebraicParticle.getInstance("-2.34*10²") instanceof Number);
		
		AlgebraicParticle.getInstance("(-2.34*10²)");
		
		Term a9 = (Term) AlgebraicParticle.getInstance("x(-2.34*10²)");
		assertEquals(Integer.valueOf(2), ((Number)a9.get(1)).getScientificNotationExponent());
		assertFalse(a9.get(1).sign());
		
		AlgebraicParticle.getInstance("(2.34*9⁴)");
		
		assertEquals("-y+6", AlgebraicParticle.getInstance("-y+6").render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#parsable(java.lang.String, java.lang.Class<com.github.nateowami.solve4x.solver.AlgebraicParticle>[])}.
	 */
	@Test
	public void testparsable() {
		//Term
		assertTrue(AlgebraicParticle.parsable("(2x)"));
		assertTrue(AlgebraicParticle.parsable("2x"));
		assertTrue(AlgebraicParticle.parsable("2x6y"));
		assertTrue(AlgebraicParticle.parsable("2x⁹"));
		assertTrue(AlgebraicParticle.parsable("-2x"));
		//Number
		assertTrue(AlgebraicParticle.parsable("2"));
		assertTrue(AlgebraicParticle.parsable("-4x⁹"));
		assertTrue(AlgebraicParticle.parsable("(-10x)"));
		assertTrue(AlgebraicParticle.parsable("(-2.34*10²)"));
		assertTrue(AlgebraicParticle.parsable("-(2x)⁹"));
		assertTrue(AlgebraicParticle.parsable("(2x)²"));
		assertTrue(AlgebraicParticle.parsable("(-4y)"));
		assertTrue(AlgebraicParticle.parsable("(-4y)²"));
		assertTrue(AlgebraicParticle.parsable("-2(-4+3²)²"));
		assertFalse(AlgebraicParticle.parsable("-(-2)"));
		//That no class can parse an empty string
		assertFalse(AlgebraicParticle.parsable(""));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSignAndExponent(java.lang.Boolean sign, java.lang.Integer exponent)}.
	 */
	@Test
	public void cloneWithNewSignAndExponent() {
		assertEquals(a("-2x"), a("2x").cloneWithNewSignAndExponent(false, null));
		assertEquals(a("-(4(3-5))⁹"), a("-4(3-5)").cloneWithNewSignAndExponent(null, 9));
	}
	
	AlgebraicParticle a(String s){
		return AlgebraicParticle.getInstance(s);
	}
	
}
