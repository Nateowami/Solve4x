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
package com.github.nateowami.solve4x.algorithm;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.github.nateowami.solve4x.solver.AlgebraicParticle;

/**
 * @author Nateowami
 */
@RunWith(Suite.class)
@SuiteClasses({ ChangeSidesTest.class, CombineLikeTermsTest.class, 
			MultiplyTest.class, DivideBothSidesTest.class, DistributeTest.class,
			CancelFactorsTest.class, MultiplyBothSidesTest.class, ConvertToMixedNumberTest.class,
			DivideTest.class, InvertAndMultiplyTest.class, FactorTest.class })
public class AlgorithmTests {
	
	/**
	 * Constructs a new AlgebraicParticle. This is a convenience method intended for test cases.
	 * @param s The string from which to construct an AlgebraicParticle.
	 * @return A newly constructed AlgebraicParticle.
	 */
	public static AlgebraicParticle a(String s) {
		return AlgebraicParticle.getInstance(s);
	}
	
}
