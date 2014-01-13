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
	private String coeInt = "";
	private String coeFrac = "";
	//the body of the term
	private String expr = "";
	
	/**
	 * Creates a new term from a String
	 * @param term The term to create
	 */
	public Term(String expr){

		//parse the expression and set coe and expr
		
		//look for an integer an the beginning
		for(int i=0; i< expr.length(); i++){
			
			//if it's not a numeral
			if(!Util.isNumeral(expr.charAt(i))){
				//set the coefficient to a substring (the beginning
				//of the expr to i-1). The method substring() will subtract 1 for us.
				this.coeInt = expr.substring(0, i);
				//remove the integer from the beginning of the string
				expr = expr.substring(i, expr.length());
				//set the integer part of the coefficient
				break;
			}
		}
		
		//now check for a fraction that could come after the integer
		for(int i=0; i<expr.length(); i++){
			//if 0 to i+1 is a nice and neat fraction
			if(parseFraction(expr.substring(0, i))){
				//if it's a fraction set coeFrac to it
				this.coeFrac = expr.substring(0, i);
				//remove the fraction from the string and set the body of the term
				this.expr = expr.substring(i, expr.length());
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
				//if both sides are valid fraction sides
				return parseFracSide(frac.substring(0, i)) && parseFracSide(frac.substring(i, frac.length()));
			}
		}
		//there's not a '/'
		return false;
		
	}
	
	/**
	 * Tells if a given expression is in the form of <integer>. Example: <56> This is useful
	 * for determining if an expression is part of a fraction.
	 * @param side The expression to evaluate
	 * @return If side is in the form of <integer>.
	 */
	private boolean parseFracSide(String side){

		//if it's an integer
		if(Util.isInteger(side)){
			return true;
		}
		
		//if it's surrounded by carets and inside is an integer
		if(side.charAt(0) == '<' && side.charAt(side.length()-1) == '>'){
			//if what's in the carets is is an integer
			if(Util.isInteger(side.substring(1, side.length()-1))){
				return true;
			}
			//it's surrounded by carets but it's not an integer
			else{
				return false;
			}
		}
		//it's not a fraction side
		else return false;
	}

	/**
	 * @return The coefficient of the term (may include a fraction)
	 */
	public String getCoe(){
		return coeInt+coeFrac;
	}

	/**
	 * @return the expr
	 */
	public String getBody() {
		return expr;
	}
	
	

}
