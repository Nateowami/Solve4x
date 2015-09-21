/*
    Solve4x - An algebra solver that shows its work
    Copyright (C) 2015  Nathaniel Paulus

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
import java.math.MathContext;
import java.math.RoundingMode;

import com.github.nateowami.solve4x.config.RoundingRule;

/**
 * Represents a integer or decimal such as 35, 45.25, 56.36, 3.1415926535, 2.4*10⁴ and 
 * provides methods for adding, subtracting, multiplying, and dividing numbers.
 * @author Nateowami
 */
public class Number extends AlgebraicParticle{
	
	//The numerical part of a mixed number (could be a decimal)
	private String integer, decimal;
	//the exponent of 10, if this is scientific notation, e.g., in 2.3*10⁴ this would be 4
	private Integer sciExponent = null;
	public static final Number ZERO = new Number("0"), ONE = new Number("1"), NEGATIVE_ONE = new Number(false, "1", null, null, 1);
	
	/**
	 * Constructs a new number.
	 * @param num The number to parse into a Number. Examples: 2.67, 15, 0.34, 3.1415
	 */
	public Number(String num) {
		//check for scientific notation
		int a = num.indexOf("*10");
		if(a != -1){
			sciExponent = Util.superscriptToInt(num.substring(a+3));
			//remove the *10⁸ part, or whatever it happens to be
			num = num.substring(0, a);
		}

		int i = num.indexOf('.');
		if(i == -1){
			this.integer = num;
		}
		else {
			String integ = num.substring(0, i), dec = num.substring(i+1);
			if(integ.length() != 0) this.integer = integ;
			if(dec.length() != 0) this.decimal = dec;
		}
	}
	
	/**
	 * Constructs a new Number from a sign, integer part, decimal part,  
	 * @param sign The sign of the number.
	 * @param integer The part of the number before the decimal point (may be null or empty).
	 * @param decimal The part of the number after the decimal point (may be null or empty).
	 * @param sciExponent The exponent of 10 if this number is in scientific notation (may be null).
	 * @param exponent The exponent of the number.
	 */
	public Number(boolean sign, String integer, String decimal, Integer sciExponent, int exponent){
		super(sign, exponent);
		if(integer != null && !integer.isEmpty()) this.integer = integer;
		if(decimal != null && !decimal.isEmpty()) this.decimal = decimal;
		this.sciExponent = sciExponent; 
	}
	
	 /**
	  * @return A BigDecimal representing this number, with precision set to the number 
	  * of signification figures in this number, and a rounding mode of HALF_UP.
	  */
	protected BigDecimal toBigDecimal() {
		if(this.exponent() != 1) throw new IllegalArgumentException("Connot a number with an exponent to BigDecimal.");
		
		return new BigDecimal(
				(sign() ? "" : "-") + integer + (decimal == null ? "" : "." + decimal) + (sciExponent == null ? "" : "e" + sciExponent)
			);
	}
	
	/**
	 * Converts bd to Number.
	 * @param bd A BigDecimal to be converted to Number.
	 * @return bd, converted to Number.
	 */
	protected static Number toNumber(BigDecimal bd){
		String s = bd.toString();
		int dot = s.indexOf('.'), e = s.indexOf('E', dot+1);
		//change e to the end if there is no exponent
		if(e == -1) e = s.length();
		//change dot to e if there is no dot
		if(dot == -1) dot = e;
		String integer = s.substring(bd.signum() == -1 ? 1 : 0, dot);
		String decimal = dot >= e ? null : s.substring(dot+1, e);
		Integer sciExponent = null;
		//+2 takes care of the 'e' and sign characters, e.g. "e+3"; multiplying by -1 accounts for the sign
		if(e < s.length()) sciExponent = new Integer(s.substring(e+2)) * (s.charAt(e+1) == '-' ? -1 : 1);
		//set the sign for the sciExponent
		return new Number(bd.signum() == -1 ? false : true, integer, decimal, sciExponent, 1);
	}	
	
	/**
	 * Adds two numbers. If you want to subtract, make the second number negative and add.
	 * @param n1 The first number to add.
	 * @param n2 The second number to add.
	 * @param round The rounding rules to use when adding.
	 * @return The value of the two numbers added.
	 * @throws IllegalArgumentException
	 */
	public static Number add(Number n1, Number n2, RoundingRule round) throws IllegalArgumentException{
		//create the decimals and add
		BigDecimal a = n1.toBigDecimal();
		BigDecimal b = n2.toBigDecimal();
		BigDecimal result = a.add(b);
		
		if(round.isCannedRule()){
			if(shouldUsePrecisionRules(round, n1, n2)){
				//use the smaller of the two scales
				return toNumber(result.setScale(a.scale() > b.scale() ? b.scale() : a.scale(), RoundingMode.HALF_UP));
			}
			//don't use precision rules; use the greater scale
			else return toNumber(result);
		}
		//it's a custom rule, with r.getValue() the max number of significant decimal places to leave
		else{
			//use whichever is smaller of r.getValue() and result.scale()
			return toNumber(result.setScale(round.getValue() < result.scale() ? round.getValue() : result.scale(), RoundingMode.HALF_UP));
		}
	}
	
