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
public class SolutionTest {

	@Test
	public void testSolution() {
		Solution solution1 = new Solution(a("1"));
		Solution solution2 = new Solution(solution1);
		solution2.addStep(new Step(a("2")));
		assertEquals(0, solution1.length());
	}
	
	//alias
	private static AlgebraicParticle a(String s) {
		return AlgebraicParticle.getInstance(s);
	}
	
}
