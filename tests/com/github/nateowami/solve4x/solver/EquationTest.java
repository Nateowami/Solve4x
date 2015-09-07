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
public class EquationTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#Equation(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testEquation()  {
		Equation eq = new Equation("23x2y+14x=6(34xy2+7)");
		assertEquals("23x2y+14x", eq.left().render());
		assertEquals("6(34xy2+7)", eq.right().render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#render()}.
	 * @ 
	 */
	@Test
	public void testRender()  {
		Equation eq = new Equation("12x2y4(34+6xa(43x2+6+1(43)))=45+6x(4+85xy5)");
		assertEquals("12x2y4(34+6xa(43x2+6+1(43)))=45+6x(4+85xy5)", eq.render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#parsable(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testParsable()  {
		assertTrue(Equation.parsable("2x=4"));
		assertFalse(Equation.parsable("(2(xy-14)z)/((xy-14)6z)"));
	}
	
}
