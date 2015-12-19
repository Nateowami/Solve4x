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

import com.github.nateowami.solve4x.config.RoundingRule;
import com.github.nateowami.solve4x.solver.*;
import com.github.nateowami.solve4x.solver.Number;

/**
 * Distributes terms (e.g. 2(x+4) becomes 2x+8, (x+2)(x-3) becomes x^2-x-6).
 * @author Nateowami
 */
public class Distribute extends Algorithm {
	
	private final RoundingRule roundingRule;
	
	/**
	 * Constructs a new Distribute.
	 * @param rule A rounding rule to use when multiplying.
	 */
	public Distribute(RoundingRule rule) {
		super(Term.class);
		this.roundingRule =  rule;
	}

	@Override
	public Step execute(Algebra algebra) {
		Term term = (Term) algebra;
		Step step = null;
		int expressionIndex = indexOfFirstExpression(term);
		
		//if there is an expression right after it, such as (2x+4)(x+6)
		if(term.length() - 1 > expressionIndex && term.get(expressionIndex + 1) instanceof Expression) {
			Expression multiplied = multiplyExpressions(
					(Expression)term.get(expressionIndex), 
					(Expression)term.get(expressionIndex+1));
			AlgebraicParticle result = insertInTerm(term, multiplied, expressionIndex, 2);
			step = new Step(result);
			step.explain("In the term ").explain(term).explain(", multiply each term in ")
					.explain(term.get(expressionIndex)).explain(" by each term in ")
					.explain(term.get(expressionIndex)).explain(" to get ").explain(result);
		}
		//else there's an expression multiplied by the whole term
		else {
			AlgebraicParticle multiplicand = term.cloneAndRemove(expressionIndex);
			AlgebraicParticle result = multiplyTermByExpression(multiplicand, (Expression) term.get(expressionIndex));
			step = new Step(result);
			step.explain("In the term ").explain(term).explain(" multiply ").explain(multiplicand)
					.explain(" by each term in ").explain(term.get(expressionIndex))
					.explain(" to get ").explain(result);
		}
		
		return step;
	}
	
	@Override
	public int smarts(Algebra algebra) {
		Term term = (Term) algebra;
		if(indexOfFirstExpression(term) != -1) return 7;
		else return 0;
	}
	
	/**
	 * Tells the first index of an Expression (using <code>instanceof</code> in the given Term. 
	 * Only an Expression with no exponent (i.e. exponent of 1) will be considered.
	 * @param term The term to check.
	 * @return The index of the first Expression in <code>term</code>.
	 */
	private int indexOfFirstExpression(Term term) {
		for(int i = 0; i < term.length(); i++) {
			if(term.get(i) instanceof Expression && term.get(i).exponent() == 1)return i;
		}
		return -1;
	}
	
	/**
	 * Takes a term and makes a pseudo-clone, which is identical in every way except that 
	 * <code>numToReplace</code> elements are removed starting at index <code>insertIndex</code> 
	 * and replaced with given <code>algebra</code>. For example, if <code>term</code> was 
	 * <code>2xy(6+4)</code>, and <code>algebra</code> was <code>4x</code>, <code>insertIndex</code> 
	 * <code>2</code>, and <code>numToReplace</code> <code>1</code>, then <code>2x4x(6+y)</code> 
	 * would be returned. If <code>numToReplace</code> is equal to the length of the given 
	 * <code>term</code>, <code>algebra</code> itself will be returned, but with the sign and 
	 * exponent of <code>term</code>.
	 * @param term The term (which will be left untouched) from which to clone and remove elements.
	 * @param algebra The algebra to insert where elements are removed.
	 * @param insertIndex The index at which to begin removing elements, and to add algebra.
	 * @param numToReplace The number of elements to remove, starting at insertIndex.
	 * @return A clone of term with numToReplace elements removed from index insertIndex.
	 */
	protected static AlgebraicParticle insertInTerm(Term term, AlgebraicParticle algebra, int insertIndex, int numToReplace) {
		//if replacing the entire term
		if(term.length() == numToReplace) return algebra.cloneWithNewSignAndExponent(term.sign(), term.exponent());
		// create an arraylist to hold the term we're building (length is just a slight optimization)
		ArrayList<AlgebraicParticle> list = new ArrayList<AlgebraicParticle>(term.length() - numToReplace + 1);
		// add the elements before insertIndex
		for(int i = 0; i < insertIndex; i++) {
			list.add(term.get(i));
		}
		int elementsAdded = list.size();
		list.add(algebra);
		for(int i = elementsAdded + numToReplace; i < term.length(); i++) {
			list.add(term.get(i));
		}
		
		return new Term(term.sign(), list, term.exponent());		
	}
	
