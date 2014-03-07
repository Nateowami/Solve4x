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

import java.nio.charset.MalformedInputException;
import java.util.Arrays;

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
public class Term {
	
	//the coefficient of the term
	private Number coe;
	//the list of variables in this term
	private char vars[];
	//and their respective powers
	private int varPowers[];
	//the list of expressions in this term
	private Expression exprs[];
	//and their respective powers
	private int exprPowers[];
	
	/**
	 * Creates a new term from a String
	 * @param term The term to create
	 * @throws MalformedInputException If the term is not properly formatted (and not always even then)
	 */
	public Term(String term) throws MalformedInputException{
		//fill varPowers[] and exprPowers[] with 1s
		Arrays.fill(varPowers, 1);
		Arrays.fill(exprPowers, 1);
		
		//create the coefficient
		//loop backwards until we find it's a number.
		//numbers will descend
		for(int i = term.length()-1; i >= 0; i--){
			if(!Number.isNumber(term.substring(0, i+1))){
				//we found the largest section that could be called the coefficient
				//take it and initialize the coefficient
				coe = new Number(term.substring(0, i+1));
				break;
			}
		}
		//Now term has no coefficient. 
		//for every variable, add it to vars[] and it's power to varPowers[]
		//add every expression to esprs[] and their powers to exprPowers[]
		
		//variable for keeping track of what we've put into vars[] and exprs[]
		int varsSoFar = 0, exprsSoFar = 0;
		
		//while there's something to parse
		while(term.length() > 0){
			//it the first char is a variable
			if(Util.isLetter(term.charAt(0))){
				//add it to the list of vars at the index varsSoFar
				vars[varsSoFar] = term.charAt(0);
				//as long as numerals are found, add them to the this string
				String power = "";
				for(int i = 1; i < term.length() && Util.isNumeral(term.charAt(i)); i++){
					//there is another char, and it's a numeral
					//append it to power
					power+=term.charAt(i);
				}
				//now set the power for the var we found
				//and increment varsSoFar
				varPowers[varsSoFar++] = Integer.parseInt(power);
				//Now cut out the part we've parsed. The length is 1 (var's size) + power.length()
				term = term.substring(1+power.length(), term.length());
			}
			//otherwise it's an expression
			else{
				//figure out how long this expression lasts
				//the depth into parentheses nesting that we've gone
				int parDepth = 0;
				//loop through the expression, waiting for the parentheses depth to reach 0
				for(int i = 0; i < term.length(); i++){
					if(Util.isOpenPar(term.charAt(i))){
						parDepth++;
					}
					//if it's the first char and it wasn't an open par, throw and exception
					else if(i == 0 && !Util.isOpenPar(term.charAt(i))){
						throw new MalformedInputException(0);
					}
					else if(Util.isClosePar(term.charAt(i))){
						parDepth--;
					}
					//if we've reached the end of the expression
					if(parDepth == 0){
						//create a new Expression and then break
						
						exprs[exprsSoFar] = new Expression(term.substring(0, i+1));
						//cut the expression from the term
						term = term.substring(i+1, term.length());
						
						//as long as numerals are found, add them to the this string
						String power = "";
						for(int b = 1; b < term.length() && Util.isNumeral(term.charAt(b)); b++){
							//there is another char, and it's a numeral
							//append it to power
							power+=term.charAt(i);
						}
						//now set the power for the expression we found
						//and increment exprsSoFar
						exprPowers[exprsSoFar++] = Integer.parseInt(power);
						break;
					}
				}
			}
		}
	}
	
	/**
	 * Tells if the Term has a coefficient. Example:
	 * xy2 would be false. There is no coefficient.
	 * 2(xy2+9) would be true. 2 is the coefficient.
	 * &lt;1&gt;/&lt;2&gt; would be true. It has a fraction for a coefficient.
	 * @return If the Term has a coefficient
	 */
	public boolean hasCoe(){
		return coe == null;
	}
	
	/**
	 * Gives the coefficient of the Term if it has one, otherwise null
	 * @return the coefficient of the Term.
	 * @see hascoe();
	 */
	public Number getCoe(){
		return coe;
	}
	
	/**
	 * Tells the number of variables in this term. For example,
	 * 2xy(2x+6) would return 2. (2x+6) is an Expression, not a variable.
	 * @return The number of variables in this term
	 * @see getVarAt(int i)
	 */
	public int numOfVars(){
		return vars.length;
	}
	
	/**
	 * @param i The index of the variable you want.
	 * @return The variable at index i.
	 * @see numOfVars()
	 */
	public char getVarAt(int i){
		return vars[i];
	}
	
	/**
	 * Tells the power of the specified variable.
	 * @param i The index of the variable for which you want its power.
	 * @return The power of the variable at index i.
	 */
	public int getVarPower(int i){
		return varPowers[i];
	}
	
	/**
	 * Tells the number of Expressions in this term. For example,
	 * 2xy(2x+6) would return 1. x and y are variables, not Expressions.
	 * @return The number of Expressions in this term.
	 * @see getExprAt(int i)
	 */
	public int numOfExprs(){
		return exprs.length;
	}
	
	/**
	 * @param i The index of the Expression you want.
	 * @return The Expression at index i.
	 * @see numOfExprs()
	 */
	public Expression getExprAt(int i){
		return exprs[i];
	}
	
	/**
	 * Tells the power of the expression at index i.
	 * @param i The index of the Expression for which you want the power
	 * @return The power of the expression at index i.
	 */
	public int getExprPower(int i){
		return exprPowers[i];
	}
	
	/**
	 * @return The variables and Expressions of this term as a String. 
	 */
	public String getBody(){
		String vars = "";
		for(int i = 0; i < this.vars.length; i++){
			vars+=	this.vars[i];
			//add the power if it's there
			if(this.varPowers[i]!=1){
				vars+=this.varPowers[i];
			}
		}
		//now add the expressions
		String exprs = "";
		for(int i = 0; i < this.exprs.length; i++){
			exprs+=this.exprs[i];
			//add the power if it's there
			if(this.exprPowers[i]!=1){
				vars+=this.exprPowers[i];
			}
		}
		return vars+exprs;
	}
	
	/**
	 * Returns a String representation of this Term in the form of an algebraic term, not the 
	 * traditional toString().
	 * @return
	 */
	public String getAsString(){
		return (coe==null?"":coe.getAsString()) + getBody();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Term [coe=" + coe + ", vars=" + Arrays.toString(vars)
				+ ", varPowers=" + Arrays.toString(varPowers) + ", exprs="
				+ Arrays.toString(exprs) + ", exprPowers="
				+ Arrays.toString(exprPowers) + "]";
	}
	
}
