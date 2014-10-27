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
		String[] parts = Util.splitByNonNestedChars(expr, '+', '-');
		for(String part : parts){
			termList.add(AlgebraicParticle.getInstance(part, subParts));
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
		for(int i = 0; i < termList.size(); i++){
			String term = termList.get(i).getAsString();
			//if it's not the first term, and the term isn't negative, prepend a + sign
			expr += i != 0 && term.charAt(0) != '-' ? "+" + term : term;
		}
		return expr;
	}

	/**
	 * Tells if string s can be parsed as an expression.
	 * @param s The string to check.
	 * @return If s can be parsed as an expression.
	 */
	public static boolean parseable(String expr) {
		String[] parts = Util.splitByNonNestedChars(expr, '+', '-');
		if(parts.length < 2) return false;
		for(String part : parts){
			if(!AlgebraicParticle.parseable(part, subParts))return false;
		}
		return true;
	}

}
