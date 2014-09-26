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

import java.nio.charset.MalformedInputException;

/**
 * Holds a fraction and integer value, which can be used as an exponent.
 * @author Nateowami
 */
public class MixedNumber extends AlgebraicParticle{
	
	Integer numeral;
	ConstantFraction fraction;
	
	/**
	 * Constructs a new Mixed Number
	 * @param s The string from which to construct it.
	 * @throws MalformedInputException If s is improperly formatted.
	 */
	protected MixedNumber(String s) throws MalformedInputException{
		//if it's just a plain fraction
		if(ConstantFraction.isConstantFraction(s)){
			this.fraction = new ConstantFraction(s);
			return;
		}
		
		//if it's just an int
		if(Util.isInteger(s)){
			this.numeral = Integer.parseInt(s);
			return;
		}
		
		//figure out how much is the numeral
		int i = s.length();
		for(; i > 0 && !Util.isInteger(s.substring(0, i)); i--);
		//i is now index of last integer char
		this.numeral = Integer.parseInt(s.substring(0, i));
		//parse the fraction
		if(ConstantFraction.isConstantFraction(s.substring(i))){
			this.fraction = new ConstantFraction(s.substring(i));
		}
		else throw new MalformedInputException(0);
	}

	/**
	 * Tells if s is a Mixed Number.
	 * @param s The string to check.
	 * @return If s can be parsed as a MixedNumber.
	 */
	public static boolean parseable(String s){
		//if it's just a fraction
		if(ConstantFraction.isConstantFraction(s)){
			return true;
		}
		
		//if it's just an int
		if(Util.isInteger(s)){
			return true;
		}
		
		//figure out how much is the numeral
		int i = 0;
		for(; i < s.length() && Util.isInteger(s.substring(0, i+1)); i++);
		//i is now index of last integer char
		if(i == 0) return false; //number is required
		//parse the fraction
		System.out.println(s.substring(i+1));
		if(ConstantFraction.isConstantFraction(s.substring(i))){
			return true;
		}
		else return false;
	}
	
	/**
	 * Tells if the the MixedNumber contains a numeral. 
	 * Example:
	 * 2 - true
	 * 1/3 -false, fraction only
	 * 1(2)/(-3) -true, 1 is the numeral
	 * @return True if the MixedNumber contains a numeral, otherwise false.
	 */
	public boolean hasNumeral(){
		return this.numeral != null;
	}
	
	/**
	 * Tells if the MixedNumber contains a fraction.
	 * Example:
	 * 2 - false, no fraction
	 * 1/3 -true
	 * 1(2)/(-3) -true, (2)/(-3) is the fraction
	 * @return True if the MixedNumer contains a fraction, otherwise false.
	 */
	public boolean hasFraction(){
		return this.fraction != null;
	}
	
	/**
	 * @return The fraction part of the MixedNumber.
	 */
	public ConstantFraction getFraction() {
		return fraction;
	}

	/**
	 * @return The numeral part of the MixedNumber.
	 */
	public int getNumeral() {
		return numeral;
	}

	/**
	 * @return A string representation of the MixedNumber
	 */
	public String getAsString(){
		return wrapWithSignAndExponent(
				(numeral == null ? "" : numeral) 
				+ (fraction == null ? "" : ( "(" + fraction.getTop().getAsString() + ")/(" + fraction.getBottom().getAsString()) + ")"));
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MixedNumber [" + (fraction != null ? "frac=" + fraction + ", " : "")
				+ "numeral=" + numeral + "]";
	}
	
}
