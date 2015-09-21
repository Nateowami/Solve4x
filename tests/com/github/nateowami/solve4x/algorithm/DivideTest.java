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

import com.github.nateowami.solve4x.config.RoundingRule;

/**
 * @author Nateowami
 */
public class DivideTest {
	
	Divide d = new Divide(RoundingRule.FOR_SCIENTIFIC_NOTATION);
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Divide#execute(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testExecute() {
		assertEquals(a("0.75"), d.execute(a("(3)/(4)")).getChange());
		assertEquals(a("5.33"), d.execute(a("(12.8)/(2.40)")).getChange());
		assertEquals(a("0.75"), d.execute(a("(3)/(4)")).getChange());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Divide#smarts(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(3, d.smarts(a("(2.3)/(5.4)")));
		assertEquals(3, d.smarts(a("(2.3)/(5)")));
		assertEquals(3, d.smarts(a("((4xy+6)2.3x)/(5xy⁵)")));
		assertEquals(3, d.smarts(a("(4y*47*6)/(16.3)")));
		assertEquals(3, d.smarts(a("(4)/(5)")));
		
		assertEquals(0, d.smarts(a("(4y*47+6)/(16.3)")));
		assertEquals(0, d.smarts(a("(5)/(x(16.3+6))")));
	}
	
}
