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
public class FactorTest {
	
	Factor f = new Factor();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Factor#execute(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public final void testExecute() {
		assertEquals(a("2(x+3)"), f.execute(a("2x+6")).getChange());
		assertEquals(a("12(2x+1)"), f.execute(a("24x+12")).getChange());
		assertEquals(a("7x³(1+3x)"), f.execute(a("7x³+21x⁴")).getChange());
		assertEquals(a("3(x+6)(5(x+6)⁵+1)"), f.execute(a("15(x+6)⁶+3(x+6)")).getChange());
		assertEquals(a("2(xy+2x³y+3)"), f.execute(a("2xy+4x³y+6")).getChange());
		assertEquals(a("2x²y³(3x³y⁶+2y⁴-6z³ax⁶)").render(), f.execute(a("6x⁵y⁹+4x²y⁷-12z³ay³x⁸")).getChange().render());
		assertEquals(a("x(3+4x²)"), f.execute(a("3x+4x³")).getChange());
		assertEquals(a("x²(6.7+4x)"), f.execute(a("6.7x²+4x³")).getChange());
//		assertEquals(a(""), f.execute(a("")).getChange());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Factor#smarts(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public final void testSmarts() {
		assertEquals(7, f.smarts(a("2x+4")));
		assertEquals(0, f.smarts(a("x³+x-1")));//¹²³⁴⁵⁶⁷⁸⁹
		assertEquals(7, f.smarts(a("3x+4x³")));
		assertEquals(0, f.smarts(a("y⁷+x")));
		assertEquals(9, f.smarts(a("6x⁵y⁹+4x²y⁷-12z³ay³x⁸")));
		assertEquals(9, f.smarts(a("7x³+21x⁴")));
		assertEquals(9, f.smarts(a("15(x+6)⁶+3(x+6)")));
		assertEquals(7, f.smarts(a("2xy+4x³y+6")));
	}
	
}
