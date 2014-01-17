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

import java.util.ArrayList;

/**
 * Represents an algebraic expression with positive and negative terms
 * @author Nateowami
 */
public class Expression {
	
	//create an ArrayList for storing a list of terms
	private ArrayList <Term>termList = new ArrayList<Term>();
	//the original term
	private String expression;
	
	/**
	 * Creates a list of terms our of the expression
	 * @param expr The expression to store as terms
	 */
	public Expression(String expr){
		
		//set this.expression
		this.expression = expr;
		
		//Iterate through the expression keeping track of how deep into par nesting we get
		int parDepth = 0;
		for(int i = 0; i< expr.length(); i++){
			//check to see if parDepth needs to change
			if(Util.isOpenPar(expr.charAt(i))){
				parDepth++;
			}
			else if(Util.isClosePar(expr.charAt(i))){
				parDepth--;
			}
			//if it's a + or - AND parDepth is 0
			//and it's OK to use else if because if it's + or - then the 
			//above if statements would have failed
			else if(parDepth == 0 && (expr.charAt(i) == '+' || expr.charAt(i) == '-')){
				//take everything from the beginning of the expression until i
				//and make a new Term. Add it to the term list
				this.termList.add(new Term(expr.substring(0, i)));
				//delete that term from expr
				expr = expr.substring(i, expr.length());
				//now reset i to 0
				//this is necessary because the expr.length() just changed
				i = 0;
			}
		}
	}
	
	/**
	 * @return the list of terms
	 */
	public ArrayList<Term> getExprList() {
		return termList;
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
	public Term termAt(int i){
		return termList.get(i);
	}
	
	/**
	 * @return The expression in String form
	 */
	public String getExpression(){
		return this.expression;
	}
}
