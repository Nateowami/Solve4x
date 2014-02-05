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
 * @autor Nateowami, Tribex
 */
public class Validator {
	
	/**
	 * Tells whether a given equation is valid
	 * @param equation The equation to check
	 * @return true if the equation is valid, otherwise false
	 */
	public static boolean eqIsValid(String equation){
				
		//debugging
		Solve4x.debug("eqIsValid()" + equation);
		Solve4x.debug("Testing Equation Validity: "+equation);
		
		//check for one and only one equals sign
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
		}
	}
	
	/**
	 *Evaluates the algebraic validity of a give expression.
	 *@param expr The expression to check
	 *@return if the expression is valid
	 */
	public static boolean exprIsValid(String expr) {
		
		//remove parentheses from both ends if they surround the entire expression
		expr = Util.removePar(expr);
		
		//Before doing the regular recursive check, there are a few misc things
		//to check first
		
		//make sure the length isn't 0
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
		return termIsValid(expr);

		
	}
	
	/**
	 * Validates both sides of an expression. 
	 * @param expr The expression to evaluate
	 * @param cut The index to cut at. The char at this index IS included
	 * in the first substring, unlike Validator.cut()
	 * @return if both sides of the expression are valid
	 */
	private static boolean cutMultiplication(String expr, int cut) {
		return exprIsValid(expr.substring(0, cut+1)) && exprIsValid(expr.substring(cut+1, expr.length())); 
	}
	
	/**
	 * Evaluates the algebraic validity of the given term. A term may have commas in its exponent.
	 * @param term The term to be evaluated
	 * @return If the term is valid
	 */
	private static boolean termIsValid(String term) {
		//debugging
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
		return true;
	}

	/**
	 * Figures out if the next char (after index) is is (, [, {, or <
	 * @param str The string to search through
	 * @param index The index to start searching from
	 * @return true the next char is (, [, {, or <, false if it isn't or if the string
	 * has reached the end.
	 */
	private static boolean isNextCharOpenPar(String str, int index){
		boolean isPar = false;
		//if there is another char to check
		if(str.length()-1>index){
			//if the next char is a parentheses
			if(str.charAt(index+1)=='(' || str.charAt(index+1)=='[' ||
					str.charAt(index+1)=='{' || str.charAt(index+1)=='<'){
				//the next char is a paretheses
				isPar = true;
			}
		}
		return isPar;
	}

	/**
	 * Finds the index of the end of a power
	 * @param str The string to search in
	 * @param index The index to start the search at
	 * @return The last char that is part of the power
	 */
	private static int getEndOfPower(String str, int index){
		//debugging
		Solve4x.debug("getNextNonNumeral str: "+str+" index: "+index);
		int answer = str.length()-1;//if there is no answer, default is the last index
		//loop through the chars starting at index
		for(int i = index; i < str.length(); i++){
			//if the char isn't a numeral
			if(!Util.isNumeral(str.charAt(i))){
				//then the one before must have been the last
				answer = i-1;
				//but the answer should never be less than the original index
				if(answer < index){
					answer = index;
				}
				break;
			}
		}
		Solve4x.debug("getNextNonNumeral returns: " + answer);
		return answer;
	}

	/**
	 * Finds the index of where a given expression has a + or - sign that is not nested
	 *@param expr The expression to evaluate
	 *@return The index of non-nested addition or subtraction
	 */
	private static int findCutAtAdditionOrSubtraction(String expr){
		int parDepth = 0;//count how nested in parentheses we get
		//loop through chars, keeping track of how far into parentheses nesting we get
		for (int i = 0; i < expr.length(); i++){
			//if it's an opening par
			if(Util.isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			//if it's a closing par
			else if (Util.isClosePar(expr.charAt(i))){
				parDepth--;
			}
			//if it's addition or subtraction AND parDepth (nesting) is 0
			if((expr.charAt(i) == '+' || expr.charAt(i) == '-' || expr.charAt(i) == '±') && parDepth == 0)
				return i;
		}
		return -1;//There is no non-nested addition or subtraction
	}
	
	/**
	 * Finds the index of the last char in an expression that is multiplied by another.
	 * For example, if expr is (5)2(6) it will return 3.
	 *@param expr The expression to evaluate
	 *@return The index of non-nested multiplication
	 */
	private static int findCutAtMultiplication(String expr){
		//debug
		Solve4x.debug(" *  " + expr);
		int parDepth = 0;//how deep into par nesting we get
		for (int i = 0; i < expr.length(); i++){
			
			//watch the parDepth
			if(Util.isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			//and watch the parDepth for going down
			if(Util.isClosePar(expr.charAt(i))){
				parDepth--;
			}
			
			//if it's a closing parentheses
			if(Util.isClosePar(expr.charAt(i)) && parDepth == 0){
				//find the end of a power (if any)
				return getEndOfPower(expr, i);//because there could be powers after it
			}
			else if(Util.isOpenPar(expr.charAt(i)) && parDepth == 1 /*because it's an opening par*/&& i != 0/*Don't cut if it's the first time*/){
				//so it parDepth must be at least 1
				Solve4x.debug("HERE");
				Solve4x.debug(" * returns " + (i -1));
				return i-1;
			}
		}
		Solve4x.debug(" * returns " + -1);
		return -1;
	}
	
	/**
	 * Finds the index of where a given expression has a division sign that is not nested
	 *@param expr The expression to evaluate
	 *@return The index of a non-nested division
	 */
	private static int findCutAtDivision(String expr){
		int parDepth = 0;//count how nested in parentheses we get
		//loop through chars, keeping track of how far into parentheses nesting we get
		for (int i = 0; i < expr.length(); i++){
			//if it's an opening par
			if(Util.isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			//if it's a closing par
			else if (Util.isClosePar(expr.charAt(i))){
				parDepth--;
			}
			//if it's division AND parDepth (nesting) is 0
			else if(expr.charAt(i) == '/' && parDepth == 0){
				return i;
			}
		}
		return -1;//There is no non-nested division
	}
	
	/**
	 * Validates both sides of an expression. 
	 * @param expr The expression to evaluate
	 * @param cut The index to cut at. The char at this index
	 * will not be in either of the sub strings
	 * @return if both sides of the expression are valid
	 */
	private static boolean cut(String expr, int cut){
		//check both sides of the expression dividing at cut
		return exprIsValid(expr.substring(0, cut)) && exprIsValid(expr.substring(cut+1, expr.length()));
	}
}