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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Provides static utility methods for use with String equations. Many local methods in other
 * classes may be moved here if they need to have central use (especially methods in Validator.java)
 * @author Nateowami
 */
public class Util {
	
	/**
	 * Evaluates a given string to determine if it has an = sign
	 * @param The string to evaluate
	 * @return If the equation has an equals sign
	 */
	public static boolean isEq(String str){
		//debugging
		Solve4x.debug("isEq()");
		boolean hasEqualsSign = false;
		for(int i=0; i < str.length(); i++){
			if(str.charAt(i) == '='){
				hasEqualsSign = true;
			}
		}
		return hasEqualsSign;
	}
	
	/**
	 * Compiles a list of terms in a given expression. Don't send this
	 * method an equation.
	 * @return An ArrayList of Terms.
	 */
	public static <Term>ArrayList getTerms(){
		
		return null;//TODO
		
		
	}
	
	/**
	 * Evaluates a char to see if it is a numeral
	 * @param c The char to evaluate
	 * @return If the char is a numeral
	 */
	public static boolean isNumeral(char c){
		switch (c){
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
				return true;
			default: return false;
		}
	}

	/**
	 * Tells if a string can be parsed as an integer
	 * @param intager The string to check 
	 * @return If the string can be parsed as an integer
	 */
	public static boolean isInteger(String integer) {
		Solve4x.debug("isInteger(" + integer + ")");
		//iterate through chars
		
		boolean allAreNumerals = true;
		for(int i = 0; i<integer.length(); i++){
			//if it's not a numeral
			if(!isNumeral(integer.charAt(i))){
				allAreNumerals = false;
				//no use going farther
				break;
			}
		}
		
		//check to see if it has commas (which would have caused the above to fail)
		
		//start  be reversing the integer. This will make it easier to check the commas
		integer = new StringBuilder(integer).reverse().toString();
		boolean commasInOrder = true;
		//now iterate through the string
		for(int i = 0; i<integer.length(); i++){
			//every fourth char should be a comma
			//i+1 is the actual iteration number
			if((i+1) % 4 == 0){
				//then it should be a comma
				if(integer.charAt(i) != ','){
					//if it's not a comma
					commasInOrder = false;
					//no use going farther
					break;
				}
			}
			//if should be an integer but isn't
			else if(!isNumeral(integer.charAt(i))){
				commasInOrder = false;
				//no use going farther
				break;
			}
		}
		//only one of the above needs algorithms needs to have succeeded
		Solve4x.debug("returns" + (allAreNumerals || commasInOrder));
		return allAreNumerals || commasInOrder;
	}


}
