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
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class Factor extends Algorithm {
	
	/**
	 * Constructs a new Factor object.
	 */
	public Factor() {
		super(Expression.class);
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Expression expr = (Expression) algebra;
		
		//find greatest common factor
		Map<AlgebraicParticle, Integer> commonFactors = commonFactors(expr);
		AlgebraicParticle gcf = construct(commonFactors);
		
		//divide each term by the GCF
		ArrayList<Map<AlgebraicParticle, Integer>> terms = new ArrayList<Map<AlgebraicParticle, Integer>>(expr.length());
		for(int i = 0; i < expr.length(); i++) {
			terms.add(removeCommon(factors(expr.get(i)), commonFactors));
		}
		
		//construct a new expression from the modified terms
		ArrayList<AlgebraicParticle> resultingTerms = new ArrayList<AlgebraicParticle>(terms.size());
		for(Map<AlgebraicParticle, Integer> term : terms) {
			resultingTerms.add(construct(term));
		}
		
		//append the the expression to the common factors and convert it to a term
		commonFactors.put(new Expression(true, resultingTerms, 1), 1);
		Term result = (Term) construct(commonFactors);
		
		Step step = new Step(result);
		step.explain("The greatest common factor in ").explain(algebra).explain(" is ").explain(gcf)
				.explain(". Dividing each term by it and bring it ouside the parentheses to get ")
				.explain(result).explain(".");
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		//count the common factors and return accordingly
		int numCommon = commonFactors((Expression) algebra).entrySet().size();
		if(numCommon == 0) return 0;
		else if(numCommon == 1) return 7;
		else return 9;
	}
	
	private Map<AlgebraicParticle, Integer> factors(AlgebraicParticle a){
		return CancelFactors.factors(a);
	}
	
	//TODO: exception if expr.length() < 2; handle such things
	private Map<AlgebraicParticle, Integer> commonFactors(Expression expr) {
		Map<AlgebraicParticle, Integer> common = commonFactors(factors(expr.get(0)), factors(expr.get(1)));
		for(int i = 2; i < expr.length(); i++) {
			common = commonFactors(common, factors(expr.get(i)));
		}
		return common;
	}
	
	private Map<AlgebraicParticle, Integer> commonFactors(Map<AlgebraicParticle, Integer> a, Map<AlgebraicParticle, Integer> b){
		//use a LinkedHashMap to preserve order of insertion
		Map<AlgebraicParticle, Integer> common = new LinkedHashMap<AlgebraicParticle, Integer>();
		
		//find common keys (common factors)
		for(Entry<AlgebraicParticle, Integer> entry : a.entrySet()) {
			//if the current one is common to both 
			if(b.containsKey(entry.getKey())) {
				//add the common factor with an exponent of the common number of them
				common.put(entry.getKey(), Math.min(entry.getValue(), b.get(entry.getKey())));
			}
		}
		return common;		
	}
	
	private Map<AlgebraicParticle, Integer> removeCommon(Map<AlgebraicParticle, Integer> term, Map<AlgebraicParticle, Integer> common) {
		for(Entry<AlgebraicParticle, Integer> entry : common.entrySet()) {
			int exponent = term.get(entry.getKey()) - common.get(entry.getKey());
			if(exponent == 0) term.remove(entry.getKey());
			else term.put(entry.getKey(), exponent);
		}
		return term;
	}
	
	private AlgebraicParticle construct(Map<AlgebraicParticle, Integer> map) {
		Set<Entry<AlgebraicParticle, Integer>> set = map.entrySet();
		if(set.size() == 0) return Number.ONE;
		
		//keep track of coefficient (will be multiplying integer factors shortly)
		int coefficient = 1;
		
		//check for a factor of negative one and remove it and set sign to negative if found
		boolean sign = true;
		if(map.containsKey(Number.NEGATIVE_ONE)) {
			sign = false;
			map.remove(Number.NEGATIVE_ONE);
		}
		
		//there must be multiple factors to raise to their respective powers
		ArrayList<AlgebraicParticle> factors = new ArrayList<AlgebraicParticle>(set.size());
		for(Entry<AlgebraicParticle, Integer> entry : set) {
			AlgebraicParticle alg = entry.getKey();
			int exponent = entry.getValue();
			//if it's an integer multiply it by the coefficient
			if(alg instanceof Number 
					&& ((Number)alg).isInteger()) coefficient *= Math.pow(Integer.parseInt(((Number)alg).getInteger()), exponent);
			//otherwise adjust its exponent
			else factors.add(alg.cloneWithNewSignAndExponent(alg.sign(), exponent));
		}
		
		//push the coefficient to the beginning of the list
		if(coefficient != 1) factors.add(0, new Number(true, Integer.toString(coefficient), null, null, 1));
		
		//determine the result and return
		if(factors.size() == 1) return factors.get(0).cloneWithNewSign(sign);
		else return new Term(sign, factors, 1);
	}
	
}
