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

import com.github.nateowami.solve4x.config.RoundingRule;
import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class Divide extends Algorithm {
	
	private final RoundingRule round;
	
	public Divide(RoundingRule round) {
		super(Fraction.class);
		this.round = round;
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Fraction frac = (Fraction) algebra;
		AlgebraicParticle top = frac.getTop(), bottom = frac.getBottom();
		
		Number numerator = 	getNumber(top), denominator = getNumber(bottom); 
		Number dividend = Number.divide(numerator, denominator, round);
		
		//replace the top with the dividend
		AlgebraicParticle newTop = replace(top, dividend);
		AlgebraicParticle newBottom = bottom instanceof Number ? Number.ONE : ((Term)bottom).cloneAndRemove(numberIndex((Term) bottom));
		
		AlgebraicParticle result = newBottom.equals(Number.ONE) 
				? newTop.cloneWithNewSign(newTop.sign() == frac.sign())
				: new Fraction(frac.sign(), newTop, newBottom, frac.exponent());
		
		Step step = new Step(result);
		step.explain("In the fraction ").explain(frac).explain(" divide ").explain(numerator)
				.explain(" by ").explain(denominator).explain(" to get ").explain(dividend)
				.explain(".").explain("This leaves ").explain(result).explain(".");
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		Fraction frac = (Fraction) algebra;
		//if the top and bottom both have numbers
		if(getNumber(frac.getTop()) != null && getNumber(frac.getBottom()) != null) return 3;
		else return 0;
	}
	
	private AlgebraicParticle replace(AlgebraicParticle a, AlgebraicParticle replacement) {
		return a instanceof Number ? replacement : ((Term)a).cloneWithNewElement(numberIndex((Term)a), replacement);
	}
	
	private int numberIndex(Term t) {
		for(int i = 0; i < t.length(); i++) {
			if(t.get(i) instanceof Number) return i;
		}
		return -1;
	}
	
	private Number getNumber(AlgebraicParticle a) {
		if(a instanceof Number) return (Number) a;
		//iterate over the term looking for decimals
		else if(a instanceof Term) {
			int index = numberIndex((Term)a);
			return index == -1 ? null : (Number) ((Term)a).get(index);
		}
		return null;
	}
		
	private boolean isNumber(AlgebraicParticle a) {
		return a instanceof Number && ((Number)a).getDecimal() != null;
	}
	
}
