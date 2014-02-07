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
	//but you can have an empty string. Example: 2x has no power. We can't 
	//represent that if we use an int.
	
	//the coefficient of the term (it's a String because the coefficient could be a fraction)
	//and the expression/body part of the term
	private String coeInt = "", coeFrac = "", expr = "";
	//the positive or negative value of the term
	private boolean value;
	
	/**
	 * Creates a new term from a String
	 * @param term The term to create
	 */
	public Term(String term){

		//parse the expression and set coe and term
		
		//set the term's positive or negative value
		//if it's negative
		if(term.charAt(0) == '-'){
			value = false;
			//remove the - from term
			term = term.substring(1, term.length());
		}
		else{
			//positive is default
			value = true;
			//the + may or may not exist, but it's positive if it doesn't
			if(term.charAt(0) == '+'){
				//remove the +
				term = term.substring(1, term.length());
			}
		}
		
		//look for an integer an the beginning
		for(int i=0; i< term.length(); i++){
			
			//if it's not a numeral
			if(!Util.isNumeral(term.charAt(i))){
				//set the coefficient to a substring (the beginning
				//of the term to i-1). The method substring() will subtract 1 for us.
				this.coeInt = term.substring(0, i);
				//remove the integer from the beginning of the string
				term = term.substring(i, term.length());
				//set the integer part of the coefficient
				break;
			}
		}
		
		//now check for a fraction that could come after the integer
		for(int i=0; i<term.length(); i++){
			//if 0 to i+1 is a nice and neat fraction
			if(Util.isFraction(term.substring(0, i))){
				//if it's a fraction set coeFrac to it
				this.coeFrac = term.substring(0, i);
				//remove the fraction from the string and set the body of the term
				this.expr = term.substring(i, term.length());
			}
		}
	}
	
	/**
	 * @return The coefficient of the term (may include a fraction)
	 */
	public String getCoe(){
		return coeInt+coeFrac;
	}

	/**
	 * @return The body of the term. For example, in "4x" this would return "x".
	 */
	public String getBody() {
		return expr;
	}
	
	/**
	 * @return The positive or negative value of this term
	 * true is positive, false is negative.
	 */
	public boolean getIsPositive(){
		return value;
	}

}
