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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents an algebraic equation
 * @author Nateowami
 */
public class Equation {
	
	//exprs holds expressions (one if this equation is just representing an 
	//expression, two if it's representing an equation
	private Expression exprs[];
	
	/**
	 * Creates an equation by turning it into two expressions
	 * @param eq The equation (or expression) to turn into an
	 * equation, which can be just an expression if necessary
	 * @throws MalformedInputException 
	 */
	public Equation(String eq) throws MalformedInputException{
		Solve4x.debug("Creating Equation from String: " + eq);
		//find the = sign
		int i; //needs to be used outside loop
		//tells if an = sign was even found
		boolean found = false;
		for(i = 0; i < eq.length(); i++){
			//if it's an equals sign
			if(eq.charAt(i) == '='){
				//say we found it
				found = true;
				//we found the = sign, we're done now (i is index of =)
				break;
			}
		}
		
		//if an = sign was found
		if(found){
			//create the array and add two expressions to it
			exprs = new Expression[2];
			exprs[0] = new Expression(eq.substring(0, i));
			exprs[1] = new Expression(eq.substring(i+1, eq.length()));
		}
		//no = sign found
		else{
			exprs = new Expression[1];
			exprs[0] = new Expression(eq);
		}
		//debug
		Solve4x.debug("Equation created. " + this.getAsString());
		
	}
	
	/**
	 * @param i The expression you want in this equation.
	 *Needs to be 0 or 1.
	 * @return The expression at index i
	 */
	public Expression getExpression(int i){
		Solve4x.debug("about to return expression from equation. index is: " + i);
		return exprs[i];
	}
	
	/**
	 * Tells the length/number of expressions in this Equation
	 * This seems unnecessary (there would usually be two) but there could be only
	 * one if Equation holds only one expression, or more if it represents a system
	 * of equations.
	 * @return The number of expressions in this Equation
	 */
	public int getSize(){
		return exprs.length;
	}

	/**
	 * Sets the specified expression in this equation to the specified expression
	 * @param expr The expression you want to change it to 
	 * @param index The index of the Expression you want to change
	 */
	public void setExpression(String expr, int index) throws MalformedInputException{
		this.exprs[index] = new Expression(expr);
		
	}
	
	/**
	 * @return A String representation of this equation. Example: 2x+x=5
	 */
	public String getAsString(){
		String eq= "";
		for(int i = 0; i < exprs.length; i++){
			//if i is odd append '='
			eq += i % 2 == 0 ? "" : "=";
			eq += exprs[i].getAsString();
		}
		return eq;
	}
	
}