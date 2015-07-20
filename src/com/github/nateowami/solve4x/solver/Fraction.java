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
 * Contains a fraction with expressions on top and bottom.
 * @author Nateowami
 */
public class Fraction extends AlgebraicParticle{
	
	protected AlgebraicParticle top, bottom;
	
	/**
	 * Constructs a new Fraction.
	 * @param frac The String from which to construct the fraction, in the form of
	 * <i>expression</i>/<i>expression</i>. If an expression has more than one term
	 * it must be surrounded by parentheses.
	 */
	protected Fraction(String frac){
		//find the '/' not nested in parentheses	
		int divisionIndex = indexOfSlash(frac);
		//if divisionIndex is 0 there's a problem
		if(divisionIndex < 1) throw new ParsingException("Non-parentheses-nested '/' not found in proper range (char 1 to end of string)");
		//split the fraction at divisionIndex, but don't include the slashes or parentheses
		//it should be in the form of (expr1)/(expr2)
		this.top = AlgebraicParticle.getInstance(frac.substring(1, divisionIndex-1));
		this.bottom = AlgebraicParticle.getInstance(frac.substring(divisionIndex+2, frac.length()-1));
	}
	
	/**
	 * Constructs a new fraction.
	 * @param sign The sign of the fraction.
	 * @param top The top of the fraction (numerator).
	 * @param bottom The bottom of the fraction (denominator).
	 * @param exponent The exponent of the fraction.
	 */
	public Fraction(boolean sign, AlgebraicParticle top, AlgebraicParticle bottom, int exponent) {
		super(sign, exponent);
		this.top = top;
		this.bottom = bottom;
	}
	
	/**
	 * Tells if a String is in the form of <i>(expression)</i>/<i>(expression)</i>.
	 * @param frac The String in question.
	 * @return If frac is a valid fraction.
	 */
	public static boolean parsable(String frac){
		//fractions can't be shorter than 7 chars
		if(frac.length() < 7)return false;
		
		//find the index of the first slash that is not nested in parentheses
		int divisionIndex = indexOfSlash(frac);
		if(divisionIndex < 1)return false;
		
		String top = frac.substring(0, divisionIndex);
		String bottom = frac.substring(divisionIndex + 1);
		int topLength = top.length(), bottomLength = bottom.length();
		
		top = Util.removePar(top);
		bottom = Util.removePar(bottom);
		
		//if pars din not surround top and bottom, return false
		if(top.length() == topLength || bottom.length() == bottomLength)return false;
		
		return AlgebraicParticle.parsable(top) && AlgebraicParticle.parsable(bottom);
	}
	
	/**
	 * @return The top expression of the fraction.
	 */
	public AlgebraicParticle getTop(){
		return this.top;	
	}
	
	/**
	 * @return The bottom expression of the fraction.
	 */
	public AlgebraicParticle getBottom(){
		return this.bottom;
	}
	
	/**
	 * Tells if the fraction is fully simplified, that is, that both top and bottom are
	 * instances of Number, are both whole numbers, the bottom is not negative, the 
	 * bottom is greater than the top, and they have no common factors (other than 1).
	 * @return True if the fraction is fully simplified, otherwise false.
	 */
	public boolean isSimplified(){
		if(this.top instanceof Number && this.bottom instanceof Number){
			Number top = (Number) this.top;
			Number bottom = (Number) this.bottom;
			//if they aren't whole numbers or the bottom is negative
			if(top.getDecimal() != null || bottom.getDecimal() != null || bottom.sign() == false) return false;
			else{
				//make sure bottom is greater than top (i.e., it's not an improper fraction) and they have no common factors
				long topNum = Long.parseLong(top.getInteger()), bottomNum = Long.parseLong(bottom.getInteger());
				return topNum < bottomNum && Number.GCF(topNum, bottomNum) == 1;
			}
		}
		else return false;
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#render()
	 */
	@Override
	public String render() {
		return wrapWithSignParAndExponent("(" + this.top.render() + ")/(" + this.bottom.render() + ")", true);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fraction [top=" + top + ", bottom=" + bottom + ", sign()="
				+ sign() + ", exponent()=" + exponent() + "]";
	}
	
	/**
	 * Tells the index of the first non-nested slash (/).
	 * If there is no slash not nested in parentheses, it returns -1
	 * Example:
	 * indexOfSlash("2/3") returns 1
	 * indexOfSlash("(4/5)") returns -1
	 * indexOfSlash("(4+6)/(8)") returns 5
	 * @param frac The fraction from which to search for the slash.
	 * @return The index of the first non-nested forward slash.
	 */
	private static int indexOfSlash(String frac){
		int parDepth = 0;
		for(int i = 0; i < frac.length(); i++){
			//if it's a parentheses
			if(frac.charAt(i) == '(') parDepth++;
			else if (frac.charAt(i) == ')') parDepth--;
			//find the / if parDepth is 0
			else if (parDepth == 0 && frac.charAt(i) == '/'){
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * @return True if both top and bottom are instances of Number. Keep in mind that a 
	 * fraction could be constant if it had a fraction on top and bottom, so strictly speaking,
	 * this method could be considered a misnomer.
	 */
	public boolean constant(){
		return top instanceof Number && bottom instanceof Number;
	}
	
	/**
	 * Tells if the denominators (bottoms) of this fraction and the given fraction f
	 * are the same.
	 * @param f The fraction to check.
	 * @return If the denominators of this fraction and f are equal.
	 */
	public boolean like(Fraction f){
		return f.bottom.equals(this.bottom);
	}
	
	/* (non-Javadoc)
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#cloneWithNewSign(java.lang.Boolean)
	 */
	@Override
	public Fraction cloneWithNewSign(Boolean sign) {
		return new Fraction(sign == null ? this.sign() : sign,
				this.top,
				this.bottom,
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
		result = prime * result + ((bottom == null) ? 0 : bottom.hashCode());
		result = prime * result + ((top == null) ? 0 : top.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		return super.equals(obj) && almostEquals(obj);
	}
	
	public boolean almostEquals(Object obj){
		if (this == obj)
			return true;
		if (getClass() != obj.getClass())
			return false;
		Fraction other = (Fraction) obj;
		if (bottom == null) {
			if (other.bottom != null)
				return false;
		} else if (!bottom.equals(other.bottom))
			return false;
		if (top == null) {
			if (other.top != null)
				return false;
		} else if (!top.equals(other.top))
			return false;
		return true;
	}
	
	/**
	 * Adds fractions a and b, which must both have numbers for numerators.
	 * @param a The first fraction to add.
	 * @param b The second fraction to add.
	 * @return a and b added.
	 * @throws IllegalArgumentException if the denominators of a and b differ.
	 */
	public static Fraction add(Fraction a, Fraction b, RoundingRule round) {
		//make sure the bottoms are equal
		if(!a.bottom.equals(b.bottom)) throw new IllegalArgumentException("Cannot add two fractions with differing denominators.");
		//cast the tops to Number
		Number a_top = (Number)a.top, b_top = (Number)b.top;
		//add the top, using the combined signs from the fraction and numerator ( -2/4 is the same as -(2/4) )
		Number top = Number.add(a_top.cloneWithNewSign(a_top.sign() == a.sign()), b_top.cloneWithNewSign(b_top.sign() == b.sign()), round);
		//construct a new fraction, making sure the signs are right
		return new Fraction(top.sign(), top.cloneWithNewSign(true), a.bottom, 1);
	}
	
}
