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

import java.nio.charset.MalformedInputException;

/**
 * Holds a fraction and integer value, which can be used as an exponent.
 * @author Nateowami
 */
public class MixedNumber {
	
	int numeral;
	ConstantFraction fraction;
	
	/**
	 * Constructs a new Mixed Number
	 * @param s The string from which to construct it.
	 * @throws MalformedInputException If s is improperly formatted.
	 */
	public MixedNumber(String s) throws MalformedInputException{
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
	public static boolean isMixedNumber(String s){
		//figure out how much is the numeral
		int i = 0;
		for(; i < s.length() && Util.isInteger(s.substring(0, i+1)););
		//i is now index of last integer char
		if(i == 0) return false; //number is required
		//parse the fraction
		if(ConstantFraction.isConstantFraction(s.substring(i+1))){
			return true;
		}
		else return false;
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
		return fraction.getAsString() + numeral;
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
