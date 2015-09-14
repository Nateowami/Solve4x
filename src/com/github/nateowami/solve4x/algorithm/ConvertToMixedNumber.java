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

import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class ConvertToMixedNumber extends Algorithm {
	
	public ConvertToMixedNumber() {
		super(Fraction.class);
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Fraction frac = (Fraction) algebra;
		long top = Integer.parseInt(((Number)frac.getTop()).getInteger());
		long bottom = Integer.parseInt(((Number)frac.getBottom()).getInteger());
		
		//calculate the new numerator and the integer that will be added to the fraction
		long front = top / bottom;
		top = top % bottom;
		
		Fraction properFraction = new Fraction(true, number(top), number(bottom), 1);
		boolean sign = frac.sign() == (frac.getTop().sign() == frac.getBottom().sign());
		MixedNumber mn = new MixedNumber(sign, number(front), properFraction, frac.exponent());
		
		Step step = new Step(mn);
		step.explain("Convert ").explain(frac).explain(" to a mixed number. ")
				.explain(frac.getBottom()).explain(" goes into ").explain(frac.getTop())
				.explain(number(front)).explain((front == 1 ? "time" : "times") +" leaving ")
				.explain(mn.getFraction().getTop()).explain(".");
		
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		Fraction frac = (Fraction) algebra;
		//if the fraction is a positive integer on top and bottom, but it's not fully simplified
		if(frac.getTop() instanceof Number && frac.getBottom() instanceof Number && 
				((Number)frac.getTop()).isInteger() && ((Number)frac.getBottom()).isInteger() && !frac.isSimplified()) {
			Number top = (Number) frac.getTop(), bottom = (Number) frac.getBottom();
			//if top and bottom has no common factors
			if(Number.GCF(Long.parseLong(top.getInteger()), Long.parseLong(bottom.getInteger())) == 1) return 3;
			else return 0;
		}
		else return 0;
	}
	
	private static Number number(long l) {
		return new Number(true, Long.toString(l), null, null, 1);
	}
	
}
