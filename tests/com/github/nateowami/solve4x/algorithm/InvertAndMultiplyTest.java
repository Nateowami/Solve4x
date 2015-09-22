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
package com.github.nateowami.solve4x.algorithm;

import static org.junit.Assert.*;

import org.junit.Test;

import static com.github.nateowami.solve4x.algorithm.AlgorithmTests.*;

/**
 * @author Nateowami
 */
public class InvertAndMultiplyTest {
	
	InvertAndMultiply i = new InvertAndMultiply();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.InvertAndMultiply#execute(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public final void testExecute() {
		assertEquals(a("5*(7)/(3)"), i.execute(a("(5)/((3)/(7))")).getChange());
		assertEquals(a("(2)/(9)*(4)/(x)"), i.execute(a("((2)/(9))/((x)/(4))")).getChange());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.InvertAndMultiply#smarts(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public final void testSmarts() {
		assertEquals(0, i.smarts(a("(2)/(30)")));
		assertEquals(0, i.smarts(a("2*27.6x")));
		assertEquals(9, i.smarts(a("(2)/((30)/(4))")));
	}
	
}
