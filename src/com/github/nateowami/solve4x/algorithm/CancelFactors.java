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
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * @author Nateowami
 */
public class CancelFactors extends Algorithm {
		
	/**
	 * Constructs a new Divide algorithm.
	 */
	public CancelFactors() {
		super(Fraction.class);
	}
	
	@Override
	public Step execute(Algebra algebra) {
		Fraction frac = (Fraction) algebra;
		
		//get factors of top and bottom
		Map<AlgebraicParticle, Integer> factorsTop = factors(frac.getTop()),
				factorsBottom = factors(frac.getBottom());
		
		//find intersection of set of factors of top and bottom
		Set<AlgebraicParticle> commonFactors = new HashSet<AlgebraicParticle>(factorsTop.keySet());
		commonFactors.retainAll(factorsBottom.keySet());
		
		//cancel all the common factors and add what doesn't cancel to the respective list
		for(AlgebraicParticle element : commonFactors) {
			Integer numTop = factorsTop.get(element), numBottom = factorsBottom.get(element);
			
			//cancel the factors, and if there are more on top than bottom, leave some on top, else 
			//leave more on the bottom.
			int numToCancel = numTop > numBottom ? numBottom : numTop;
			int resultNumTop = numTop - numToCancel;
			int resultNumBottom = numBottom - numToCancel;
			
			//set the new coefficient for every factor (canceling)
			factorsTop.put(element, resultNumTop);
			factorsBottom.put(element, resultNumBottom);
		}
		
		//calculate the sign for the terms
		boolean sign = factorsTop.containsKey(Number.NEGATIVE_ONE) == factorsBottom.containsKey(Number.NEGATIVE_ONE);
		sign = sign == frac.sign();
		
		//remove -1 & 1 from factors (if necessary) (have already tracked -1, so can ignore now)
		factorsTop.remove(Number.NEGATIVE_ONE);
		factorsBottom.remove(Number.NEGATIVE_ONE);
		
		//we factored integers; now multiply the ones that didn't cancel
		multiplyIntegers(factorsTop);
		multiplyIntegers(factorsBottom);
		
		AlgebraicParticle top = constructResult(factorsTop);
		AlgebraicParticle bottom = constructResult(factorsBottom);
		
		Fraction done = new Fraction(sign, top, bottom, frac.exponent());
		AlgebraicParticle result = done;
		
		//if the bottom is 1, the result is the top (any number divided by 1 is that number)
		if(done.getBottom().equals(Number.ONE)) {
			if(sign) result = done.getTop();
			else result = done.getTop().cloneWithNewSign(sign);
		}
		
		Step step = new Step(result);

		AlgebraicParticle[] commonFactorsArray = commonFactors.toArray(new AlgebraicParticle[commonFactors.size()]);
		//if there were factors, explain. There aren't always factors; e.g. 4/1 simplifies to 4
		int count = commonFactorsArray.length;
		if(count != 0) {
			step.explain("In the fraction ").explain(frac)
			.explain(" the common factor" + (count == 1 ? " is " : "s are "))
			.list(commonFactorsArray).explain(". Dividing top and bottom by ");
			//specify the term to divide by if there's only 1, otherwise just say "these"
			if(count == 1) step.explain(commonFactorsArray[0]);
			else step.explain("these");
			step.explain(" leaves ").explain(result).explain(".");
		}
		//there weren't any factors that could cancel
		else step.explain("Simplify ").explain(frac).explain(" to get ").explain(result);
		
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		Fraction frac = (Fraction) algebra;
		if(frac.isSimplified()) return 0;
		//if the bottom is 1 or -1 we can simplify for sure
		else if(frac.getBottom().equals(Number.ONE) || frac.getBottom().equals(Number.NEGATIVE_ONE)) return 7;
		else if(!frac.getTop().sign() || !frac.getBottom().sign()) return 5;
		else {
			//get factors of top and bottom
			Map<AlgebraicParticle, Integer> factorsTop = factors(frac.getTop()),
					factorsBottom = factors(frac.getBottom());
			//see if they have common factors
			for(AlgebraicParticle factor : factorsTop.keySet()) {
				if(factorsBottom.containsKey(factor)) return 7;
			}
			return 0;
		}
	}
	
