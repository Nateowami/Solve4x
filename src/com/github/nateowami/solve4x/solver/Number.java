/*
    Solve4x - An audio-visual algebra solver
    Copyright (C) 2014 Nathaniel Paulus

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
 * Represents a number/fraction/mixed-number such as 35, 45/6, 56&lt;36&gt;&lt;32&gt;, and 
 * provides methods for adding, subtracting, multiplying, and dividing numbers. TODO
 * @author Nateowami
 */
public class Number {
	
	//The numerical part of a mixed number (could be a decimal)
	String wholeNumber;
	//the top of a fraction
	int top;
	//and the bottom
	int bottom;
	
	public Number(String num){
		//parse the number
		int i;
		for(i = 0; i<num.length(); i++){
			//if the current char is not a numeral
			if(!Util.isNumeral(num.charAt(i))){
				break;
			}
		}
		//now we have the index of the first non-numeral, or else just the end of the string
		//(if they're all numerals)
		wholeNumber = num.substring(0, i);
		//parse the fraction part
		parseFraction(num.substring(i+1, num.length()));
	}
	
	/**
	 * Parses the fraction part of a number and initialises the vars top and bottom
	 * @param frac The fraction to parse
	 */
	private void parseFraction(String frac){
		//the index of the /
		int indexOfDiv = 0;
		for(int i = 0; i< frac.length(); i++){
			if(frac.charAt(i) == '/'){
				indexOfDiv = i;
				break;
			}
		}
		//now parse the top and bottom of the fraction, namely everything
		//before indexOfDiv and everything after it
		for(int i = 0; i<2; i++){
			//set sideOfFrac
			String sideOfFrac;
			if(i == 0) sideOfFrac = frac.substring(0, indexOfDiv);
			else sideOfFrac = frac.substring(indexOfDiv+1, frac.length());
			//remove any carets that may be on both sides
			if(sideOfFrac.charAt(0) == '<' && sideOfFrac.charAt(sideOfFrac.length()) == '>'){
				sideOfFrac = sideOfFrac.substring(1, sideOfFrac.length()-1);
			}
			if(i == 0) top = Integer.parseInt(sideOfFrac);
			else bottom = Integer.parseInt(sideOfFrac);
		}
	}

	/**
	 * @return the wholeNumber part of the mixed number
	 */
	public String getWholeNumber() {
		return wholeNumber;
	}

	/**
	 * @return the top of the fraction
	 */
	public int getTop() {
		return top;
	}

	/**
	 * @return the bottom of the fraction
	 */
	public int getBottom() {
		return bottom;
	}
	
}
