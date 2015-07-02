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

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

/**
 * @author Nateowami
 */
public class EquationTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#Equation(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testEquation()  {
		Equation eq = new Equation("23x2y+14x=6(34xy2+7)");
		assertEquals("23x2y+14x", eq.get(0).render());
		assertEquals("6(34xy2+7)", eq.get(1).render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#get(int)}.
	 * @ 
	 */
	@Test
	public void testGetExpression()  {
		Equation eq = new Equation("12x2y(34+6xa)=45+6x(4+85xy5)");
		assertEquals("12x2y(34+6xa)", eq.get(0).render());
		assertEquals("45+6x(4+85xy5)", eq.get(1).render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#length()}.
	 * @ 
	 */
	@Test
	public void testGetSize()  {
		Equation eq = new Equation("12x2y(34+6xa)=45+6x(4+85xy5)");
		assertEquals(2, eq.length());
		Equation eq2 = new Equation("2x+45(45+16x2y)");
		assertEquals(1, eq2.length());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#render()}.
	 * @ 
	 */
	@Test
	public void testrender()  {
		Equation eq = new Equation("12x2y4(34+6xa(43x2+6+1(43)))=45+6x(4+85xy5)");
		assertEquals("12x2y4(34+6xa(43x2+6+1(43)))=45+6x(4+85xy5)", eq.render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#replace()}.
	 * @ 
	 */
	@Test
	public void testReplace()  {
		Equation eq = new Equation("2x+4y=6x(4-3)");
		assertEquals(new Equation("2x+4y=6x(17x-3)"), eq.replace(((Expression)((Term)eq.get(1)).get(2)).get(0), new Term("17x")));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#flattenAndLimitByClass()}.
	 * @ 
	 */
	@Test
	public void testFlattenAndLimitByClass()  {
		Equation eq = new Equation("2x+4y=6x(17x-3)");
		ArrayList<Term> a = new ArrayList<Term>();
		a.add(new Term("2x"));
		a.add(new Term("4y"));
		a.add(new Term("6x(17x-3)"));
		a.add(new Term("17x"));
		assertEquals(a, eq.flattenAndLimitByClass(Term.class));
		
		ArrayList<Term> b = new ArrayList<Term>();
		b.add(new Term("2*2"));
		assertEquals(b, new Equation("2*2=x").flattenAndLimitByClass(Term.class));
	}
	
}
