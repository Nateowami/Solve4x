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

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * @author Nateowami
 */
@RunWith(Suite.class)
@SuiteClasses({ AlgebraicCollectionTest.class, AlgebraicParticleTest.class,
		EquationTest.class, ExpressionTest.class, FractionTest.class,
		MixedNumberTest.class, NumberTest.class, RootTest.class, SolutionTest.class,
		SolverTest.class, TermTest.class, UtilTest.class, VariableTest.class, TreeTest.class })
public class SolverTests {
	
	/**
	 * Constructs a new AlgebraicParticle. This is a convenience method intended for test cases.
	 * @param s The string from which to construct an AlgebraicParticle.
	 * @return A newly constructed AlgebraicParticle.
	 */
	public static AlgebraicParticle a(String s) {
		return AlgebraicParticle.getInstance(s);
	}
	
}
