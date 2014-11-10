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



/**
 * Holds a fraction and integer value, which can be used as an exponent.
 * @author Nateowami
 */
public class MixedNumber extends AlgebraicParticle{
	
	private int numeral;
	private Fraction fraction;
	
	/**
	 * Constructs a new Mixed Number
	 * @param frac The string from which to construct it.
	 */
	protected MixedNumber(String frac) {
		//figure out how much is the numeral
		int i = 0;
		while(i < frac.length() && frac.charAt(i) >= '0' && frac.charAt(i) <= '9')i++;
		//set the integer part
		this.numeral = Integer.parseInt(frac.substring(0, i));
		this.fraction = new Fraction(frac.substring(i));
	}

	/**
	 * Tells if s is a Mixed Number.
	 * @param frac The string to check.
	 * @return If frac can be parsed as a MixedNumber.
	 */
	public static boolean parseable(String frac){
		//basically this is a match of the regex \d+\(\d+\)/\(\d+\), e.g. 2(3)/(4)
		//figure out how much is the numeral
		int i = 0;
		while(i < frac.length() && frac.charAt(i) >= '0' && frac.charAt(i) <= '9')i++;
		//i is now index of last integer char
		if(i == 0) return false; //integer part is required
		//remove the integer part
		frac = frac.substring(i);
		//make sure frac is in the form of (2)/(3)
		if (frac.length() < 7) return false;//there's no way for it to be parseable and less than 7 chars
		if (frac.charAt(0) != '(' || frac.charAt(frac.length()-1) != ')') return false;
		frac = frac.substring(1, frac.length()-1);//remove first and last chars
		int middle = frac.indexOf(")/(");
		if (middle < 1) return false;
		String top = frac.substring(0, middle), bottom = frac.substring(middle+3, frac.length());
		try{
			//they must be integers and the bottom must be greater
			return Integer.parseInt(top) < Integer.parseInt(bottom);
		} catch(NumberFormatException e){
			return false;
		}
	}
	
	/**
	 * @return The fraction part of the MixedNumber.
	 */
	public Fraction getFraction() {
		return fraction;
	}

	/**
	 * @return The numeral part of the MixedNumber.
	 */
	public int getNumeral() {
		return numeral;
	}

	/**
	 * Tells if the mixed number is fully simplified, that is, that the fraction part is
	 * positive (both on top and bottom) and is fully simplified.
	 * @return If the mixed number is simplified.
	 * @see com.github.nateowami.solve4x.solver.Fraction#isSimplified()
	 */
	public boolean isSimplified(){
		return this.fraction.isSimplified() && ((Number) this.fraction.top).sign() == true;
	}
	
	/**
	 * @return A string representation of the MixedNumber
	 */
	public String getAsString(){
		return wrapWithSignAndExponent(numeral + fraction.getAsString());
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MixedNumber [numeral=" + numeral + ", fraction=" + fraction
				+ ", sign()=" + sign() + ", exponent()=" + exponent() + "]";
	}
	
}
