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
package com.github.nateowami.solve4x.algorithm;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

import com.github.nateowami.solve4x.config.RoundingRule;
import com.github.nateowami.solve4x.solver.*;

/**
 * @author Nateowami
 */
public class MultiplyTest {
	
	Multiply m = new Multiply(RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS);
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Multiply#execute(com.github.nateowami.solve4x.solver.Equation)}.
	 */
	@Test
	public void testExecute() {
		assertEquals(a("2x²"), m.execute(new Equation("2xx")).getEquation().terms().get(0));
		assertEquals(a("4"), m.execute(new Equation("2*2")).getEquation().left());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Multiply#smarts(com.github.nateowami.solve4x.solver.Equation)}.
	 */
	@Test
	public void testSmarts() {
		assertEquals(9, m.smarts(new Equation("2(4)(8)")));
		assertEquals(7, m.smarts(new Equation("2+x(y-4)2x7y")));
		assertEquals(7, m.smarts(new Equation("2*2")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Multiply#multipliable(com.github.nateowami.solve4x.solver.AlgebraicParticle a, com.github.nateowami.solve4x.solver.AlgebraicParticle b)}.
	 */
	@Test
	public void testMultipliable() {
		assertTrue(m.multipliable(a("2"), a("3")));
		assertTrue(m.multipliable(a("x"), a("x")));
		assertFalse(m.multipliable(a("2"), a("x")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.algorithm.Multiply#multipliableGroups(com.github.nateowami.solve4x.solver.Term t)}.
	 */
	@Test
	public void testMultipliableGroups() {
		ArrayList<ArrayList<AlgebraicParticle>> list = new ArrayList<ArrayList<AlgebraicParticle>>();
		ArrayList<AlgebraicParticle> first = new ArrayList<AlgebraicParticle>();
		first.add(a("2"));
		list.add(first);
		ArrayList<AlgebraicParticle> second = new ArrayList<AlgebraicParticle>();
		second.add(a("x"));
		second.add(a("x"));
		list.add(second);
		
		assertEquals(list, Multiply.multipliableGroups((Term)a("2xx")));
	}
	
	AlgebraicParticle a(String s){
		return AlgebraicParticle.getInstance(s);
	}

}
