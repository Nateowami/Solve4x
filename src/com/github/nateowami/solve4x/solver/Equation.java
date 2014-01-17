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

/**
 * Represents an algebraic equation
 * @author Nateowami
 */
public class Equation {
	
	private Expression[] exprs = new Expression[2];
	
	/**
	 * Creates an equation by turning it into two expressions
	 * @param eq
	 */
	public Equation(String eq){
		//find the = sign
		int i; //needs to be used outside loop
		for(i = 0; i < eq.length(); i++){
			//if it's an equals sign
			if(eq.charAt(i) == '='){
				//we found the = sign, we're done now (i is index of =)
				break;
			}
		}
		//set the two slots in the exprs array with expressions 
		//from before and after the = sign
		exprs[0] = new Expression(eq.substring(0, i));
		exprs[1] = new Expression(eq.substring(0, i));
	}
	
	/**
	 * @param i The expression you want in this equation.
	 *Needs to be 0 or 1.
	 * @return The expression at index i
	 */
	public Expression getExpression(int i){
		return exprs[i];
	}
}