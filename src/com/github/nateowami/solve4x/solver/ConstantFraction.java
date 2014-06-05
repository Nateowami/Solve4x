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
 * A fraction containing constant numbers on top and bottom. Extends Fraction.
 * @author Nateowami
 */
public class ConstantFraction extends Fraction{

	/**
	 * @param frac
	 * @throws MalformedInputException
	 */
	public ConstantFraction(String frac) throws MalformedInputException {
		//just construct the fraction
		super(frac);
		//make sure it's valid
		if(this.getTop().numbOfTerms() != 1 || this.getBottom().numbOfTerms() != 1 ){
			throw new MalformedInputException(frac.length());
		}
		//now make sure the terms only have numbers
		Term t1 = this.getTop().termAt(0);
		Term t2 = this.getBottom().termAt(0);
		if(t1.numOfExprs() != 0 || t1.numOfVars()  != 0){
			throw new MalformedInputException(frac.length());
		}
	}
	
	/**
	 * Tells if a string is a constant fraction. Both top and bottom must be whole numbers or decimals.
	 * @param frac The fraction to check
	 * @return If frac is a. a fraction and b. constant.
	 * @throws MalformedInputException 
	 */
	public static boolean isConstantFraction(String frac) throws MalformedInputException{
		//if it's not even a fraction
		if (!Fraction.isFraction(frac)){
			return false;
		}
		//create a fraction and ask it if it's constant
		return new Fraction(frac).isConstant();
	}
	
	/**
	 * @return The value of the top of the fraction.
	 */
	public Number getConstantTop(){
		return super.getTop().termAt(0).getCoe();
	}
	
	/**
	 * @return The value of the bottom of the fraction.
	 */
	public Number getConstantBottom(){
		return super.getBottom().termAt(0).getCoe();
	}
	
}
