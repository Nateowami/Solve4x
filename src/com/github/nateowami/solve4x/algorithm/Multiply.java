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

import java.util.ArrayList;

import com.github.nateowami.solve4x.config.RoundingRule;
import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class Multiply extends Algorithm {

	private final RoundingRule roundingRule;
	
	/**
	 * Constructs a new Multiply algorithm.
	 * @param r The RoundingRule to use for arithemetic operations.
	 */
	public Multiply(RoundingRule r){
		super(Term.class); // declare that this algorithm works on terms
		this.roundingRule = r;
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Term term = (Term) algebra;
		ArrayList<ArrayList<AlgebraicParticle>> groups = multipliableGroups(term);
		
		//term is now the term to work on, and groups is the list of combinable AlgebraicParticles
		Term multiplied = multiply(term, groups);
		
		Step step = new Step(unwrap(multiplied));
		step.explain("In the term ").explain(term).explain(" we neeed to multiply.\n");
		for(int i = 0; i < groups.size(); i++){
			if(groups.get(i).size() > 1){
				step.explain("Multiply ").list(groups.get(i)).explain(" to get ").explain(multiplied.get(i)).explain(".\n");
			}
		}
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		Term term = (Term) algebra;
		int combinable = term.length() - multipliableGroups(term).size();
		if(combinable == 0) return 0;
		else if(combinable == 1) return 7;
		else return 9;
	}
	
	/**
	 * Creates an ArrayList of ArrayLists of AlgebraicParticles, showing which AlgebraicParticles 
	 * may be multiplied with each other. Each ArrayList of AlgebraicParticles contains one group 
	 * of AlgebraicParticles that may be multiplied with each other. For example, if t were 2xy4x, 
	 * the returned ArrayList would look similar to the following: <br>
	 * 2 4
	 * x x
	 * y
	 * @param t A term from which to find AlgebraicParticles that may be combined.
	 * @return A structure showing which AlgebraicParticles may be combined with each other.
	 */
	static ArrayList<ArrayList<AlgebraicParticle>> multipliableGroups(Term t){
		ArrayList<ArrayList<AlgebraicParticle>> list = new ArrayList<ArrayList<AlgebraicParticle>>();
		bigloop:
		for(int i = 0; i < t.length(); i++){
			for(ArrayList<AlgebraicParticle> a : list){
				if(multipliable(a.get(0), t.get(i))){
					a.add(t.get(i));
					continue bigloop;
				}
			}
			//else it wasn't multipliable with any of the others
			ArrayList<AlgebraicParticle> single = new ArrayList<AlgebraicParticle>();
			single.add(t.get(i));
			list.add(single);
		}
		return list;
	}
	
	/**
	 * Tells if a and b may be multiplied together. This is possible when either they are both numbers, 
	 * fractions (with numbers on top and bottom), or when they are are both the same 
	 * without regard for sings or exponents (e.g. 2x and -(2x)^2). MixedNumbers are not considered 
	 * multipliable.
	 * @param a The first AlgebraicParticle to compare.
	 * @param b The second AlgebraicParticle to compare.
	 * @return True if a and be may be multiplied, otherwise false.
	 */
	static boolean multipliable(AlgebraicParticle a, AlgebraicParticle b){
		return a.getClass().equals(b.getClass()) && a.almostEquals(b) 
															//mixed number need to be converted to fractions first
				|| Util.constant(a) && Util.constant(b) && !(a instanceof MixedNumber) && !(b instanceof MixedNumber);
	}
	
	private Term multiply(Term term, ArrayList<ArrayList<AlgebraicParticle>> groups){
		//what we'll use to build the new term
		ArrayList<AlgebraicParticle> combined = new ArrayList<AlgebraicParticle>(groups.size());
		//loop over the list
		for(ArrayList<AlgebraicParticle> a : groups){
			//multiply each item
			AlgebraicParticle multiplied = a.get(0);
			for(int i = 1; i < a.size(); i++){
				multiplied = multiply(multiplied, a.get(i));
			}
			//and add the grand total to the list
			combined.add(multiplied);
		}
		//then construct the final term
		return  new Term(term.sign(), combined, term.exponent());
	}
	
	private AlgebraicParticle multiply(AlgebraicParticle a, AlgebraicParticle b){
		if(Util.constant(a) && Util.constant(b)){
			if(a instanceof Number && b instanceof Number) return Number.multiply((Number)a, (Number)b, this.roundingRule);
			else if(a instanceof Fraction && b instanceof Fraction){
				Fraction f1 = (Fraction)a, f2 = (Fraction)b;
				Fraction frac =  new Fraction(
						f1.sign() == f2.sign(),
						Number.multiply((Number)f1.getTop(), (Number)f2.getTop(), this.roundingRule), 
						Number.multiply((Number)f1.getTop(), (Number)f2.getTop(), this.roundingRule),
						f1.exponent() // trust that their exponents are equal
						);
				//if the top and bottom have different signs, reverse the sign of the fraction
				if(frac.getTop().sign() != frac.getBottom().sign()) frac.cloneWithNewSign(!frac.sign());
				//else if the signs are the same, but they're negative, make them both positive
				else if(!frac.getTop().sign() && !frac.getBottom().sign()){
					frac = new Fraction(frac.sign(), frac.getTop().cloneWithNewSign(true), frac.getBottom().cloneWithNewSign(true), 1);					
				}
				return frac;
			}
			//then one must be a number and the other a fraction
			Number num; Fraction frac;
			if(a instanceof Number){
				num = (Number)a;
				frac = (Fraction)b;
			} else {
				num = (Number)b;
				frac = (Fraction)a;
			}
			//put the number over one and multiply as fractions
			return multiply(new Fraction(true, num, Number.ONE, 1), frac);
		}
		else if(a.getClass().equals(b.getClass())){
			//calculate the new sign and exponent
			int exponent = a.exponent() + b.exponent();
			boolean sign = a.sign() == b.sign();
			return a.cloneWithNewSignAndExponent(sign, exponent);
		}
		else throw new IllegalArgumentException("Cannot multiply " + a.render() + " and " + b.render() + " because they are not of the same type.");
	}
	
}
