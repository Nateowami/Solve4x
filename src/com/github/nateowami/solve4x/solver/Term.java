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
 * Provides a way to work with terms in equations.
 * Terms are represented as follows: A Term (optionally) contains a coefficient (instance of class Number),
 * and two list: one of variables, the other of Expressions. For example, the term 2&lt;3&gt;/&lt;4&gt;xy2(2x+4y)(x+y)2
 * would be represented as follows:
 * 2&lt;3&gt;/&lt;4&gt; (Number)
 * variable list:
 * 		x
 * 		y (variable with power of 2) 
 * Expression list:
 * 		(2x+4y)
 * 		(x+y)2 (Expression with power of two)
 * You can access these values with methods such as hasCoe(), getCoe(), numOfVars(), varAt(int), numOfExprs(), and exprAt(int).
 * Variables and Expressions are held in two separate arrays.
 * @author Nateowami
 */
public class Term extends AlgebraicParticle{
	
	//the list of variables in this term
	private ArrayList<AlgebraicParticle> parts = new ArrayList<AlgebraicParticle>();
	
	private static final Class[] subParts = {Variable.class, Number.class, Fraction.class, MixedNumber.class, Root.class, Expression.class};
	
	/**
	 * Creates a new term from a String
	 * @param term The term to create
	 * @throws MalformedInputException If the term is not properly formatted (and not always even then)
	 */
	protected Term(String s) throws MalformedInputException{
		Solve4x.debug("Creating term: "+s);
		//loop backwards to find a match
		int i;
		for(i = s.length(); i >=0; i--){
			if(AlgebraicParticle.parseable(s.substring(0, i), subParts)){
				Solve4x.debug("Creating algebraic particle: " + s.substring(0, i));
				parts.add(AlgebraicParticle.getInstance(s.substring(0, i), subParts));
				//reset the loop
				s = s.substring(i);
				i = s.length()+1;//+1 because it is about to be subtracted when the loop continues
			}
		}
		//if the whole thing wasn't parsed
		if(s.length() > 0){
			throw new MalformedInputException(i);
		}
	}
	
	/**
	 * Tells the number of Expressions in this term. For example,
	 * 2xy(2x+6) would return 1. x and y are variables, not Expressions.
	 * @return The number of Expressions in this term.
	 * @see getExprAt(int i)
	 */
	public int numOfParts(){
		return this.parts.size();
	}
	
	/**
	 * @param i The index of the Expression you want.
	 * @return The Expression at index i.
	 * @see numOfExprs()
	 */
	public AlgebraicParticle getPartAt(int i){
		return this.parts.get(i);
	}
		
	/**
	 * Returns a String representation of this Term in the form of an algebraic term, not the 
	 * traditional toString().
	 * @return
	 */
	public String getAsString(){
		String s = "";
		for(int i = 0; i < this.parts.size(); i++){
			s += 	//wrap with parentheses only if it's an expression
					this.parts.get(i) instanceof Expression ? 
							"(" + this.parts.get(i).getAsString() + ")" : 
							this.parts.get(i).getAsString();
		}
		return wrapWithSignAndExponent(s);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Term [" + (parts != null ? "parts=" + parts : "") + "]";
	}

	/** 
	 * Tells if a specified string s may be parsed as a terrm
	 * @param s The string to check.
	 * @return If s is parseable as a term.
	 */
	public static boolean parseable(String s){
		int numParsed = 0; //make sure we're not doing someone else's job 
		//for example, we shouldn't say "1" is a term, because it should be considered a number
		
		//make sure it's a list of algebraic particles
		for(int i = s.length(); i >= 0; i--){
			//make sure it doesn't have a sign
			if(s.length() > 0 && s.charAt(0) != '+' && s.charAt(0) != '-' && AlgebraicParticle.parseable(s.substring(0, i), subParts)){
				numParsed++;
				//reset the loop
				s = s.substring(i);
				i = s.length()+1;//+1 because it's about to be subtracted 1
			}
		}
		//if the whole thing was parseable
		if(s.length() == 0 && numParsed > 1){
			return true;
		}
		else return false;		
	}
	
}
