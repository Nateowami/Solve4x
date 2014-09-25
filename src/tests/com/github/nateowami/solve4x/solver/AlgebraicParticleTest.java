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

/**
 * @author Nateowami
 */
public class AlgebraicParticleTest {

	//a list of all subclasses of AlgebraicParticle, which can be passed to
	//AlgebraicParticle.parseable() and AlgebraicParticle.getInstance()
	private static final Class[] all = new Class[]{Variable.class, Number.class, Root.class, Fraction.class, MixedNumber.class, Term.class, Expression.class};
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#sign()}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testSign() throws MalformedInputException {
		assertFalse(AlgebraicParticle.getInstance("-2x", new Class[]{Term.class}).sign());
		assertFalse(AlgebraicParticle.getInstance("-v", all).sign());
		assertTrue(AlgebraicParticle.getInstance("₄√(4y+x⁶)", all).sign());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#exponent()}.
	 */
	@Test
	public void testExponent() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#getInstance(java.lang.String, java.lang.Class[])}.
	 * @throws MalformedInputException 
	 */
	@Test
	public void testGetInstance() throws MalformedInputException {
		System.out.println("HERE IT IS!!!!!!" + AlgebraicParticle.getInstance("2x6y", new Class[]{Term.class}).toString());
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
		assertTrue(AlgebraicParticle.parseable("-4⁹", new Class[]{Term.class}));
		//That no class can parse an empty string
		assertFalse(AlgebraicParticle.parseable("", all));
	}

}
