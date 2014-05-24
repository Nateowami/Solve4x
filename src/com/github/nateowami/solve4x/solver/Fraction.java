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
 * Contains a fraction with expressions on top and bottom.
 * @author Nateowami
 */
public class Fraction {
	
	private Expression top, bottom;
	boolean isConstant;
	
	/**
	 * Constructs a new Fraction.
	 * @param frac The String from which to construct the fraction, in the form of
	 * <i>expression</i>/<i>expression</i>. If an expression has more than one term
	 * it must be surrounded by parentheses.
	 * @throws MalformedInputException 
	 */
	public Fraction(String frac) throws MalformedInputException{
		//find the '/'
		int parDepth = 0, divisionIndex = 0;
		for(int i = 0; i < frac.length(); i++){
			//if it's a parentheses
			if(Util.isOpenPar(frac.charAt(i))) parDepth++;
			else if (Util.isClosePar(frac.charAt(i))) parDepth--;
			//find the / if parDepth is 0
			else if (parDepth == 0 && frac.charAt(i) == '/'){
				divisionIndex = i;
				break;
			}
		}
		//if divisionIndex is 0 there's a problem
		if(divisionIndex == 0) throw new MalformedInputException(0);
		//split the fraction at divisionIndex
		String frac1 = frac.substring(0, divisionIndex), frac2 = frac.substring(divisionIndex+1, frac.length());
		Expression expr1 = new Expression(Util.removePar(frac1)), expr2 = new Expression(Util.removePar(frac2));
		//Make a fuss if the expressions weren't surrounded by parentheses by had more than
		//one expression (i.e. are the same with and without parentheses removed)
		if(expr1.numbOfTerms() > 1 && Util.removePar(frac1).equals(frac1) || 
				expr2.numbOfTerms() > 1 && Util.removePar(frac2).equals(frac1)){
			throw new MalformedInputException(0);
		}
		//everything's good to go; init top and bottom
		this.top = expr1;
		this.bottom = expr2;
		//figure out if the fraction is a constant
		if(Number.isNumber(Util.removePar(frac1)) && Number.isNumber(Util.removePar(frac2))){
			isConstant = true;
		}
	}
	
	public static boolean isFraction(String frac){
		//TODO
		return false;
	}
	
	public boolean isConstant(){
		return isConstant;
	}
	
	public Expression getTop(){
		return this.top;	
	}
	
	public Expression getBottom(){
		return this.bottom;
	}
	
}
