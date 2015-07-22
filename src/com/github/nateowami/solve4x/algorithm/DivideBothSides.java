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
package com.github.nateowami.solve4x.algorithm;

import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * Divides both sides of an equation by the same value, which hopefully isn't 0. Yeah, we hope. 
 * Works in the following situations:<br>
 * A term with a numeric value on one side, and a number on the other:<br>
 * 2x=5<br>
 * 1/2=(2x)4(x-3)<br>
 * What about situations with numbers on both sides, for example, 2x=4(y-2)? In this case, if they 
 * are both Numbers, both sides are divided by the smaller one.
 * @author Nateowami
 */
public class DivideBothSides extends Algorithm {
	
	public DivideBothSides() {
		super(Equation.class); // Declare that this algorithm operates on equations 
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Equation eq =  (Equation) algebra;
		
		//set "from" and "to" to the sides of the equations we'll be moving from and to
		Term from = (Term) (isNumeric(eq.left()) ? eq.right() : eq.left());
		AlgebraicParticle to = (AlgebraicParticle) (from == eq.left() ? eq.right() : eq.left());
		
		//the number we need to divide by (Watch for signs. In -2x we divide by -2)
		int indexOfNumeric = indexOfNumeric(from);
		AlgebraicParticle numeric = from.get(indexOfNumeric);
		Number divisor = (Number) (from.sign() ? numeric : numeric.cloneWithNewSign(false));
		
		//now calculate the resulting fraction
		Fraction frac = new Fraction(true, to, divisor, 1);
		//and calculate the side we're moving from
		AlgebraicParticle resultingFromSide = unwrap(from.cloneAndRemove(indexOfNumeric));
		//calculate the final equation
		Equation out = new Equation(resultingFromSide, frac);
		
		//create a step and explain
		Step step = new Step(out);
		step.explain("Divide both sides of the equation by ").explain(divisor)
				.explain(". This leaves ").explain(resultingFromSide).explain(" on one side, and ")
				.explain(frac).explain(" on the other.");
		
		return step;
	}

	@Override
	public int smarts(Algebra algebra) {
		Equation eq =  (Equation) algebra;
		//if one side is a term with a numeric part and the other is numeric
		if(indexOfNumeric(eq.left()) != -1 && isNumeric(eq.right()) || isNumeric(eq.left()) && indexOfNumeric(eq.right()) != -1) {
			return 7;
		}
		else return 0;
	}
	
	/**
	 * Finds a numeric item in a (Number, MixedNumber, or constant Fraction) and 
	 * returns its index.
	 * @param a A term to search.
	 * @return The index of the first numeric element in a. If none exist -1 is returned.
	 */
	private int indexOfNumeric(Algebra a) {
		if(a instanceof Term) {
			Term t = (Term) a;
			for(int i = 0; i < t.length(); i++) {
				if(isNumeric(t.get(i))) return i;
			}
		}
		return -1;
	}
	
	/**
	 * Tells whether a is numeric, that is that it is a Number, MixedNumber, or constant Fraction.
	 * @param a The AlgebraicParticle to check.
	 * @return True if a is numeric, otherwise false;
	 */
	private boolean isNumeric(AlgebraicParticle a) {
		return a instanceof Number || a instanceof Fraction  && ((Fraction)a).constant() || a instanceof MixedNumber;
	}
	
}
