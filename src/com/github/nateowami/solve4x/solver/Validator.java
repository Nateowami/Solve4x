/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Nathaniel Paulus

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

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
	public static boolean eqIsValid(String e){
				
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
		/*//check for one and only one equals sign
		int numbOfEquals = 0;
		int indexOfEquals = 0;
		for(int i = 0; i < equation.length(); i++){
			if (equation.charAt(i) == '='){
				numbOfEquals++;
				indexOfEquals = i;
			}
		}
		if(numbOfEquals == 1){
			//check the validity of the
			return (exprIsValid(equation.substring(0, indexOfEquals)) 
					&& exprIsValid(equation.substring(indexOfEquals + 1, equation.length())));
		} 
		else {
			return false;
		}*/
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
		
		//Before doing the regular recursive check, there are a few misc things
		//to check first
		
		/*//make sure the length isn't 0
		if(expr.length()==0){
			//then it can't be valid
			return false;
		}
		//the expression also shouldn't start with a + sing (- is OK because it means negative)
		//we already know the length isn't 0, so this is safe
		if(expr.charAt(0)== '+'){
			//then it can't be valid
			return false;
		}
		
		int cutAt = -1;
		
		//if we can cut based on addition or subtraction
		cutAt = findCutAtAdditionOrSubtraction(expr);
		//if the cut was successful
		if(cutAt != -1){
			//return true if both expressions are found valid
			return cut(expr, cutAt);
		}
		
		//cut at division
		cutAt = findCutAtDivision(expr);
		//if the cut was successful
		if(cutAt != -1){
			return cut(expr, cutAt);
		}
		
		//cut at multiplication
		cutAt = findCutAtMultiplication(expr);
		//if the cut was successful
		if(cutAt != -1){
			return cutMultiplication(expr, cutAt);
		}
		
		//if it still wasn't cut
		return termIsValid(expr);*/

		
	}
	
	/**
	 * Evaluates the algebraic validity of the given term. A term may have commas in its exponent.
	 * @param term The term to be evaluated
	 * @return If the term is valid
	 */
	private static boolean termIsValid(Term term) {
		Solve4x.debug("Param: " + term.getAsString());
		//Check the term body for being formatted properly, being ""
		if(areLettersAndNums(term.getBody()) || term.getBody().equals("")){
			Solve4x.debug("Returns true");
			return true;
		}
		//it's also possible it's an expression, but we should only check that if the number of terms
		//is greater than 1, or it would cause a stack overflow 
		if(hasMoreThanOneTerm(term.getBody()) && exprIsValid(new Expression(term.getBody()))){
			Solve4x.debug("Returns true");
			return true;
		}
		//we'd have returned false already if it wasn't valid
		Solve4x.debug("Returns false");
		return false;
		
		/*//debugging
		Solve4x.debug("termIsValid()" + term);
		
		//keep track of the first letter in the term
		int checkFrom = 0;
		
		//check the exponent
		for(int i=0; i<term.length(); i++){
			//if it's a letter
			if (Util.isLetter(term.charAt(i))){
				//check the first part of the term (everything before i) ONLY IF IT EXISTS
				//and make sure it's an integer (in the form of 34,546)
				if(term.substring(0, i).length() != 0 && !Util.isInteger(term.substring(0, i))){
					//if it's not an integer, return false
					Solve4x.debug("termIsValid returns " + false);
					return false;
				}
				//set the location to check (from here on still needs to be checked by another loop)
				checkFrom=i;
				break;
			}
			//else if it's the last char in the string
			else if(i==term.length()-1){
				Solve4x.debug("termIsValid returns " + Util.isInteger(term.substring(0, i+1)));
				return Util.isInteger(term.substring(0, i+1));
			}
		}
		//check the rest of the term (everything following the exponent)
		for (int i=checkFrom; i<term.length(); i++){
			Solve4x.debug("isTerm is checking the rest of the term from " + checkFrom);
			if (!Util.isNumeral(term.charAt(i)) && !Util.isLetter(term.charAt(i))){
				Solve4x.debug("termIsValid returns " + false);
				return false;
			}
		}
		//if we din't hit any problems above, return true now
		Solve4x.debug("termIsValid returns " + true);
		return true;*/
	}

	/**
	 * Tells if all chars in a String are numerals (0-9) and letters (a-z and A-Z)
	 * @param s The String to check.
	 * @return 
	 */
	private static boolean areLettersAndNums(String s){
		Solve4x.debug("Param: " + s);
		//loop through the chars
		for(int i = 0; i < s.length(); i++){
			//if it's not a numerals or letter
			if(!Util.isLetter(s.charAt(i)) && !Util.isNumeral(s.charAt(i))){
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
	private static boolean hasMoreThanOneTerm(String s){
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