	/**
	 * Calculates the factors of a given AlgebraicParticle. If it is a term, the factors of its 
	 * children will be calculated. If it is an integer, it will be factored. Otherwise the 
	 * specified algebra is the sole factor. The map that is returned maps factors to the number of 
	 * occurrences of that factor. For example, when 12 is factored the resulting map would have 
	 * the factor 2 paired with a 2, meaning 2 to the second power (equaling 4), and a 3 paired 
	 * with a 1, thus factoring 12 as 2²*3.
	 * @param algebra The AlgebraicParticle to factor.
	 * @return The factors of algebra in a map paring each with the number of times it appears.
	 */
	protected static Map<AlgebraicParticle, Integer> factors(AlgebraicParticle algebra){
		HashMap<AlgebraicParticle, Integer> factors = new HashMap<AlgebraicParticle, Integer>();
		if(algebra instanceof Term){
			Term term = (Term) algebra;
			for(int i = 0; i < term.length(); i++) combine(factors, factors(term.get(i)));
			if(!algebra.sign()) addToMap(factors, Number.NEGATIVE_ONE);
		}
		else if(algebra instanceof Number && ((Number)algebra).isInteger()){
			Number number = (Number) algebra;
			//calculate the primes
			Long[] primes = Util.primeFactors(Long.parseLong(number.getInteger()));
			//add them to the map
			for(long prime : primes) addToMap(factors, new Number(Long.toString(prime)));
			//if the number is negative, add -1 to the map
			if(!algebra.sign()) addToMap(factors, Number.NEGATIVE_ONE);
		}
		else addToMap(factors, algebra);
		return factors;
	}
	
	/**
	 * Adds a given AlgebraicParticle to a given map, mapping the AlgebraicPartile's raw value to 
	 * its exponent (by raw value we mean it's value when it has a positive sign and an exponent of 
	 * 1). If its raw value already exists in the map, the exponent will be added to the previously 
	 * recorded exponent. Since we're only mapping positive values, if the sign is negative, a -1 
	 * will be either added or removed from the map, depending on whether one currently exists in 
	 * the map. Thus, there will always be either 1 or 0 instances of -1 in the map, and if it 
	 * exits, it will always be paired with an exponent of 1 (indicating that there is only one in 
	 * existence). Adding -1 to the map simply results in the -1 being toggled onto or off of the 
	 * map. Adding 1 has no effect.
	 * @param map The map to which to add algebra to.
	 * @param algebra The AlgebraicParticle to add to the map.
	 */
	private static void addToMap(Map<AlgebraicParticle, Integer> map, AlgebraicParticle algebra){
		boolean sign = algebra.sign();
		int exponent = algebra.exponent();
		
		if(!sign) {
			//it's negative, so toggle the -1
			if(map.containsKey(Number.NEGATIVE_ONE)) map.remove(Number.NEGATIVE_ONE);
			else map.put(Number.NEGATIVE_ONE, 1);
		}
		
		algebra = algebra.cloneWithNewSignAndExponent(true, 1);
		
		//if it's not just a 1 (or a 1 to any power), add it to the map (or update the exponent in the map)
		if(!algebra.equals(Number.ONE)) {
			if(map.containsKey(algebra)) map.put(algebra, map.get(algebra) + exponent);
			else map.put(algebra, exponent);
		}
	}
	
