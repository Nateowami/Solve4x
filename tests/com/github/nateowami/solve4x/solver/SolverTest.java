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

import org.junit.*;

import com.github.nateowami.solve4x.algorithm.CombineLikeTerms;
import com.github.nateowami.solve4x.config.RoundingRule;

/**
 * @author Nateowami
 */
public class SolverTest {
	
	Solver.SolveFor solve = Solver.SolveFor.SOLVE,
					simplify = Solver.SolveFor.SIMPLIFY;
	RoundingRule round = RoundingRule.FOR_SCIENTIFIC_NOTATION;
	
	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Solver#getSolution()}.
	 */
	@Test
	public void testGetSolution() {
		//SOLVE
		assertEquals(new Equation("4=x"), new Solver("2+2=x", solve, round).getSolution().getLastAlgebraicExpression());
		assertEquals(new Equation("4=x"), new Solver("4=3x-2x", solve, round).getSolution().getLastAlgebraicExpression());
		assertEquals(new Equation("4=x"), new Solver("2*2=x", solve, round).getSolution().getLastAlgebraicExpression());
		assertEquals(new Equation("7=x"), new Solver("3+4=x", solve, round).getSolution().getLastAlgebraicExpression());
		assertEquals(new Equation("x=1(1)/(2)"), new Solver("x=(3)/(2)", solve, round).getSolution().getLastAlgebraicExpression());
		assertEquals(new Equation("x=3.75").render(), new Solver("x=(3)/((4)/(5))", solve, round).getSolution().getLastAlgebraicExpression().render());
		
		//SIMPLIFY
		assertEquals(AlgebraicParticle.getInstance("7"), new Solver("3+4", simplify, round).getSolution().getLastAlgebraicExpression());
		assertEquals(AlgebraicParticle.getInstance("2x+7"), new Solver("5x+7-3x", simplify, round).getSolution().getLastAlgebraicExpression());
		
		//PARTIAL SOLVING/SIMPLIFYING
		//note that these will become obsolete as the solver improves
		//TODO Broken. The problem appears to be in the Divide class, though it wasn't changed in the 
		//commit that broke it.
		//assertEquals(new Equation("5=-x³"), new Solver("3x³+5=2x³", solve, round).getSolution().getLastAlgebraicExpression());
	}
	
	@Test
	public void testIsFirstDegreeExpression() {
		assertTrue(Solver.isFirstDegreeExpression((Expression) AlgebraicParticle.getInstance("5x+2")));
		assertTrue(Solver.isFirstDegreeExpression((Expression) AlgebraicParticle.getInstance("5x+2")));
	}
	
	@Test
	@Ignore("Not yet implimented.")
	public void testXMadePositive() {
		assertEquals("15=x", new Solver("3x+4-2=17+2x", solve, round).getSolution().getLastAlgebraicExpression().render());
	}

}
