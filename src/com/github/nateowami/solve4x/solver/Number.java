/*
    Solve4x - An algebra solver that shows its work
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
import java.nio.charset.MalformedInputException;

import com.github.nateowami.solve4x.Solve4x;

/**
 * Represents a integer or decimal such as 35, 45.25, 56.36, 3.1415926535, and 
 * provides methods for adding, subtracting, multiplying, and dividing numbers. (TODO)
 * @author Nateowami
 */
public class Number extends AlgebraicParticle{
	
	//The numerical part of a mixed number (could be a decimal)
	private String integerPart, decimalPart;
	
	/**
	 * Constructs a new number.
	 * @param num The number to parse into a Number. Examples: 2.67, 15, 0.34, 3.1415
	 * @throws MalformedInputException if num is not parsable as a decimal.
	 */
	protected Number(String num) throws MalformedInputException{
		Solve4x.debug("Parsing number: " + num);
		
		//check for empty string
		if(num.length() == 0){
			throw new MalformedInputException(0);
		}
		//if it's just a numeral
		if(Util.isInteger(num)){
			integerPart = num;
		}
		//if the '.' char exists in the string num and only once
		else if(num.indexOf('.') != -1 && (num.length() - num.replace(".", "").length()) == 1 
				//and both sides are integers
				&& Util.isInteger(num.substring(0, num.indexOf('.'))) 
				&& Util.isInteger(num.substring(num.indexOf('.') + 1, num.length()))){
			integerPart = num.substring(0, num.indexOf('.'));
			//TODO decide whether the decimal part should be added in the case of e.g. "1.0"
			decimalPart = num.substring(num.indexOf('.') + 1);
		}
		//not a valid number
		else{
			throw new MalformedInputException(num.length());
		}
	}

	 /**
	 * Adds two Numbers and returns the result
	 * @param n1 The first Number
	 * @param n2 The second Number
	 * @return A Number equal to the value of the two numbers added
	 * @throws MalformedInputException If the 
	 */
	public static Number add(Number n1, Number n2) throws MalformedInputException{
		//convert to strings, add, and convert back to a Number
		return new Number(add(n1.getAsString(), n2.getAsString()));
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
	 * Return a string representation of this Number, not the traditional toString()
	 * @return This Number in a string format. 
	 */
	public String getAsString(){
		return (this.sign() ? "" : "-") + integerPart + (decimalPart == null ? "" : "." + decimalPart);
	}

	/**
	 * Tells if a string is a number. It must have at least one numeral followed by 
	 * any number of numerals, and then (optionally) a decimal and at least on numeral.
	 * @param number The number to check
	 * @return If it's a number and/or fraction combination
	 */
	public static boolean isNumber(String number) {
		//make sure it's an int with at least one char followed (optionally) by 
		//a decimal and any number of numerals
		return number.matches("^-?[0-9]+(\\.[0-9]+)?$");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Number ["
				+ (integerPart != null ? "integerPart=" + integerPart + ", "
						: "")
				+ (decimalPart != null ? "decimalPart=" + decimalPart : "")
				+ "]";
	}
	
	/**
	 * @return The integer-part (the part from before the decimal)
	 */
	public String getIntegerPart() {
		return integerPart;
	}
	
	/**
	 * @return The decimal part (the part after the decimal point)
	 */
	public String getDecimalPart() {
		return decimalPart;
	}
	
}
