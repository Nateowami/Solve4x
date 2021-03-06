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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

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
		assertEquals("6", expr0.get(0).render());
		assertEquals("2", expr0.get(1).render());
		
		Expression exprx = new Expression("2x+6(34)");
		assertEquals("2x", exprx.get(0).render());
		assertEquals("6(34)", exprx.get(1).render());
		
		Expression expr1 = new Expression("2x+6(34+9xy2(45x+6))");
		assertEquals("2x", expr1.get(0).render());
		assertEquals("6(34+9xy2(45x+6))", expr1.get(1).render());
		
		Expression expr2 = new Expression("x+6((34)/(6x))+67");
		assertEquals("x", expr2.get(0).render());
		assertEquals("6((34)/(6x))", expr2.get(1).render());
		assertEquals("67", expr2.get(2).render());
		
		assertEquals("(6+4)⁴+8(3)", new Expression("(6+4)⁴+8*3").render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#length()}.
	 * @ 
	 */
	@Test
	public void testNumbOfTerms()  {
		Expression expr1 = new Expression("2x+45+6xy45+12(23+6xy4)");
		assertEquals(4, expr1.length());
		Expression expr2 = new Expression("-c+(y)/(16+34)-xy2");
		assertEquals(3, expr2.length());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#get(int)}.
	 * @ 
	 */
	@Test
	public void testTermAt()  {
		Expression expr1 = new Expression("2x+45+6xy45+12(23+6xy4)");
		assertEquals("2x", expr1.get(0).render());
		assertEquals("45", expr1.get(1).render());
		assertEquals("6xy45", expr1.get(2).render());
		assertEquals("12(23+6xy4)", expr1.get(3).render());
		Expression expr2 = new Expression("-c+(y)/(16+34)-xy2");
		assertEquals("-c", expr2.get(0).render());
		assertEquals("(y)/(16+34)", expr2.get(1).render());
		assertEquals("-xy2", expr2.get(2).render());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#render()}.
	 * @ 
	 */
	@Test
	public void testrender()  {
		assertEquals("1+2", new Expression("1+2").render());
		assertEquals("-y+6", new Expression("-y+6").render());
		assertEquals("1x+45", new Expression("1x+45").render());
		assertEquals("x+45+6xy45+12(23+6xy4)", new Expression("x+45+6xy45+12(23+6xy4)").render());
		assertEquals("-c+(y)/(16+34)-xy2", new Expression("-c+(y)/(16+34)-xy2").render());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#parsable()}.
	 * @ 
	 */
	@Test
	public void testparsable()  {
		assertTrue(Expression.parsable("6+2"));
		assertTrue(Expression.parsable("(6+4)⁴+8*3"));
		assertFalse(Expression.parsable("6(34+9xy2(45x+6)"));
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#cloneAndRemove()}.
	 * @ 
	 */
	@Test
	public void testCloneAndRemove()  {
		assertEquals(new Expression("2+x+y"), new Expression("2+t+x+y").cloneAndRemove(1));
	}
	
}
