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

import java.nio.charset.MalformedInputException;

/**
 * Solves equations and simplifies expressions
 * @author Nateowami
 */
public class Solver {
	
	/**
	 * Solves the given equation or simplifies it if it is an expression
	 * @param expr The expression or equation to solve or simplify
	 * @return A Solution object that contains the steps for solving
	 * @throws MalformedInputException 
	 */
	public static Solution solve(String expr) throws MalformedInputException{
		
		//if it's an equation
		if(Util.isEq(expr)){
			//make sure the equation is valid
			if(!Validator.eqIsValid(expr)){
				//if it's not, throw an exception
				throw new MalformedInputException(expr.length());
			}
			else{
				//TODO solve it
			}
		}
		//then it's not an equation; maybe an expression
		else{
			//make sure the expression is valid
			if(!Validator.exprIsValid(expr)){
				throw new MalformedInputException(expr.length());
			}
			//TODO simplify it
		}
		return null;//XXX fix this later
	}
}
