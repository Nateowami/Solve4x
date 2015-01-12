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
		assertEquals("6", expr0.termAt(0).getAsString());
		assertEquals("2", expr0.termAt(1).getAsString());
		
		Expression exprx = new Expression("2x+6(34)");
		assertEquals("2x", exprx.termAt(0).getAsString());
		assertEquals("6(34)", exprx.termAt(1).getAsString());
		
		Expression expr1 = new Expression("2x+6(34+9xy2(45x+6))");
		assertEquals("2x", expr1.termAt(0).getAsString());
		assertEquals("6(34+9xy2(45x+6))", expr1.termAt(1).getAsString());
		
		Expression expr2 = new Expression("x+6((34)/(6x))+67");
		assertEquals("x", expr2.termAt(0).getAsString());
		assertEquals("6((34)/(6x))", expr2.termAt(1).getAsString());
		assertEquals("67", expr2.termAt(2).getAsString());
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
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#termAt(int)}.
	 * @ 
	 */
	@Test
	public void testTermAt()  {
		Expression expr1 = new Expression("2x+45+6xy45+12(23+6xy4)");
		assertEquals("2x", expr1.termAt(0).getAsString());
		assertEquals("45", expr1.termAt(1).getAsString());
		assertEquals("6xy45", expr1.termAt(2).getAsString());
		assertEquals("12(23+6xy4)", expr1.termAt(3).getAsString());
		Expression expr2 = new Expression("-c+(y)/(16+34)-xy2");
		assertEquals("-c", expr2.termAt(0).getAsString());
		assertEquals("(y)/(16+34)", expr2.termAt(1).getAsString());
		assertEquals("-xy2", expr2.termAt(2).getAsString());
	}

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#getAsString()}.
	 * @ 
	 */
	@Test
	public void testGetAsString()  {
		assertEquals("1+2", new Expression("1+2").getAsString());
		assertEquals("-y+6", new Expression("-y+6").getAsString());
		assertEquals("1x+45", new Expression("1x+45").getAsString());
		assertEquals("x+45+6xy45+12(23+6xy4)", new Expression("x+45+6xy45+12(23+6xy4)").getAsString());
		assertEquals("-c+(y)/(16+34)-xy2", new Expression("-c+(y)/(16+34)-xy2").getAsString());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#parseable()}.
	 * @ 
	 */
	@Test
	public void testParseable()  {
		assertTrue(Expression.parseable("6+2"));
		assertFalse(Expression.parseable("6(34+9xy2(45x+6)"));
	}
	
	/** 
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#parseable()}.
	 * @ 
	 */
	@Test
	public void testcombinableTerms()  {
		Expression e1 = new Expression("2x²+4x-2.3x+12");
		ArrayList<AlgebraicParticle> a1 = new ArrayList<AlgebraicParticle>(Arrays.asList(AlgebraicParticle.getInstance("2x²")));
		ArrayList<AlgebraicParticle> a2 = new ArrayList<AlgebraicParticle>(Arrays.asList(AlgebraicParticle.getInstance("+4x"), AlgebraicParticle.getInstance("-2.3x")));
		ArrayList<AlgebraicParticle> a3 = new ArrayList<AlgebraicParticle>(Arrays.asList(AlgebraicParticle.getInstance("+12")));
		ArrayList<ArrayList<AlgebraicParticle>> one = new ArrayList(Arrays.asList(a1, a2, a3)), two = e1.combinableTerms();
		
		assertEquals(new ArrayList(Arrays.asList(a1, a2, a3)).toString().length(), e1.combinableTerms().toString().length());
		assertEquals(new ArrayList(Arrays.asList(a1, a2, a3)), e1.combinableTerms());
	}
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Expression#combinableTerms(com.github.nateowami.solve4x.solver.AlgebraicParticle, com.github.nateowami.solve4x.solver.AlgebraicParticle)}.
	 * @ 
	 */
	@Test
	public void testcombinableTermsAlgebraicParticleAlgebraicParticle()  {
		//check variables
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("a"), AlgebraicParticle.getInstance("a²")));
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("x³"), AlgebraicParticle.getInstance("x")));
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("x"), AlgebraicParticle.getInstance("a")));
		
		//check constants such as numbers, mixed numbers, and constant fractions
		assertTrue(Expression.combinableTerms(AlgebraicParticle.getInstance("4"), AlgebraicParticle.getInstance("17.3")));
		assertTrue(Expression.combinableTerms(AlgebraicParticle.getInstance("(4)/(7)"), AlgebraicParticle.getInstance("(5)/(7)")));
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("4(2)/(3)"), AlgebraicParticle.getInstance("1(7)/(16)")));
		assertTrue(Expression.combinableTerms(AlgebraicParticle.getInstance("2(4)/(5)"), AlgebraicParticle.getInstance("17.3")));
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("(4)/(x)"), AlgebraicParticle.getInstance("12")));
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("23.2"), AlgebraicParticle.getInstance("(x+4)/(16)")));
		
		assertTrue(Expression.combinableTerms(AlgebraicParticle.getInstance("4x"), AlgebraicParticle.getInstance("17.3x")));
		assertTrue(Expression.combinableTerms(AlgebraicParticle.getInstance("2y(3x-6)((4)/(5))"), AlgebraicParticle.getInstance("9.2y(3x-6)((4)/(5))")));
		assertFalse(Expression.combinableTerms(AlgebraicParticle.getInstance("2y(3-6)((4)/(5))"), AlgebraicParticle.getInstance("9.2y(3x-6)((4)/(5))")));
	}

}