	/**
	 * Constructs an AlgebraicParticle given a list map of AlgebraicParticles and integers, with 
	 * the integers specifying the exponent of the AlgebraicParticle. If the map is empty, 1 is 
	 * returned. If there is exactly one entry in the map, it will be returned, with the respective 
	 * exponent. If there are move than one entries, a Term with all of them will be returned. 
	 * Though the order of the elements in the term is undefined (because the order in the map is 
	 * undefined), all numbers are pushed moved to the beginning.
	 * @param map The map of AlgebraicParticles to construct into a term or whatever.
	 * @return The map converted to an AlgebraicParticle.
	 */
	private static AlgebraicParticle constructResult(Map<AlgebraicParticle, Integer> map) {
		if(map.size() == 0) return Number.ONE;
		else if(map.size() == 1) {
			//you'd think getting the entry from a map would be simpler...
			Entry<AlgebraicParticle, Integer> entry = map.entrySet().iterator().next();
			AlgebraicParticle algebra = entry.getKey();
			
			if(entry.getValue() == 0) return Number.ONE;
			//take action if exponent needs to be something other than 1
			if(entry.getValue() != 1) algebra = algebra.cloneWithNewSignAndExponent(true, entry.getValue());
			return algebra;
		}
		else {
			ArrayList<AlgebraicParticle> factors = new ArrayList<AlgebraicParticle>(map.size());
			for(Entry<AlgebraicParticle, Integer> entry : map.entrySet()) {
				//if the exponent isn't 0
				if(entry.getValue() != 0) {
					// Add a factor to the list. If the exponent shouldn't be 1, clone with new exponent
					factors.add(entry.getValue() == 1 ? entry.getKey() : entry.getKey().cloneWithNewSignAndExponent(true, entry.getValue()));
				}
			}
			
			//move numbers to the beginning of the list of factors (just nicer that way)
			//current implementation has the bonus that because the integer is at the end, it will 
			//always end up first
			for(int i = 0; i < factors.size(); i++) {
				if(factors.get(i) instanceof Number) {
					//move the element to the beginning
					AlgebraicParticle number = factors.get(i);
					factors.remove(i);
					factors.add(0, number);
				}
			}
			
			//not all factors were necessarily added, so we could have less than 2 now
			if(factors.size() == 0) return Number.ONE;
			else if(factors.size() == 1) return factors.get(0);
			else return new Term(true, factors, 1);
		}
	}
	
	/**
	 * Removes all integers in the specified map, raises each 
	 * to it's respective power (indicated by the values in the map), multiplies them, and adds 
	 * the result as a Number to the map, paired with the value 1. For example, if the map had 2 
	 * paired with three (indicating 2^3) and a 3 paired with a 1, the result would be 18. 
	 * @param map The map in which to multiply all integers.
	 */
	private static void multiplyIntegers(Map<AlgebraicParticle, Integer> map) {
		long product = 1;
		
		// Iterate over each item in the map using an iterator to avoid ConcurrentModificationException\
		// when removing items
		for(Iterator<Entry<AlgebraicParticle, Integer>> it = map.entrySet().iterator(); it.hasNext();) {
			Entry<AlgebraicParticle, Integer> entry = it.next();
			if(entry.getKey() instanceof Number) {
				Number number = (Number) entry.getKey();
				if(number.isInteger()) {
					//multiply the total so far by the number raised to its exponent
					//e.g. if we had factored and gotten 2*3^4, and this was the second iteration, 
					//the product so far would be 2 and we would raise 3^4 to get 81, then multiply 
					//to get a total of 162
					product *= Math.pow(Integer.parseInt(number.getInteger()), entry.getValue());
					it.remove();
				}
			}
		}
		//add the product back into the map
		if(product != 1) map.put(new Number(true, String.valueOf(product), null, null, 1), 1);
	}
	
	/**
	 * Takes all elements in map2 and adds them to map1. When both maps have the same key, the 
	 * resulting pair will have the sum of the two values. For example, if both maps had 'x' paired 
	 * with 2, the resulting entry in map1 would have 'x' paired with 4.
	 * @param map1 The map to add elements to, or to update their values.
	 * @param map2 The map of elements that need to be added to map1.
	 */
	private static void combine(Map<AlgebraicParticle, Integer> map1, Map<AlgebraicParticle, Integer> map2) {
		for(Entry<AlgebraicParticle, Integer> entry : map2.entrySet()) {
			//add or update the key in map1
			map1.put(entry.getKey(), (map1.containsKey(entry.getKey()) ? map1.get(entry.getKey()) : 0) + entry.getValue());
		}
	}
	
}
