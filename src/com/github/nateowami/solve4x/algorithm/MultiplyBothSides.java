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

import java.util.ArrayList;

import com.github.nateowami.solve4x.solver.*;

/**
 * @author Nateowami
 */
public class MultiplyBothSides extends Algorithm {
	
	/**
	 * Constructs a new MultiplyBothSides.
	 */
	public MultiplyBothSides() {
		super(Equation.class);
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Equation equation = (Equation) algebra;
		
		// NOTICE: The some of the following was written with a baby guinea pig on my lap, so if 
		// it's totally incoherent, I was probably just a little distracted, or else a little 
		// fellow was helping me type. Never mind. I deleted those lines.
		
		AlgebraicParticle multiply = multiplyBy(equation);
		
		Equation result = new Equation(multiply(equation.left(), multiply), multiply(equation.right(), multiply));
		
		Step step = new Step(result);
		step.explain("Multiply both sides of ").explain(equation).explain(" by ").explain(multiply)
				.explain(" to get ").explain(result.left()).explain(" on the left and ")
				.explain(result.right()).explain(" on the right.");
		
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		Equation equation = (Equation) algebra;
		
		//if there's something good to multiply both sides by
		if(multiplyBy(equation) != null) return 7;
		else return 0;
	}
	
	/**
	 * Tells what to multiply both sides of an equation by. If one side of the equation is a 
	 * fraction and the other is not, both sides should be multiplied by the denominator of the 
	 * fraction. Otherwise if both sides are fractions, choose the first one the has a denominator 
	 * that isn't just a fraction, number, etc. Either way we shouldn't divide by the denominator 
	 * of a fraction that is raised to a power. If there's nothing good to multiply by null will be 
	 * returned. 
	 * @param eq The equation to analyze.
	 * @return The AlgebraicParticle to multiply both sides by.
	 */
	private AlgebraicParticle multiplyBy(Equation eq) {
		AlgebraicParticle left = eq.left(), right = eq.right();
		if(left instanceof Fraction && right instanceof Fraction && left.exponent() == 1 && right.exponent() == 1) {
			Fraction fracLeft = (Fraction) left, fracRight = (Fraction) right;
			//if the bottom left is constant multiply both sides by bottom right
			if(Util.constant(fracLeft.getBottom())) return fracRight.getBottom();
			else return fracLeft.getBottom();
		}
		else if(left instanceof Fraction && left.exponent() == 1) return ((Fraction)left).getBottom();
		else if(right instanceof Fraction && right.exponent() == 1) return ((Fraction)right).getBottom();
		else return null;
	}
	
	/**
	 * Multiplies a and b. If a is a fraction and b is its denominator the top of b will be 
	 * returned (with any necessary adjustments to the sign). Otherwise a and b are combined into 
	 * one term and any necessary adjustments to the sign are made.
	 * @param a The first AlgebraicParticle term to multiply.
	 * @param b The second AlgebraicParticle term to multiply.
	 * @return a multiplied by b.
	 */
	private AlgebraicParticle multiply(AlgebraicParticle a, AlgebraicParticle b) {
		//if a is a fraction and b is the bottom of that fraction
		if(a instanceof Fraction && ((Fraction)a).getBottom().equals(b)) {
			Fraction frac = (Fraction) a;
			//return the top of the fraction, making sure it's got the right sign
			return frac.getTop().cloneWithNewSign(a.sign() == frac.getTop().sign());
		}
		//if a is a term add b to the end of it
		else {
			ArrayList<AlgebraicParticle> aList = new ArrayList<AlgebraicParticle>(), bList = new ArrayList<AlgebraicParticle>();
			if(a instanceof Term && a.exponent() == 1) aList = ((Term)a).toList();
			else aList.add(a.cloneWithNewSign(true));
			if(b instanceof Term && b.exponent() == 1) bList = ((Term)b).toList();
			else bList.add(b.cloneWithNewSign(true));
			aList.addAll(bList);
			return new Term(a.sign() == b.sign(), aList, 1);
		}
	}
	
}