	/**
	 * Multiplies n1 by n2 and returns the result.
	 * @param n1 The first number to multiply by.
	 * @param n2 The second number ot multiply by.
	 * @param round The RoundingRule to govern rounding.
	 * @return n1 multiplied by n2.
	 */
	public static Number multiply(Number n1, Number n2, RoundingRule round) {
		BigDecimal a = n1.toBigDecimal();
		BigDecimal b = n2.toBigDecimal();
		boolean rules = shouldUsePrecisionRules(round, n1, n2);
		
		int precision = rules ? Math.min(a.precision(), b.precision()) : a.precision() + b.precision();
		
		BigDecimal result = a.multiply(b, new MathContext(precision));
		//if it's a custom rule that specifies the max number of digits after the decimal point\
		if(!round.isCannedRule() && result.scale() > round.getValue()) result = result.setScale(round.getValue(), RoundingMode.HALF_UP);
		if(!rules) result = result.stripTrailingZeros();
		
		return toNumber(result);
	}
	
	/**
	 * Divides n1 by n2 and returns the result.
	 * @param n1 The dividend.
	 * @param n2 The divisor.
	 * @param round The RoundingRule to govern rounding.
	 * @return n1 divided by n2.
	 */
	public static Number divide(Number n1, Number n2, RoundingRule round) {
		BigDecimal a = n1.toBigDecimal();
		BigDecimal b = n2.toBigDecimal();
		boolean rules = shouldUsePrecisionRules(round, n1, n2);
		
		int precision = Math.min(a.precision(), b.precision());
		
		BigDecimal result = null;
		//if using sig fig rules
		if(rules) result = a.divide(b, new MathContext(precision));
		//if it's a canned rule but not using sig fig rules, round to two decimal places
		else if(round.isCannedRule()) result = a.divide(b, 2, RoundingMode.HALF_UP);
		//not a canned rule, so used specified number of decimal places
		else result = a.divide(b, round.getValue(), RoundingMode.HALF_UP);
		
		if(!rules) result = result.stripTrailingZeros();
		
		return toNumber(result);
	}
	
	/**
	 * Return a string representation of this Number, not the traditional toString()
	 * @return This Number in a string format. 
	 */
	public String render(){
		return wrapWithSignParAndExponent(
				integer + (decimal == null ? "" : "." + decimal) + 
						(sciExponent == null ? "" : "*10" + Util.toSuperscript(Integer.toString(sciExponent))),
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
	public static boolean parsable(String num) {
		//check for empty string
		if(num.length() == 0){
			return false;
		}
		
		//check for scientific notation
		int a = num.indexOf("*10");
		//if there's an asterisk
		if(a != -1){
			//if it's in the proper format
			if(a > 0 && a < num.length() && Util.isSuperscript(num.substring(a+3))){
				//remove the *10⁸ part, or whatever it happens to be
				num = num.substring(0, a);
			}
			else return false;
		}
		//now keep working ignoring that there may have been scientific notation that got removed
		
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
	public Number cloneWithNewSign(boolean sign){
		if(this.sign() == sign) return this;
		return new Number(sign, 
				this.integer, 
				this.decimal, 
				this.sciExponent,
				this.exponent()
				);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Number [integer=" + integer + ", decimal=" + decimal
				+ ", sciExponent=" + sciExponent + ", sign()=" + sign()
				+ ", exponent()=" + exponent() + "]";
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
	 * Tells if this Number is an integer, that is, that it has no decimal part and is not in 
	 * scientific notation.
	 * @return True if this number is an integer, otherwise false.
	 */
	public boolean isInteger() {
		return this.decimal == null && this.sciExponent == null;
	}
	
	/**
	 * @return The exponent of 10, if the number is in scientific notation. For example, 
	 * in 2.3*10⁴ it would be 4. Normally this will not be present, and hence this will 
	 * return null.
	 */
	public Integer getScientificNotationExponent(){
		return this.sciExponent;
	}
	
	/**
	 * @return The number of significant figures in this number (sum of integer length and decimal length).
	 */
	public int sigFigs(){
		return (this.integer == null ? 0 : this.integer.length()) + (this.decimal == null ? 0 : this.decimal.length());
	}
	
	/**
	 * Tells if n1 and n2 should be rounded "normally," i.e., that they should be rounded using 
	 * the standard rules for precision and significant figures. Basically, given r and two 
	 * numbers, it tells if r specifies that they should be added with the standard rules.
	 * @param r The RoundingRule for rounding n1 and n2.
	 * @param n1 The first number.
	 * @param n2 The second number.
	 * @return If n1 and n2 should be added with standard rules for precision and significant figures
	 */
	private static boolean shouldUsePrecisionRules(RoundingRule r, Number n1, Number n2){
		return 
				r == RoundingRule.ALWAYS || 
				//or we're supposed to use precision rules for rounding except for whole numbers, and one isn't a whole number
				(r == RoundingRule.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS && (!n1.isInteger() || !n2.isInteger())) ||
				//or we're supposed to use precision rules only for scientific notation, and one is in scientific notation 
				(r == RoundingRule.FOR_SCIENTIFIC_NOTATION && (n1.sciExponent != null || n2.sciExponent != null));
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
		result = prime * result
				+ ((sciExponent == null) ? 0 : sciExponent.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && almostEquals(obj);
	}
	
	public boolean almostEquals(Object obj) {
		if (this == obj)
			return true;
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
		if (sciExponent == null) {
			if (other.sciExponent != null)
				return false;
		} else if (!sciExponent.equals(other.sciExponent))
			return false;
		return true;
	}
	
}
