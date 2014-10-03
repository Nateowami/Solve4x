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

import com.github.nateowami.solve4x.solver.Expression;

/**
 * @author Nateowami
 */
public class ExpressionTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#Expression(java.lang.String)}.
	 * @ 
	 */
	@Test
	public void testExpression()  {
		Expression expr0 = new Expression("6+2");
		assertEquals("6", expr0.termAt(0).getAsString());
		assertEquals("2", expr0.termAt(1).getAsString());
		
		Expression exprx = new Expression("2x+6(34)");
		assertEquals("2x", exprx.termAt(0).getAsString());
		assertEquals("6(34)", exprx.termAt(1).getAsString());
		
		Expression expr1 = new Expression("2x+6(34+9xy2(45x+6))");
		assertEquals("2x", expr1.termAt(0).getAsString());
		assertEquals("6(34+9xy2(45x+6))", expr1.termAt(1).getAsString());
		
		Expression expr2 = new Expression("x+6((34)/(6x))+67");
		assertTrue(expr2.termAt(0).getAsString().equals("x"));
		assertTrue(expr2.termAt(1).getAsString().equals("6((34)/(6x))"));
		assertTrue(expr2.termAt(2).getAsString().equals("67"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#numbOfTerms()}.
	 * @ 
	 */
	@Test
	public void testNumbOfTerms()  {
		Expression expr1 = new Expression("2x+45+6xy45+12(23+6xy4)");
		assertTrue(expr1.numbOfTerms()==4);
		Expression expr2 = new Expression("-c+(y)/(16+34)-xy2");
		assertTrue(expr2.numbOfTerms()==3);
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#termAt(int)}.
	 * @ 
	 */
	@Test
	public void testTermAt()  {
		Expression expr1 = new Expression("2x+45+6xy45+12(23+6xy4)");
		assertTrue(expr1.termAt(0).getAsString().equals("2x"));
		assertTrue(expr1.termAt(1).getAsString().equals("45"));
		assertTrue(expr1.termAt(2).getAsString().equals("6xy45"));
		assertTrue(expr1.termAt(3).getAsString().equals("12(23+6xy4)"));
		Expression expr2 = new Expression("-c+(y)/(16+34)-xy2");
		assertTrue(expr2.termAt(0).equals("-c"));
		assertTrue(expr2.termAt(1).equals("(y)/(16+34)"));
		assertTrue(expr2.termAt(2).equals("-xy2"));
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		Expression expr0 = new Expression("1x+45");
		Expression expr1 = new Expression("1x+45+6xy45+12(23+6xy4)");
		assertTrue(expr1.getAsString().equals("x+45+6xy45+12(23+6xy4)"));
		Expression expr2 = new Expression("-c+(y)/(16+34)-xy2");
		assertTrue(expr2.getAsString().equals("-c+(y)/(16+34)-xy2"));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#parseable()}.
	 * @ 
	 */
	@Test
	public void testParseable()  {
		assertFalse(Expression.parseable("6(34+9xy2(45x+6)"));
	}

}
