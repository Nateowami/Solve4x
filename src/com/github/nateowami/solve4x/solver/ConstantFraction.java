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

import java.math.BigDecimal;
import java.nio.charset.MalformedInputException;

/**
 * A fraction containing constant numbers on top and bottom. Not to be confused with Fraction.
 * TODO add support for decimals, subtraction, and multiplication (division not necessary as it is 
 * multiplication by the reciprical). 
 * @author Nateowami
 * @see {@link com.github.nateowami.solve4x.solver.Fraction}
 */
public class ConstantFraction {

	Number top, bottom;
	
	/**
	 * Constructs a new ConstantFraction.
	 * @param frac The fraction to parse.
	 * @throws MalformedInputException If frac is not in the form of <i>Number</i>/<i>Number</i>.
	 */
	public ConstantFraction(String frac) throws MalformedInputException {
		//find the fraction bar
		int i = 0;
		for(; i < frac.length(); i++){
			if(frac.charAt(i) == '/'){
				break;
			}
		}
		//error; bar wasn't found (or was at index 0)
		if(i == 0){
			
			throw new MalformedInputException(frac.length());
		}
		//parse the two numbers
		this.top = new Number(frac.substring(0,i));
		this.bottom = new Number(frac.substring(i+1,frac.length()));
	}
	
	/**
	 * Tells if a string is a constant fraction. Both top and bottom must be whole numbers or decimals.
	 * @param frac The fraction to check
	 * @return If frac is a. a fraction and b. constant.
	 * @throws MalformedInputException 
	 */
	public static boolean isConstantFraction(String frac){
		int i = frac.indexOf('/');
		//make sure there's at least one char on each side of i in the fraction
		if(i > 0 && i < frac.length()-1){
			return Number.isNumber(frac.substring(0,i)) && Number.isNumber(frac.substring(i+1,frac.length()));
		}
		else return false;
	}
	
	/**
	 * @return The value of the top of the fraction.
	 */
	public Number getTop(){
		return this.top;
	}
	
	/**
	 * @return The value of the bottom of the fraction.
	 */
	public Number getBottom(){
		return this.bottom;
	}
	
	public static ConstantFraction add(ConstantFraction frac1, ConstantFraction frac2) throws MalformedInputException{
		//if the denominators aren't the same
		if(frac1.bottom.equals(frac2.bottom)){
			throw new MalformedInputException(0);
		}
		//add frac1.top and frac2.top
		String addedTop = "";
		//if they're ints
		if(Util.isInteger(frac1.top.getAsString()) && Util.isInteger(frac2.top.getAsString())){
			addedTop = "" + (Integer.parseInt(frac1.top.getAsString()) + Integer.parseInt(frac2.top.getAsString()));
		}
		else{
			//add them with BigDecimal
			BigDecimal dec1 = new BigDecimal(frac1.top.getAsString());
			BigDecimal dec2 = new BigDecimal(frac2.top.getAsString());
			addedTop = dec1.add(dec2).toString();
		}
		return new ConstantFraction(addedTop + '/' + frac1.bottom.getAsString());
	}

	
	/**
	 * @return A string representation of the fraction, not the data (e.g. "2/3" rather than 
	 * "ConstantFraction [top="somevalue" bottom="somevalue"]".
	 */
	public Object getAsString() {
		return this.top.getAsString() + "/" + this.bottom.getAsString();
	}
	
}
