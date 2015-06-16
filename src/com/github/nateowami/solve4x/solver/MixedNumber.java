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

import com.github.nateowami.solve4x.config.RoundingRule;

/**
 * Holds a fraction and integer value, which can be used as an exponent.
 * @author Nateowami
 */
public class MixedNumber extends AlgebraicParticle{
	
	private Number numeral;
	private Fraction fraction;
	
	/**
	 * Constructs a new Mixed Number
	 * @param frac The string from which to construct it.
	 */
	protected MixedNumber(String frac) {
		//set the integer part
		this.numeral = new Number(frac.substring(0, frac.indexOf('(')));
		this.fraction = new Fraction(frac.substring(frac.indexOf('(')));
	}
	
	/**
	 * Constructs a new MixedNumber.
	 * @param numeral The numeral part of the number.
	 * @param fraction The fraction part of the number.
	 */
	public MixedNumber(Number numeral, Fraction fraction) {
		this.numeral = numeral;
		this.fraction = fraction;
	}
	
	/**
	 * Constructs a new MixedNumber.
	 * @param sign The sign of the MixedNumber.
	 * @param numeral The numeral part of the MixedNumber.
	 * @param fraction The fraction part of the MixedNumber.
	 * @param exponent The exponent of the MixedNumber.
	 */
	public MixedNumber(boolean sign, Number numeral, Fraction fraction, int exponent) {
		super(sign, exponent);
		this.numeral = numeral;
		this.fraction = fraction;
	}
	
	/**
	 * Tells if s is a Mixed Number.
	 * @param s The string to check.
	 * @return If s can be parsed as a MixedNumber.
	 */
	public static boolean parseable(String s){
		int j = s.indexOf('(');
		if(j < 1) return false;
		if(!Util.areAllNumerals(s.substring(0, j)))return false;
		String frac = s.substring(j);
		//make sure frac matches (<numerals>)/(<numerals>)
		int i = frac.indexOf(")/(");
		return i > 1 && frac.charAt(0) == '(' && frac.charAt(frac.length()-1) == ')' 
				&& Util.areAllNumerals(frac.substring(1, i)) && Util.areAllNumerals(frac.substring(i+3, frac.length() - 1));
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
	public Number getNumeral() {
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
	public String render(){
		return wrapWithSignParAndExponent(numeral.render() + fraction.render(), true);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MixedNumber [numeral=" + numeral + ", fraction=" + fraction
				+ ", sign()=" + sign() + ", exponent()=" + exponent() + "]";
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSign(java.lang.Boolean)
	 */
	@Override
	public MixedNumber cloneWithNewSign(Boolean sign) {
		return new MixedNumber(sign == null ? this.sign() : sign,
				this.numeral,
				this.fraction,
				this.exponent()
				);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((fraction == null) ? 0 : fraction.hashCode());
		result = prime * result + ((numeral == null) ? 0 : numeral.hashCode());
		return result;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return almostEquals(obj) && super.equals(obj);
	}
	
	public boolean almostEquals(Object obj){
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		MixedNumber other = (MixedNumber) obj;
		if (fraction == null) {
			if (other.fraction != null)
				return false;
		} else if (!fraction.equals(other.fraction))
			return false;
		if (numeral == null) {
			if (other.numeral != null)
				return false;
		} else if (!numeral.equals(other.numeral))
			return false;
		return true;
	}
	
	/**
	 * Adds a and b.
	 * @param a The first MixedNumber to add.
	 * @param b The second MixedNumber to add.
	 * @return a and b added, which is not necessarily a MixedNumber; it could be a whole number.
	 */
	public static AlgebraicParticle add(MixedNumber a, MixedNumber b, RoundingRule round) {
		//if the mixednumbers are negative, make the numbers negative before adding
		Number numA = a.numeral, numB = b.numeral;
		Fraction fracA = a.fraction, fracB = b.fraction;
		if(!a.sign()){numA = numA.cloneWithNewSign(false); fracA = fracA.cloneWithNewSign(false);}
		if(!b.sign()){numB = numB.cloneWithNewSign(false); fracB = fracB.cloneWithNewSign(false);}
		
		//Number numA = a.sign() ? a.numeral : a.numeral.cloneWithNewSign(false);
		//Number numB = b.sign() ? b.numeral : b.numeral.cloneWithNewSign(false);
		//Fraction fracA = 
		
		
		Number sum = Number.add(numA, numB, round);
		Fraction fracSum = Fraction.add(fracA, fracB, round);
		//the sign of the new mixednumber should be the sign of the sum of the two numbers
		boolean sign = sum.sign();
		if (!sum.sign()) sum = sum.cloneWithNewSign(true);
		if (!fracSum.sign()) fracSum = fracSum.cloneWithNewSign(true);
		if (fracSum.getTop().equals(Number.ZERO)) return sum;
		return new MixedNumber(sign, sum, fracSum, 1);
	}
	
}
