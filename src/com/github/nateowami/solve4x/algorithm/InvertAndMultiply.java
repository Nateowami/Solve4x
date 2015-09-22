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

import static com.github.nateowami.solve4x.solver.Util.*;

/**
 * @author Nateowami
 */
public class InvertAndMultiply extends Algorithm {
	
	public InvertAndMultiply() {
		super(Fraction.class);
	}
	
	@Override
	public Step execute(Algebra algebra) {
		//take the bottom of the fraction, invert it, and multiply it by the top
		Fraction frac = (Fraction) algebra;
		Fraction bottom = (Fraction) frac.getBottom();
		Fraction inverted = new Fraction(bottom.sign(), bottom.getBottom(), bottom.getTop(), bottom.exponent());
		Term term = new Term(frac.sign(), list(frac.getTop(), inverted), frac.exponent());
		
		Step step = new Step(term);
		step.explain("In the fraction ").explain(frac).explain(" invert ").explain(bottom).explain(" and multiply.");
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		//if it's a fraction with a fraction on the bottom
		return algebra instanceof Fraction && ((Fraction)algebra).getBottom() instanceof Fraction ? 9 : 0;
	}
	
}
