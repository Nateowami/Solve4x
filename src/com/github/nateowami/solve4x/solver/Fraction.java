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
 * Contains a fraction with expressions on top and bottom.
 * @author Nateowami
 */
public class Fraction extends AlgebraicParticle{
	
	protected AlgebraicParticle top, bottom;
	
	private static final Class[] subParts = {Variable.class, Root.class, MixedNumber.class, Number.class, Term.class, Expression.class};
	
	/**
	 * Constructs a new Fraction.
	 * @param frac The String from which to construct the fraction, in the form of
	 * <i>expression</i>/<i>expression</i>. If an expression has more than one term
	 * it must be surrounded by parentheses.
	 * @ 
	 */
	protected Fraction(String frac){
		//find the '/' not nested in parentheses	
		int divisionIndex = indexOfSlash(frac);
		//if divisionIndex is 0 there's a problem
		if(divisionIndex < 1) throw new ParsingException("Non-parentheses-nested '/' not found in proper range (char 1 to end of string)");
		//split the fraction at divisionIndex, but don't include the slashes or parentheses
		//it should be in the form of (expr1)/(expr2)
		this.top = AlgebraicParticle.getInstance(frac.substring(1, divisionIndex-1), subParts);
		this.bottom = AlgebraicParticle.getInstance(frac.substring(divisionIndex+2, frac.length()-1), subParts);
	}
	
	/**
	 * Tells if a String is in the form of <i>(expression)</i>/<i>(expression)</i>.
	 * @param frac The String in question.
	 * @return If frac is a valid fraction.
	 */
	public static boolean parseable(String frac){
		if(frac.length() < 7) return false;//can't possibly be right if it's shorter than 7 chars
		//make sure it's surrounded by parentheses and remove them if so
		int divisionIndex = indexOfSlash(frac) - 1;//-1 because we're about to remove the first char
		if(frac.charAt(0) == '(' && frac.charAt(frac.length()-1) == ')') frac = frac.substring(1, frac.length()-1);
		else return false;
		//make sure there's ) before and ( after the slash
		if(divisionIndex >= 2 && divisionIndex <= frac.length() - 3 && frac.charAt(divisionIndex-1) == ')' && frac.charAt(divisionIndex+1) == '('){
			return AlgebraicParticle.parseable(frac.substring(0, divisionIndex-1), subParts) 
					&& AlgebraicParticle.parseable(frac.substring(divisionIndex+2, frac.length()), subParts);
		}
		else return false;
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
	 * @see com.github.nateowami.solve4x.solver.AlgebraicParticle#getAsString()
	 */
	@Override
	public String getAsString() {
		return wrapWithSignAndExponent("(" + this.top.getAsString() + ")/(" + this.bottom.getAsString() + ")");
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
	
}
