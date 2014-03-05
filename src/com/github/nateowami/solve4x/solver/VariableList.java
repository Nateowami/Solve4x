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

import java.util.Arrays;

/**
 * Represents a list of variables in a Term. For example, the term 2xy2 would store xy2 as a variable list.
 * @author Nateowami
 */
public class VariableList {
	//the variables in this list
	private char vars[];
	//the powers to which the variables are raised (1 if ther is no power)
	private int powers[];
	
	/**
	 * Constructs a new VariableList.
	 * @param s The String from which to construct the VariableList
	 */
	VariableList(String s){
		//keep track of how many variables there are
		int numOfVars = 0;
		for(int i = 0; i < s.length(); i++){
			//if it's a variable add one to the number of vars
			if(Util.isLetter(s.charAt(i))){
				numOfVars++;
			}
		}
		//create the arrays at the proper length
		vars = new char[numOfVars];
		powers = new int[numOfVars];
		//make all powers 1 by default
		Arrays.fill(powers, 1);
		//and fill the arrays char by char, int by int
		//the index of the arrays we're currently trying to fill
		int index = 0;
		//index is not the same as i. I tells which char of string s we are parsing,
		//index tells where we are putting it.
		for (int i = 0; i < s.length(); i++){
			//the current char has to be a var/letter
			vars[index] = s.charAt(i);
			//if there is still another char and it's a numeral
			//stops with a break
			for(;;){
				//
				if(i < s.length()-1 && Util.isNumeral(s.charAt(i+1))){
					//add one because we're going to parse another char
					i++;
					//init the power
					System.out.println("Here it is: " + s);
					System.out.println(s.charAt(2) == 50);
					powers[index] = Character.getNumericValue(s.charAt(i));
				}
				//it wasn't a numeral, or else we've reached the end
				else break;
			}
			index++;
		}
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VariableList [vars=" + Arrays.toString(vars) + ", powers="
				+ Arrays.toString(powers) + "]";
	}
	
	//TODO add getters
	
}
