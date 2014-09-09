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

import java.nio.charset.MalformedInputException;
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
	Expression(String expr) throws MalformedInputException{
		Solve4x.debug("Expression: " + expr);
		//loop backwards to find something that can be parsed
		for(int i = expr.length(); i > 0; i--){
			if(AlgebraicParticle.parseable(expr.substring(0, i), subParts));{
				termList.add(AlgebraicParticle.getInstance(expr.substring(0, i), subParts));
				//remove what's been parsed from expr and reset i
				expr = expr.substring(i, expr.length());
				i = expr.length()+1;//because the loop is about to do i--
			}
		}
		if(expr.length() > 0) throw new MalformedInputException(expr.length());
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
	 * TODO write doc
	 * @param s
	 * @return
	 */
	public static boolean parseable(String s) {
		// make sure there is more than one algebraic particle concatenated with + or -TODO will have to let Equation hold AlgebraicParticles
		return false;
	}

}
