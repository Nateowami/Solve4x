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
		assertEquals("4=x", new Solver("2*2=x", Solver.SolveFor.SOLVE, RoundingRule.ALWAYS).getSolution().getLastEquation().render());
	}
	
	@Test
	@Ignore("Not yet implimented.")
	public void testXMadePositive() {
		assertEquals("15=x", new Solver("3x+4-2=17+2x", Solver.SolveFor.SOLVE, RoundingRule.ALWAYS).getSolution().getLastEquation().render());
	}

}
