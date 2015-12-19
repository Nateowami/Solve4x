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
public class DistributeTest {
	
	final Distribute d = new Distribute(RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS);
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Distribute#execute()}.
	 */
	@Test
	public void testExecute() {
		assertEquals(a("2x+8"), d.execute(a("2(x+4)")).getChange());
		assertEquals(a("27.3x+27.3y"), d.execute(a("(x+y)27.3")).getChange());
		assertEquals(a("x²+2x-4x-8"), d.execute(a("(x-4)(x+2)")).getChange());
		assertEquals(a("8x+2xy"), d.execute(a("2x(4+y)")).getChange());
		assertEquals(a("8z+2y"), d.execute(a("2(4z+y)")).getChange());
		assertEquals(a("-4x+4y"), d.execute(a("(x-y)(-4)")).getChange());
		assertEquals(a("4xz²-24z"), d.execute(a("-4z(x(-z)+6)")).getChange());
		assertEquals(a("-24x⁵+12x²(4+y)"), d.execute(a("-6x²(x³4-2(4+y))")).getChange());
		assertEquals(a("2(x-6)x+8(x-6)"), d.execute(a("(x+4)2(x-6)")).getChange());
		assertEquals(a("x²+6x+4x+24"), d.execute(a("(x+4)(x+6)")).getChange());
		assertEquals(a("4(x+4)²x+24(x+4)²").render(), d.execute(a("(x+4)²(x+6)4")).getChange().render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Distribute#smarts()}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(7, d.smarts(a("2x(2x+6)")));
		assertEquals(7, d.smarts(a("(2x+6)*5x²y")));
		assertEquals(0, d.smarts(a("2((x+4)2)")));
		assertEquals(7, d.smarts(a("(2+3)(x+y)")));
		assertEquals(0, d.smarts(a("16*(x+2)²")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Distribute#insertInTerm(com.github.nateowami.solve4x.solver.Term, com.github.nateowami.solve4x.solver.AlgebraicParticle, int, int)}.
	 */
	@Test
	public void testInsertInTerm() {
		assertEquals(a("2zy"), Distribute.insertInTerm((Term)a("2xy"), a("z"), 1, 1));
		assertEquals(a("2(x²+2x-24)"), Distribute.insertInTerm((Term)a("2(x+6)(x-4)"), a("x²+2x-24"), 1, 2));
		assertEquals(a("8+2x"), Distribute.insertInTerm((Term)a("2(4+x)"), a("8+2x"), 0, 2));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Distribute#multiply()}.
	 */
	@Test
	public void testMultiply() {
		assertEquals(a("2x"), d.multiply(a("2"), a("x")));
	}
	
	AlgebraicParticle a(String s) {
		return AlgebraicParticle.getInstance(s);
	}

}
