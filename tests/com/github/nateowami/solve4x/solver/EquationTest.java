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

import static org.junit.Assert.*;



import org.junit.Test;

import com.github.nateowami.solve4x.solver.Equation;
import com.github.nateowami.solve4x.solver.Expression;

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
		assertTrue(eq.getExpression(0).getAsString().equals("23x2y+14x"));
		assertTrue(eq.getExpression(1).getAsString().equals("6(34xy2+7)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#getExpression(int)}.
	 * @ 
	 */
	@Test
	public void testGetExpression()  {
		Equation eq = new Equation("12x2y(34+6xa)=45+6x(4+85xy5)");
		assertTrue(eq.getExpression(0).getAsString().equals("12x2y(34+6xa)"));
		assertTrue(eq.getExpression(1).getAsString().equals("45+6x(4+85xy5)"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#getSize()}.
	 * @ 
	 */
	@Test
	public void testGetSize()  {
		Equation eq = new Equation("12x2y(34+6xa)=45+6x(4+85xy5)");
		assertTrue(eq.getSize() == 2);
		Equation eq2 = new Equation("2x+45(45+16x2y)");
		assertTrue(eq2.getSize() == 1);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#setExpression(java.lang.String, int)}.
	 * @ 
	 */
	@Test
	public void testSetExpression()  {
		Equation eq = new Equation("12x2y(34+6xa)=45+6x(4+85xy5)");
		eq.setExpression("23x+6", 0);
		eq.setExpression("s+yz", 1);
		assertTrue(eq.getExpression(0).getAsString().equals("23x+6"));
		assertTrue(eq.getExpression(1).getAsString().equals("s+yz"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Equation#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		Equation eq = new Equation("12x2y4(34+6xa(43x2+6+1(43)))=45+6x(4+85xy5)");
		assertTrue(eq.getAsString().equals("12x2y4(34+6xa(43x2+6+(43)))=45+6x(4+85xy5)"));
	}

}
