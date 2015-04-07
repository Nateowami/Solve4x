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
public class AlgebraicCollectionTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicCollection#cloneWithNewElement(int, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@Test
	public void testCloneWithNewElement() {
		assertEquals("2x+3", ((Expression)a("2x+6")).cloneWithNewElement(1, a("3")).getAsString());
		assertEquals("2x3", ((Term)a("2y3")).cloneWithNewElement(1, a("x")).getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicCollection#replace(com.github.nateowami.solve4x.solver.AlgebraicCollection, com.github.nateowami.solve4x.solver.AlgebraicParticle, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@Test
	public void testReplace() {
		Term t = (Term) a("2x(x+3)");
		assertEquals("2x(y+3)", t.replace(((AlgebraicCollection)t.get(2)).get(0), a("y")).getAsString());
		Expression e = (Expression) a("2x+4(x+6)");
		assertEquals("2x+4(x+z)", e.replace(((AlgebraicCollection)((AlgebraicCollection)e.get(1)).get(1)).get(1), a("z")).getAsString());
		assertEquals("4xy+4(x+6)", e.replace(((AlgebraicCollection)e.get(0)), a("4xy")).getAsString());
		assertEquals("2x+7(z+6)", e.replace(((AlgebraicCollection)e.get(1)), a("7(z+6)")).getAsString());
		Term t2 = new Term("5x");
		assertEquals("x", t2.replace(t2, a("x")).getAsString());		
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicCollection#flatten()}.
	 */
	@Test
	public void testFlatten() {
		Term t = (Term) a("2x(x+3)");
		assertArrayEquals(new AlgebraicParticle[]{a("2x(x+3)"), a("2"), a("x"), a("x+3"), a("x"), a("3")}, t.flatten());
		Expression e = (Expression) a("2x+4(x+6)");
		assertArrayEquals(new AlgebraicParticle[]{a("2x+4(x+6)"), a("2x"), a("2"), a("x"), a("4(x+6)"), a("4"), a("x+6"), a("x"), a("6")}, e.flatten());
		assertArrayEquals(new AlgebraicParticle[]{a("2x"), a("2"), a("x")}, ((Term)a("2x")).flatten());
	}

	//helper method
	AlgebraicParticle a(String s){
		return AlgebraicParticle.getInstance(s);
	}
	
}
