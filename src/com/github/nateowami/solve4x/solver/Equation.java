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
	 */
	public Equation(String eq){
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
		Solve4x.debug("Equation created. First expression: " + exprs[0].getAsString() + " second expression: " + exprs[1].getAsString());
		
	}
	
	/**
	 * @param i The expression you want in this equation.
	 *Needs to be 0 or 1.
	 * @return The expression at index i
	 */
	public Expression getExpression(int i){
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
	
}