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

import org.junit.*;

import com.github.nateowami.solve4x.algorithm.CombineLikeTerms;
import com.github.nateowami.solve4x.config.RoundingRule;

/**
 * @author Nateowami
 */
public class SolverTest {

	/**
	 * Test method for {@link com.github.nateowami.solve4x.solver.Solver#getSolution()}.
	 */
	@Test
	public void testGetSolution() {
		Solver.SolveFor solve = Solver.SolveFor.SOLVE;
		RoundingRule round = RoundingRule.ALWAYS;
		assertEquals(new Equation("4=x"), new Solver("2+2=x", solve, round).getSolution().getLastAlgebraicExpression());
		assertEquals(new Equation("4=x"), new Solver("4=3x-2x", solve, round).getSolution().getLastAlgebraicExpression());
		//assertEquals(new Equation("4=x"), new Solver("2*2=x", solve, round).getSolution().getLastAlgebraicExpression());
	}
	
	@Test
	public void testDispatchAlgorithmWithResource() {
		Equation eq1 = new Equation("2+2=x"), eq2 = new Equation("4=x");
		
		Step step = Solver.dispatchAlgorithmWithResource(new CombineLikeTerms(RoundingRule.ALWAYS), eq1.left(), eq1);
		
		System.out.println(step.getChange().render());
		assertEquals(step.getChange(), eq2.left());
		assertEquals(step.getAlgebraicExpression(), eq2);
	}
	
	@Test
	@Ignore("Not yet implimented.")
	public void testXMadePositive() {
		assertEquals("15=x", new Solver("3x+4-2=17+2x", Solver.SolveFor.SOLVE, RoundingRule.ALWAYS).getSolution().getLastAlgebraicExpression().render());
	}

}