	/**
	 * Multiplies two given Expressions, <code>a</code> and <code>b</code>, by multiplying each 
	 * element in <code>a</code> by each element in <code>b</code>, and returns the result.
	 * @param a The first expression to multiply.
	 * @param b The second expression to multiply.
	 * @return a multiplied by b, using the distributive property.
	 */
	private Expression multiplyExpressions(Expression a, Expression b) {
		ArrayList<AlgebraicParticle> multiplied = new ArrayList<AlgebraicParticle>(a.length() * b.length());
		for(int i = 0; i < a.length(); i++) {
			for(int j = 0; j < b.length(); j++) {
				multiplied.add(multiply(a.get(i), b.get(j)));
			}
		}
		return new Expression(true, multiplied, 1);
	}
	
	/**
	 * Multiplies an piece of algebra by an Expression, by multiplying it by each element of the 
	 * Expression, and returns the result.
	 * @param algebra The AlgebraicParticle to multiply by.
	 * @param expression The Expression to multiply by.
	 * @return <code>algebra</code> multiplied by <code>expression</code> using the distributive 
	 * property.
	 */
	private Expression multiplyTermByExpression(AlgebraicParticle algebra, Expression expression) {
		ArrayList<AlgebraicParticle> multiplied = new ArrayList<AlgebraicParticle>(expression.length());
		for(int i = 0; i < expression.length(); i++) {
			multiplied.add(multiply(algebra, expression.get(i)));
		}
		return new Expression(true, multiplied, 1);
	}
	
	/**
	 * Multiplies two AlgebraicParticles, a and b, and returns the result. If either is an instance 
	 * of Term, each element of the Term is multiplied.
	 * @param a The first AlgebraicParticle to multiply by.
	 * @param b The second AlgebraicParticle to multiply by.
	 * @return a multiplied by b.
	 */
	AlgebraicParticle multiply(AlgebraicParticle a, AlgebraicParticle b) {
		//create a list of elements
		ArrayList<AlgebraicParticle> elements = new ArrayList<AlgebraicParticle>();
		boolean sign = true;
		
		//Add all elements. If terms, take care of sign. If not terms, this is taken care of later
		if(a instanceof Term) {
			elements.addAll(termToArrayList((Term) a));
			sign = sign == a.sign();
		}
		else elements.add(a);
		if(b instanceof Term) {
			elements.addAll(termToArrayList((Term) b));
			sign = sign == b.sign();
		}
		else elements.add(b);
		
		ArrayList<AlgebraicParticle> numbers = new ArrayList<AlgebraicParticle>();
		ArrayList<AlgebraicParticle> nonNum = new ArrayList<AlgebraicParticle>();
		for(AlgebraicParticle element : elements) {
			if(element instanceof Number) numbers.add(element);
			else nonNum.add(element);
		}
		
		//multiply all non-numbers
		ArrayList<AlgebraicParticle> parts = new ArrayList<AlgebraicParticle>();
		bigloop:
		for(AlgebraicParticle algebra : nonNum) {
			for(AlgebraicParticle algebra2 : parts) {
				if(algebra.almostEquals(algebra2)) {
					parts.remove(algebra2);
					parts.add(algebra.cloneWithNewSignAndExponent(
							algebra.sign() == algebra2.sign(),
							algebra.exponent() + algebra2.exponent()));
					continue bigloop;
				}
			}
			parts.add(algebra);
		}
		
		//multiply all numbers
		Number number = (Number) (numbers.size() > 0 ? numbers.get(0) : null);
		for(int i = 1; i < numbers.size(); i++) {
			number = Number.multiply(number, (Number) numbers.get(i), this.roundingRule);
		}
		
		//add the number to the list of other stuff
		if(number != null) parts.add(0, number);
		
		//figure out the sign and make everything positive (because sign is taken care of by the term)
		for(int i = 0; i < parts.size(); i++) {
			AlgebraicParticle element = parts.get(i);
			sign = sign == element.sign();
			if(!element.sign()) parts.set(i, element.cloneWithNewSign(true));
		}
		
		if(parts.size() == 1) return parts.get(0).cloneWithNewSign(sign); 
		else return new Term(sign, parts, 1);
	}
	
	/**
	 * Converts a given Term to an ArrayList&lt;Term&gt;, disregarding sign and exponent.
	 * @param term A Term to convert.
	 * @return A converted to an ArrayList.
	 */
	private ArrayList<AlgebraicParticle> termToArrayList(Term term) {
		ArrayList<AlgebraicParticle> elements = new ArrayList<AlgebraicParticle>(term.length());
		for(int i = 0; i < term.length(); i++) elements.add(term.get(i));
		return elements;
	}
	
}