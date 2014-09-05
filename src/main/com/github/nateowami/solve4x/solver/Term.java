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
	
	private static final Class[] subParts = {Expression.class, Fraction.class, MixedNumber.class, Number.class, Root.class, Variable.class};
	
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
			if(AlgebraicParticle.isAlgebraicParticle(s.substring(0, i), subParts)){
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
			s += this.parts.get(i).getAsString();
		}
		return s;
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
	public static boolean isTerm(String s){
		//make sure it's a list of algebraic particles
		for(int i = s.length(); i >= 0; i--){
			if(isParticle(s.substring(0, i))){
				//reset the loop
				s = s.substring(i);
				i = s.length()+1;//+1 because it's about to be subtracted 1
			}
		}
		//if the whole thing was parseable
		if(s.length() == 0){
			return true;
		}
		else return false;		
	}
	
	/**
	 * Helper method for isTerm(String s). Tells if a string is valid as an algebraic particle,
	 * including an expression, but only if its length is > 1 and is surrounded by parentheses.
	 * Basically: anything can be an expression (anything that can go on one side of an equals sign)
	 * Therefore, we need a way to check that it HAS to be an expression
	 * @param s The string to check.
	 * @return If it's an algebraic particle.
	 */
	private static boolean isParticle(String s){
		if(Variable.isVariable(s) || Number.isNumber(s) || Root.isRoot(s) || Fraction.isFraction(s) 
				|| ConstantFraction.isConstantFraction(s) || MixedNumber.isMixedNumber(s)){
			return true;
		}
		//loop through it and find if it is two algebraic particles separated by + or - signs
		int parDepth = 0;
		for(int i = 0; i < s.length(); i++){
			//keep track of par depth
			if(Util.isOpenPar(s.charAt(i))){
				parDepth++;
			}
			else if(Util.isClosePar(s.charAt(i))){
				parDepth--;
			}
			//if parDepth is 0 and it's a + or - sign
			else if(parDepth == 0 && (s.charAt(i) == '+' || s.charAt(i) == '-')){
				//split s at i and and check both for being a algebraic particles
				String a = s.substring(0, i), b = s.substring(i);
				//if both are some sort of algebraic particle
				if((Number.isNumber(a) || Root.isRoot(a) || Fraction.isFraction(a) || ConstantFraction.isConstantFraction(a) || MixedNumber.isMixedNumber(a)) 
				&& (Number.isNumber(b) || Root.isRoot(b) || Fraction.isFraction(b) || ConstantFraction.isConstantFraction(b) || MixedNumber.isMixedNumber(b))){
					return true;
				}
				else return false;
			}
		}
		return false;
	}
	
}
