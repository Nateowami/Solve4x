/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Solve4x project

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

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents a number/fraction/mixed-number such as 35, 45/6, 56&lt;36&gt;&lt;32&gt;, and 
 * provides methods for adding, subtracting, multiplying, and dividing numbers. (TODO)
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
		Solve4x.debug("Parsing number: " + num);
		//parse the number
		
		//check for a negative sign
		if(num.length() > 0 && num.charAt(0) == '-'){
			//delete the sign from num
			num = num.substring(1, num.length());
			//and set the sign negative
			this.sign = false;
		}
		//parse the numeral
		for(int i = 0; i<num.length(); i++){
			//if the current char is not a numeral
			if(!Util.isNumeral(num.charAt(i))){
				//set the wholeNumber
				this.wholeNumber = num.substring(0, i);
				//and remove it from num
				num = num.substring(i, num.length());
				break;
			}
			//else if it's the last char
			else if(i == num.length()-1){
				this.wholeNumber = num;
				num = "";
			}
		}
		//parse the fraction part if it exists
		if(num.length() != 0){
			parseFraction(num);
		}
	}
	
	/**
	 * Parses the fraction part of a number and initialises the vars top and bottom
	 * @param frac The fraction to parse
	 */
	private void parseFraction(String frac){
		Solve4x.debug("Frac is: "+frac);
		Solve4x.debug(frac);
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
		//make sure the denominators (if any) are identical
		//make sure that if they both have denominators they are equal
		if(!n1.getBottom().equals(n2.getBottom()) 
				//and they both exist
				&& n1.bottom.length() != 0 && n2.bottom.length() != 0){
			//if they're not identical AND they both exist
			throw new IllegalArgumentException("Cannot add two fractions with different denominators.");
		}
		//Now that we have that out of the way
		//create a new blank number
		Number number = new Number();
		//ADD THE WHOLE NUMBERS
		//it's not guaranteed that they both have a whole number/decimal part, so add carefully
		//if it's found that the whole number part doesn't exist, just count it as 0
		number.wholeNumber = add(n1.coefficient(), n2.coefficient());
		//IF THERE ARE FRACTIONS
		if(n1.top.length() != 0 || n2.top.length() != 0){
			//if they both have fractions
			if(n1.top.length() != 0 && n2.top.length() != 0){
				//add the two fractions; they're both there
				//add the tops
				number.top = add(n1.top, n2.top);
				//and copy the bottom (which we know are the same; there would be an exception if they weren't)
				number.bottom = n1.bottom;
			}
			//else if only the first has a fraction
			else if(n1.top.length() != 0){
				//copy the fraction from the first number; it's the only one that has a fraction
				number.top = n1.top;
				number.bottom = n1.bottom;
			}
			//else it must be the second one that has a fraction
			else{
				//copy the fraction form the second number; it's the only one that has a fraction
				number.top = n2.top;
				number.bottom = n2.bottom;
			}
		}
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
	
	/**
	 * Gives a string representation of this number
	 * @return This number in a string format. For example, 3.46&lt;45&gt;/&lt;56&gt;
	 */
	public String getAsString(){
		//combine all the major fields into a string
		return (sign ? "" : "-") + this.wholeNumber + this.top + this.bottom;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Number [wholeNumber=" + wholeNumber + ", sign=" + sign
				+ ", top=" + top + ", bottom=" + bottom + "]";
	}

	/**
	 * @return The sign of the number. Either '+' or '-'.
	 */
	public char sign(){
		return this.sign ? '+' : '-';
	}

	/**
	 * Tells if a string is a number. Examples: 1234, &lt;123&gt;/&lt;34&gt;, 34/34, 12&lt;23&gt;/&lt;67&gt;
	 * @param number The number to check
	 * @return If it's a number and/or fraction combination
	 */
	public static boolean isNumber(String number) {
		//TODO support decimals and roots
		Solve4x.debug("Checking if it's a number: " + number);
		
		//i is our marker for how far we've checked
		int i = 0;
		//check for the first char being - or +
		if(number.charAt(0) == '-' || number.charAt(0) == '+'){
			//if it is, pass by it
			i++;
		}
		
		//now iterate through the first part as long as all are 0-9
		for(;i<number.length()&& Util.isNumeral(number.charAt(i)); i++){
			//if we've reached the end
			if(i==number.length()-1){
				//we've reached the end without problems
				Solve4x.debug("Returning true");
				return true;
			}
		}
		
		//if we've gotten this far at least part of the number must be a fraction, or
		//it's not a number at all. Check the rest of it for being a fraction.
		if(Util.isFraction(number.substring(i, number.length()))){
			Solve4x.debug("Returning true");
			return true;
		}
		
		//the end. if it didn't work, then, well, it wasn't a nice number
		else {
			Solve4x.debug("Returning false");
			return false;
		}
	}
	
	/**
	 * The coefficient and sign of the Number combined. If it is Negative it will be a '-' sign,
	 * even if there is no whole number part of the term. The fraction will not be included.
	 * @return A representation of the coefficient (but not the fraction part) of the term.
	 */
	private String coefficient(){
		return (sign ? "" : "-") + (wholeNumber.length() == 0? "0" : wholeNumber);
	}
	
}
