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

/**
 * Represents a integer or decimal such as 35, 45.25, 56.36, 3.1415926535, and 
 * provides methods for adding, subtracting, multiplying, and dividing numbers. (TODO)
 * @author Nateowami
 */
public class Number extends AlgebraicParticle{
	
	//The numerical part of a mixed number (could be a decimal)
	private String integer, decimal;
	public static final Number ZERO = new Number("0"), ONE = new Number("1"), NEGATIVE_ONE = new Number(false, "1", null, 1);
	
	/**
	 * Constructs a new number.
	 * @param num The number to parse into a Number. Examples: 2.67, 15, 0.34, 3.1415
	 */
	public Number(String num) {
		int i = num.indexOf('.');
		if(i == -1){
			this.integer = num;
		}
		else {
			this.integer = num.substring(0, i);
			String dec = num.substring(i+1);
			//make sure dec isn't just 0's
			if(dec.length() > 0 && (dec.charAt(0) != '0' || dec.length() > 1)){
				for(int j = 0; j < dec.length(); j++){
					if (dec.charAt(j) != '0'){
						this.decimal = dec;
						return;
					}
				}
			}
		}
	}

	public Number(boolean sign, String integer, String decimal, int exponent){
		super(sign, exponent);
		this.integer = integer;
		this.decimal = decimal;
	}
	
	 /**
	 * Adds two Numbers and returns the result
	 * @param n1 The first Number
	 * @param n2 The second Number
	 * @return A Number equal to the value of the two numbers added
	 * @throws IllegalArgumentException if either of the numbers have exponents other than 1.
	 */
	public static Number add(Number n1, Number n2) {
		if(n1.exponent() != 1 || n2.exponent() != 1) throw new IllegalArgumentException("Connot add numbers with exponents.");
		//convert to strings, add, and convert back to a Number
		return (Number) AlgebraicParticle.getInstance(add(n1.getAsString(), n2.getAsString()), null);
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
		if(Util.areAllNumerals(n1) && Util.areAllNumerals(n2)){
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
		return wrapWithSignParAndExponent(
				integer + (decimal == null ? "" : "." + decimal),
				false);
	}
	
	/**
	 * @return true if the Number is equal to zero, otherwise false.
	 */
	public boolean isZero(){
		return this.decimal == null && this.integer.equals("0");
	}
	
	/**
	 * Tells if a string is a number. It must have at least one numeral followed by 
	 * any number of numerals, and then (optionally) a decimal and at least on numeral.
	 * @param num The number to check
	 * @return If it's a number and/or fraction combination
	 */
	public static boolean parseable(String num) {
		//check for empty string
		if(num.length() == 0){
			return false;
		}
		int d = num.indexOf('.');
		//check that the decimal point doesn't exist and num doesn't start with 0 unless length is one
		if(d == -1 && (num.charAt(0) != '0' || num.length() == 1) && Util.areAllNumerals(num)) return true;
		//make sure the decimal point exists and that the first char isn't '0' OR the second char is '.'
		else if(d > 0 && (d == 1 || num.charAt(0) != '0') && 
				//make sure both sides of the decimal have only integers
				Util.areAllNumerals(num.substring(0, num.indexOf('.'))) && Util.areAllNumerals(num.substring(num.indexOf('.')+1))){
			return true;
		}
		else return false;
	}

	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSign(java.lang.Boolean)
	 */
	@Override
	public Number cloneWithNewSign(Boolean sign){
		return new Number(sign == null ? this.sign() : sign, 
				this.integer, 
				this.decimal, 
				this.exponent()
				);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Number [integer=" + integer + ", decimal=" + decimal
				+ ", sign()=" + sign() + ", exponent()=" + exponent() + "]";
	}
	
	/**
	 * @return The integer part (the part from before the decimal)
	 */
	public String getInteger() {
		return integer;
	}
	
	/**
	 * @return The decimal part (the part after the decimal point)
	 */
	public String getDecimal() {
		return decimal;
	}
	
	/**
	 * Tells the GCF (greatest common factor, aka GCD, greatest common deviser) of a and b.
	 * Both a and b must be positive.
	 * @param a The first number.
	 * @param b The second number.
	 * @return The GCF (greatest common factor) of a and b.
	 */
	public static long GCF(long a, long b){
		//Implementation of the Euclidean algorithm http://en.wikipedia.org/wiki/Euclidean_algorithm
		while (b > 0){
			long temp = b;
	    	b = a % b;
	    	a = temp;
		}
	    return a;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((decimal == null) ? 0 : decimal.hashCode());
		result = prime * result + ((integer == null) ? 0 : integer.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Number other = (Number) obj;
		if (decimal == null) {
			if (other.decimal != null)
				return false;
		} else if (!decimal.equals(other.decimal))
			return false;
		if (integer == null) {
			if (other.integer != null)
				return false;
		} else if (!integer.equals(other.integer))
			return false;
		return true;
	}

		
}
