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

/**
 * @author Nateowami
 */
public class AlgebraicCollectionTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.AlgebraicCollection#cloneWithNewElement(int, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 */
	@Test
	public void testCloneWithNewElement() {
		assertEquals("2x+3", ((Expression)a("2x+6")).cloneWithNewElement(1, a("3")).render());
		assertEquals("2x3", ((Term)a("2y3")).cloneWithNewElement(1, a("x")).render());
	}
	
	//helper method
	AlgebraicParticle a(String s){
		return AlgebraicParticle.getInstance(s);
	}
	
}
