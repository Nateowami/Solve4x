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

/**
 * Provides a way to work with terms in equations.
 * @author Nateowami
 */
public class Term {
	
	//all properties are strings because there's no way to have an empty int
	//but you can have an empty string. Example: 2x has no coefficient. We can't 
	//represent that if we use an int.
	
	//the coefficient of the term (it's a String because the coefficient could be a fraction)
	private String coe = "";
	//the body of the term
	private String expr = "";
	
	/**
	 * Creates a new term from a String
	 * @param term The term to create
	 */
	public Term(String expr){
		
		//parse any int from the beginning of the expr and put in in coe
		//then take the rest and stuff it in expr.
		for(int i=0; i< expr.length(); i++){
			
			//if it's not a numeral
			if(!Util.isNumeral(expr.charAt(i))){
				
				//set the coefficient to a substring (the beginning
				//of the expr to i-1). The method substring()
				//will subtract 1 for us.
				this.coe = expr.substring(0, i);
				
				//it's possible there is still a fraction in the coefficient
				
				
				//then make this.expr equal to the rest of the string
				this.expr = expr.substring(i, expr.length());
				
				//wer're done
				break;
			}
		}
	}
}
