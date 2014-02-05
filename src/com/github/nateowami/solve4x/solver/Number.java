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

import java.math.BigDecimal;

/**
 * Represents a number/fraction/mixed-number such as 35, 45/6, 56&lt;36&gt;&lt;32&gt;, and 
 * provides methods for adding, subtracting, multiplying, and dividing numbers.
 * @author Nateowami
 */
public class Number {
	
	//The numerical part of a mixed number (could be a decimal)
	private String wholeNumber = "";
	//the positive/negative value on the number
	private boolean sign = true;
	//the top of a fraction
	private String top = "";
	//and the bottom
	private String bottom = "";
	
	/**
	 * Constructs a new empty Number
	 */
	public Number(){}
	
	/**
	 * Constructs a new number. Calling it with an empty string will throw errors (index out of bounds)
	 * @param num The number to parse into a Number. Example: 45&lt;67&gt;/&lt;98&gt;
	 */
	public Number(String num){
		//parse the number
		int i = 0;
		//check for a negative sign
		if(num.length() > 0 && num.charAt(0) == '-'){
			//make i move past the - sign
			i++;
			//and set the sign negative
			this.sign = false;
		}
		for(; i<num.length(); i++){
			//if the current char is not a numeral
			if(!Util.isNumeral(num.charAt(i))){
				break;
			}
		}
		//now we have the index of the first non-numeral, or else just the end of the string
		//(if they're all numerals)
		wholeNumber = num.substring(0, i);
		//parse the fraction part
		parseFraction(num.substring(i, num.length()));
	}
	
	/**
	 * Parses the fraction part of a number and initialises the vars top and bottom
	 * @param frac The fraction to parse
	 */
	private void parseFraction(String frac){
		//the index of the /
		int indexOfDiv = 0;
		for(int i = 0; i< frac.length(); i++){
			if(frac.charAt(i) == '/'){
				indexOfDiv = i;
				break;
			}
		}
		//now parse the top and bottom of the fraction, namely everything
		//before indexOfDiv and everything after it
		for(int i = 0; i<2; i++){
			//set sideOfFrac
			String sideOfFrac;
			if(i == 0) sideOfFrac = frac.substring(0, indexOfDiv);
			else sideOfFrac = frac.substring(indexOfDiv+1, frac.length());
			//remove any carets that may be on both sides
			if(sideOfFrac.charAt(0) == '<' && sideOfFrac.charAt(sideOfFrac.length()-1) == '>'){
				sideOfFrac = sideOfFrac.substring(1, sideOfFrac.length()-1);
			}
			if(i == 0) top = sideOfFrac;
			else bottom = sideOfFrac;
		}
	}

	/**
	 * @return the wholeNumber part of the mixed number
	 */
	public String getWholeNumber() {
		return wholeNumber;
	}

	/**
	 * @return the numerator of the fraction
	 */
	public String getTop() {
		return top;
	}

	/**
	 * @return the denominator of the fraction
	 */
	public String getBottom() {
		return bottom;
	}
	
	 /**
	 * Adds two numbers and returns the result
	 * @param n1 The first Number
	 * @param n2 The second Number
	 * @return A Number equal to the value of the two numbers added
	 * @throws IllegalArgumentException If the denominators of the fractions of the two numbers 
	 * (if they have fractions, that is) are not equal
	 */
	public static Number add(Number n1, Number n2) throws IllegalArgumentException{
		//FIXME This method is TOTALLY broken
		//make sure the denominators (if any) are identical
		if(!n1.getBottom().equals(n2.getBottom()))
			//if they're not identical
			throw new IllegalArgumentException("Cannot add two fractions with different denominators.");
		//Now that we have that out of the way
		//create a new blank number
		Number number = new Number();
		//if there is a whole number part in either one of the arguments passed
		if(n1.wholeNumber.length() > 0 || n2.wholeNumber.length() > 0)
			//add the whole number parts
			//make sure to put the - sign in if it's applicable
			number.wholeNumber = add((n1.sign ? "" : "-") + n1.bottom, (n2.sign ? "" : "-") + n2.bottom);
		//if there is numerator and denominator in BOTH fractions
		//we'll just say if there's a numerator there's a denominator
		if(n1.top.length() > 1 && n2.top.length() > 1){
			//add the tops, then copy the bottom
			number.top = add(n1.top, n2.top);
			//n1.bottom is equal to n2.bottom
			number.bottom = n1.bottom;
		}
		//if none of the numbers has a fraction
		else if(n1.top.length() == 0 && n2.top.length() == 0){
			//neither has a fraction; we can just return the number now
			return number;
		}
		//if we've gotten here one of the numbers is a fraction, the other isn't
		//if n1 has a fraction, numWithFraction = n1, otherwise it's n2
		Number numWithFraction = n1.top.length() > 0 ? n1 : n2;
		//now make the fraction in the var "number" be equal to the fraction in numWithFraction
		number.top = numWithFraction.top;
		number.bottom = numWithFraction.top;
		return number;
	}
	
	/**
	 * Adds two numbers represented by a String. This should not be confused with adding two Numbers;
	 * this only adds integer/decimal values represented by a string
	 * @param n1 The first number to add
	 * @param n2 The second number to add
	 * @return The value of two numbers added
	 * @throws IllegalArgumentException If the Strings cannot be parsed as ints or decimals. An empty String
	 * will throw an exception.
	 */
	private static String add(String n1, String n2) throws IllegalArgumentException{
		//first see if they are both ints
		if(Util.isInteger(n1) && Util.isInteger(n2)){
			//since they can be parsed as ints just convert to ints, add, and convert to String
			return (Integer.parseInt(n1) + Integer.parseInt(n2)) + "";
		}
		//if they're not ints, try adding them with BigDecimal
		BigDecimal dec1 = new BigDecimal(n1);
		BigDecimal dec2 = new BigDecimal(n2);
		//we don't need to worry about being too overly accurate. Assuming the number of 
		//decimal places is already realistic, it won't change a whole lot since we're just adding
		return dec1.add(dec2).toString();
	}
	
}
