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

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicParticle#sign()}.
	 */
	@Test
	public void testSign() {
		fail("Not yet implemented"); // TODO
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
		assertTrue(AlgebraicParticle.parseable("2x", new Class[]{Term.class}));
		assertTrue(AlgebraicParticle.parseable("2x6y", new Class[]{Term.class}));
		assertTrue(AlgebraicParticle.parseable("2x⁹", new Class[]{Term.class}));
		assertTrue(AlgebraicParticle.parseable("-2x", new Class[]{Term.class}));
	}

}
