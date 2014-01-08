/*
    Solve4x - A Java program to solve and explain algebra problems
    Copyright (C) 2013 Nathaniel Paulus

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
 * Checks to see if an expression or equaiton is valid. 
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
					&& exprIsValid(equation.substring(indexOfEquals + 1, equation.length())));//pretty sure +2 is right
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
		
		//remove spaces
		expr = expr.replaceAll(" ","");
		
		//remove parentheses from both ends if they surround the entire expression
		expr = removePar(expr);
		
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
	 * Evaluates the algebraic validity of the given term
	 * @param term The term to be evaluated
	 * @return If the term is valid
	 */
	public static boolean termIsValid(String term) {
		//debugging
		Solve4x.debug("termIsValid()" + term);
		boolean returnSatus = true;
		for(int i = 0; i < term.length(); i++){
			//if the char is neither a number or letter
			if(!(Util.isNumeral(term.charAt(i))) && !(isLetter(term.charAt(i)))){
				returnSatus = false;
				break;
			}
		}
		return returnSatus;
	}

	/**
	 * Evaluates a char to see if it is between a-z or A-Z
	 * @param c The char to evaluate
	 * @return If the char is a a-z or A-Z
	 */
	public static boolean isLetter(char c){
		//debugging
		Solve4x.debug("isLetter()" + c);
		if(c <= 'z' && c >= 'a' || c <= 'Z' && c >= 'A'){
			Solve4x.debug("isLetter Returns true");
			return true;
		}
		else{
			Solve4x.debug("isLetter Returns false");
			return false;
		}
	}

	/**
	 * Checks to see if the entire expression is surrounded by parentheses,
	 * in which case they will be removed for easier parsing
	 */
	public static String removePar(String expr){
		String arg = expr;
		//debugging
		Solve4x.debug("removePar()" + expr);
		
		//check to see if it starts and ends with parentheses
		if((expr.length() >= 1) && isOpenPar(expr.charAt(0)) && isClosePar(expr.charAt(expr.length()-1))){

			int parDepth = 1; //how deep we are into parentheses nesting
			boolean parDepthReached0 = false;//if parDepth ever reaches 0 we're outside all parentheses
			//check to see if the entire expr is enclosed with parentheses
			for (int i = 1; i < expr.length() - 1; i++){
				if(expr.charAt(i) == '(' || expr.charAt(i) == '[' 
						|| expr.charAt(i) == '{' || expr.charAt(i) == '<'){
					parDepth++;
				}
				else if(expr.charAt(i) == ')' || expr.charAt(i) == ']' 
						|| expr.charAt(i) == '}' || expr.charAt(i) == '>'){
					parDepth--;
				}
				if (parDepth == 0){
					parDepthReached0 = true;
				}
			}

			if(!parDepthReached0){
				//debugging
				Solve4x.debug("removePar(" + arg + ") -> " + expr.substring(1, expr.length()));
				return expr.substring(1, expr.length()-1);
			}
			else{
				//debugging
				Solve4x.debug("removePar(" + arg + ") -> " + expr);
				return expr;
			}
		}
		else{
			//debugging
			Solve4x.debug("removePar(" + arg + ") -> " + expr);
			return expr;
		}
	}
	

	/**
	 * Figures out if the next char (after index) is is (, [, {, or <
	 * @param str The string to search through
	 * @param index The index to start searching from
	 * @return true the next char is (, [, {, or <, false if it isn't or if the string
	 * has reached the end.
	 */
	public static boolean isNextCharOpenPar(String str, int index){
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
	public static int getEndOfPower(String str, int index){
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
	 * Evaluates a char to see if it's a closing parentheses (or root)
	 * @param c The char to evaluate
	 * @return If the char is a closing parentheses
	 */
	private static boolean isClosePar(char c){
		if(c == ')' || c == ']' || c == '}' || c == '>' || c == '^'){
			return true;
		}
		else return false;
	}
	

	/**
	 * Evaluates a char to see if it's a opening parentheses (or root)
	 * @param c The char to evaluate
	 * @return If the char is a opening parentheses
	 */
	private static boolean isOpenPar(char c){
		if(c == '(' || c == '[' || c == '{' || c == '<' || c == '√'){
			return true;
		}
		else return false;
	}
	
	
	//XXX: BEGIN TRIBEX TEST REGEX CODE :XXX\\
	//Just leave this stuff here and bypass it if necessary, this seems to be working quite well.
	//removed because it wouldn't compile. Tribex appears to have it working, but what was here
	//wasn't finished
	//XXX: END TRIBEX TEST REGEX CODE :XXX\\
		

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
			if(isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			//if it's a closing par
			else if (isClosePar(expr.charAt(i))){
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
			if(isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			//and watch the parDepth for going down
			if(isClosePar(expr.charAt(i))){
				parDepth--;
			}
			
			//if it's a closing parentheses
			if(isClosePar(expr.charAt(i)) && parDepth == 0){
				//find the end of a power (if any)
				return getEndOfPower(expr, i);//because there could be powers after it
			}
			else if(isOpenPar(expr.charAt(i)) && parDepth == 1 /*because it's an opening par*/&& i != 0/*Don't cut if it's the first time*/){
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
			if(isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			//if it's a closing par
			else if (isClosePar(expr.charAt(i))){
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