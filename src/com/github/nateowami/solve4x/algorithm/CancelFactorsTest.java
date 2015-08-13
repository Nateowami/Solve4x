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

import com.github.nateowami.solve4x.config.RoundingRule;
import com.github.nateowami.solve4x.solver.*;

/**
 * @author Nateowami
 */
public class CancelFactorsTest {
	
	CancelFactors d = new CancelFactors();
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Divide#execute(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testExecute() {
		assertEquals(a("(1)/(x)"), d.execute(a("(2)/(2x)")).getChange());
		assertEquals(a("((x-4)³)/(2)"), d.execute(a("(2(x-4)³)/(4)")).getChange());
		assertEquals(a("(1)/(6)"), d.execute(a("(6)/(36)")).getChange());
		assertEquals(a("8"), d.execute(a("(24)/(3)")).getChange());
		assertEquals(a("8y³"), d.execute(a("(24y³)/(3)")).getChange());
		assertEquals(a("-6"), d.execute(a("(30)/(-5)")).getChange());
		assertEquals(a("(6y)/(z)"), d.execute(a("-(30x(-y)1)/(-5x(-z))")).getChange());
		assertEquals(a("(1)/(3)"), d.execute(a("(2(xy-14)z)/((xy-14)6z)")).getChange());
		assertEquals(a("5*1.2"), d.execute(a("(25x1.2)/(5x)")).getChange());
		assertEquals(a("-4"), d.execute(a("(4)/(-1)")).getChange());
		assertEquals(a("4"), d.execute(a("(4)/(1)")).getChange());
		assertEquals(a("4"), d.execute(a("-(4)/(-1)")).getChange());
		assertEquals(a("4.2x²").render(), d.execute(a("(4.2x³)/(x)")).getChange().render());
		assertEquals(a("(x+y)/(4)").render(), d.execute(a("(1(x+y)z)/(4z)")).getChange().render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Divide#smarts(com.github.nateowami.solve4x.solver.Algebra)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(7, d.smarts(a("(2)/(4)")));
		assertEquals(7, d.smarts(a("(2x)/(2)")));
		assertEquals(7, d.smarts(a("(2(x+4))/(x+4)")));
		assertEquals(7, d.smarts(a("(34xy³)/(ay(4+x))")));
		assertEquals(7, d.smarts(a("(2xyz24)/(12)")));
		assertEquals(7, d.smarts(a("(2xyz24)/(3zbc)")));
		assertEquals(7, d.smarts(a("(4)/(1)")));
		assertEquals(7, d.smarts(a("(4xy(32-7³))/(-1)")));
		
		assertEquals(0, d.smarts(a("(34xy³)/(az(4+x))")));
		assertEquals(0, d.smarts(a("(5)/(3)")));
		assertEquals(0, d.smarts(a("(5)/(8)")));
	}
	
	AlgebraicParticle a(String s) {
		return AlgebraicParticle.getInstance(s);
	}
	
}
