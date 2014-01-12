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
				//TODO check for fractions @Nateowami
				
				//then make this.expr equal to the rest of the string
				this.expr = expr.substring(i, expr.length());
				
				//wer're done
				break;
			}
		}
	}
	
	/**
	 * Tells if a given expression is a fraction AND that both the top and bottom of 
	 * the fraction contain ONLY INTAGERS
	 * @param frac The expression to evaluate
	 * @return If the expression is a fraction with only ints on top and bottom.
	 */
	private boolean parseFraction(String frac){
		
		//first find the fraction bar
		for(int i=0; i<frac.length(); i++){
			//if the current char is '/'
			if (frac.charAt(i) == '/'){
				//TODO check both sides of it
			}
		}
		//there's not '/'
		return false;
		
	}
	
	/**
	 * Tells if a given expression is in the form of <integer>. Example: <56> This is useful
	 * for determining if an expression is part of a fraction.
	 * @param side The expression to evaluate
	 * @return If side is in the form of <integer>.
	 */
	private boolean parseFracSide(String side){
		//if the first char is '<' AND the last char is '>'
		if(side.charAt(0) == '<' && side.charAt(side.length()-1) == '>'){
			//if what's in the carets is is an integer
			if(Util.isIntager(side.substring(1, side.length()-1))){
				return true;
			}
			//it's not then
			else{
				return false;
			}
		}
		//if it's not, then it's in the wrong form
		else return false;
	}
}
