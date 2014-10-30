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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Checks to see if an expression or equation is valid. 
 * @author Nateowami, Tribex
 */
public class Validator {
	
	/**
	 * Tells whether a given equation is valid
	 * @param equation The equation to check
	 * @return true if the equation is valid, otherwise false
	 */
	public static boolean eqIsValid(String e) {
				
		//debugging
		Solve4x.debug("eqIsValid()" + e);
		Solve4x.debug("Testing Equation Validity: "+e);
		
		//create a new Equation
		Equation eq;
		//try to parse it
		try{
			eq = new Equation(e);
			//check all expressions
			for(int i = 0; i < eq.getSize(); i++){
				//if the expression is invalid
				if(!exprIsValid(eq.getExpression(i))){
					return false;
				}
			}
			//they were all valid expressions
			return true;
		}
		catch(ArrayIndexOutOfBoundsException err){
			//if it couldn't be parsed it's not valid
			return false;
		}
	}
	
	/**
	 *Evaluates the algebraic validity of a give expression.
	 *@param expr The expression to check
	 *@return if the expression is valid
	 */
	private static boolean exprIsValid(Expression expr) {
		//check the terms for being correct
		for(int i = 0; i < expr.numbOfTerms(); i++){
			//if the term is not correct return false
			if(!termIsValid(expr.termAt(i))){
				Solve4x.debug("Returns false");
				return false;
			}
		}
		//no problems were found
		Solve4x.debug("Returns true");
		return true;		
	}
	
	/**
	 * Evaluates the algebraic validity of the given term. A term may have commas in its exponent.
	 * @param term The term to be evaluated
	 * @return If the term is valid
	 */
	private static boolean termIsValid(AlgebraicParticle term) {
		Solve4x.debug("Param: " + term.getAsString());
		//Check the term body for being formatted properly, being ""
		if(areLettersAndNums(term.getAsString()) || term.getAsString().equals("")){
			Solve4x.debug("Returns true");
			return true;
		}
		//it's also possible it's an expression, but we should only check that if the number of terms
		//is greater than 1, or it would cause a stack overflow 
		if(hasMoreThanOneTerm(term.getAsString()) && exprIsValid(new Expression(term.getAsString()))){
			Solve4x.debug("Returns true");
			return true;
		}
		Solve4x.debug("Returns false");
		return false;
	}

	/**
	 * Tells if all chars in a String are numerals (0-9) and letters (a-z and A-Z)
	 * @param s The String to check.
	 * @return 
	 */
	private static boolean areLettersAndNums(String s){
		Solve4x.debug("Param: " + s);
		//loop through the chars
		for(char i : s.toCharArray()){
			//if it's not a numerals or letter
			if(!Util.areAllLetters(Character.toString(i)) && !Util.areAllNumerals(Character.toString(i))){
				Solve4x.debug("Returns false");
				return false;
			}
		}
		Solve4x.debug("Returns true");
		return true;
	}
	
	/**
	 * Tells if an expression has more than one term. This is to avoid calling exprIsValid() from 
	 * termIsValid() which would cause a stack overflow.
	 * @param s The expression to check
	 * @return If there is more than one term
	 */
	private static boolean hasMoreThanOneTerm(String s) {
		try{
			//make an expression
			Expression expr = new Expression(s);
			//if there are multiple terms return true, otherwise false
			if(expr.numbOfTerms() > 1){
				return true;
			}
			else return false;
		}
		//if the expression couldn't be parsed return false;
		catch(ArrayIndexOutOfBoundsException e){
			return false;
		}
	}
	
}