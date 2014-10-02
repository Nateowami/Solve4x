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
	 * @throws MalformedInputException 
	 */
	protected Fraction(String frac) throws MalformedInputException{
		//find the '/' not nested in parentheses	
		int divisionIndex = indexOfSlash(frac);
		//if divisionIndex is 0 there's a problem
		if(divisionIndex < 1) throw new MalformedInputException(frac.length());
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
		int divisionIndex = indexOfSlash(frac);
		if (divisionIndex < 1) return false;
		return AlgebraicParticle.parseable(frac.substring(0, divisionIndex), subParts) 
				&& AlgebraicParticle.parseable(frac.substring(divisionIndex+1, frac.length()), subParts);
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
