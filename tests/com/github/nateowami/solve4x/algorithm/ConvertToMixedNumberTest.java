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

import com.github.nateowami.solve4x.solver.AlgebraicParticle;

/**
 * @author Nateowami
 */
public class ConvertToMixedNumberTest {
	
	ConvertToMixedNumber c = new ConvertToMixedNumber(); 
	
	@Test
	public void testExecute() {
		assertEquals(a("1(1)/(3)"), c.execute(a("(4)/(3)")).getChange());
		assertEquals(a("7(3)/(4)"), c.execute(a("(31)/(4)")).getChange());
		assertEquals(a("1(2)/(3)"), c.execute(a("(5)/(3)")).getChange());
		assertEquals(a("1(1)/(2)"), c.execute(a("(3)/(2)")).getChange());
		assertEquals(a("1(1)/(2)"), c.execute(a("-(3)/(-2)")).getChange());
	}
	
	@Test
	public void testSmarts() {
		assertEquals(3, c.smarts(a("(5)/(3)")));
		assertEquals(3, c.smarts(a("(3)/(2)")));
		assertEquals(0, c.smarts(a("(4)/(20)")));
		assertEquals(3, c.smarts(a("(-31)/(4)")));
		assertEquals(0, c.smarts(a("(9)/(6)")));
		assertEquals(0, c.smarts(a("(5)/(6)")));
		assertEquals(0, c.smarts(a("(7.5)/(6)")));
	}
	
	AlgebraicParticle a(String s) {
		return AlgebraicParticle.getInstance(s);
	}
	
}
