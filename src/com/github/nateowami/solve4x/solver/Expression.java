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
package com.github.nateowami.solve4x.solver;


import java.util.ArrayList;

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents an algebraic expression with positive and negative terms
 * @author Nateowami
 */
public class Expression extends AlgebraicParticle{
	
	//create an ArrayList for storing a list of terms
	protected ArrayList <AlgebraicParticle>termList = new ArrayList<AlgebraicParticle>();
	
	private static final Class[] subParts = {Variable.class, Number.class, Fraction.class, MixedNumber.class, Root.class, Term.class};
	
	/**
	 * Creates a list of terms from the expression
	 * @param expr The expression to store as terms
	 */
	Expression(String expr) {
		Solve4x.debug("Expression: " + expr);
		int parDepth = 0;
		for(int i = 0; i < expr.length(); i++){
			if(expr.charAt(i) == '(') parDepth++;
			else if(expr.charAt(i) == ')') parDepth--;
			/*
			 * We could be at the end of the string OR there could be a sign at i.
			 * That's what the i == expr.length()-1 ? i+1 : i is about.
			 * If we're at the end, we want to parse up to and including i. Otherwise
			 * we just parse up to and excluding i.
			 */
			int breakAt = i == expr.length()-1 ? i+1 : i;
			if(parDepth == 0 && (expr.charAt(i) == '+' || expr.charAt(i) == '-' || i == expr.length()-1) 
					&& AlgebraicParticle.parseable(expr.substring(0, breakAt), subParts)){
				termList.add(AlgebraicParticle.getInstance(expr.substring(0, breakAt), subParts));
				expr = expr.substring(i);
				i = 0; 
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Expression ["
				+ (termList != null ? "termList=" + termList : "") + "]";
	}
	
	/**
	 * @return The number of terms in this expression
	 */
	public int numbOfTerms(){
		return termList.size();
	}
	
	/**
	 * @param i The index of the term you want
	 * @return The term at index i
	 */
	public AlgebraicParticle termAt(int i){
		return termList.get(i);
	}
	
	/**
	 * Fetches the expression in the form of a String.
	 * This should not be used in most situations.
	 * @return The expression in String form
	 */
	public String getAsString(){
		String expr = "";
		for(AlgebraicParticle i : this.termList){
			expr += i.getAsString();
		}
		return expr;
	}

	/**
	 * Tells if string s can be parsed as an expression.
	 * @param s The string to check.
	 * @return If s can be parsed as an expression.
	 */
	public static boolean parseable(String expr) {
		int parDepth = 0, numParsed = 0;
		for(int i = 0; i < expr.length(); i++){
			if(expr.charAt(i) == '(') parDepth++;
			else if(expr.charAt(i) == ')') parDepth--;
			if(parDepth == 0 && (expr.charAt(i) == '+' || expr.charAt(i) == '-' || i == expr.length()-1) 
					&& AlgebraicParticle.parseable(expr.substring(0, i == expr.length()-1 ? i+1 : i), subParts)){
				expr = expr.substring(i);
				numParsed++;
				i = 0;
			}
		}
		return numParsed > 1 && expr.length() == 0;
	}

}
