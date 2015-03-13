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
package com.github.nateowami.solve4x.config;

/**
 * Provides settings for rounding by significant figures, or rounding to x decimal places.
 * @author Nateowami
 */
public class RoundingRule {
	
	/**
	 * Provides three values, ALWAYS, FOR_SCIENTIFIC_NOTATION_AND_DECIMALS, and FOR_SCIENTIFIC_NOTATION.
	 * Each of these are rules that may be used when performing operations on numbers. Their meanings are 
	 * as follows:
	 * ALWAYS:
	 * Always use the rules for significant figures and decimal places when performing operations.
	 * FOR_SCIENTIFIC_NOTATION_AND_DECIMALS:
	 * Use the rules for significant figures and decimal places when performing operations on decimals and 
	 * numbers in scientific notation. For whole numbers, only round when dividing, leaving two significant 
	 * decimal places. For operations with whole numbers and decimals or scientific notation, use significant
	 * figure and decimal place rounding rules.
	 * FOR_SCIENTIFIC_NOTATION:
	 * Use the rules for significant figures and decimal places when performing operations on numbers in 
	 * scientific notation. For whole numbers and decimals, only round when dividing, leaving two significant 
	 * decimal places. For operations with whole numbers or decimals and scientific notation, use significant
	 * figure and decimal place rounding rules.
	 * Or a RoundingRule may be constructed from and int, and operations will be rounded out to that many 
	 * significant decimal places when multiplying or dividing.
	 * @author Nateowami
	 */
	public enum  BySignificantFigures {
		ALWAYS(-1), FOR_SCIENTIFIC_NOTATION_AND_DECIMALS(-2), FOR_SCIENTIFIC_NOTATION(-2);
		int value;
		private BySignificantFigures(int value){
			this.value = value;
		}
	}
	
	//Declare constant versions of this class
	public static final RoundingRule ALWAYS = new RoundingRule(BySignificantFigures.ALWAYS),
			FOR_SCIENTIFIC_NOTATION_AND_DECIMALS = new RoundingRule(BySignificantFigures.FOR_SCIENTIFIC_NOTATION_AND_DECIMALS),
			FOR_SCIENTIFIC_NOTATION = new RoundingRule(BySignificantFigures.FOR_SCIENTIFIC_NOTATION);
	
	int value;
	
	/**
	 * Constructs a new RoundingRule from val.
	 * @param val The rule to use.
	 */
	private RoundingRule(BySignificantFigures val) {
		this.value = val.value;
	}
	
	/**
	 * Constructs a new rounding rule from val, which will be used as the number of significant decimal 
	 * places to leave when performing mathematical operations. val must be greater than 0.
	 * @param val The number of significant decimal places to leave when performing operations.
	 */
	public RoundingRule(int val) {
		if(val <= 0) throw new IllegalArgumentException("Cannot construct a rounding rule from a value less than or equal to 0.");
		this.value = val;
	}
	
	/**
	 * @return The number of significant decimal digits to round to.
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Tells if this RoundingRule is one of the predefined ("canned") rules. In other words, it returns true 
	 * if this rule is ALWAYS, FOR_SCIENTIFIC_NOTATION_AND_DECIMALS, or FOR_SCIENTIFIC_NOTATION. Otherwise 
	 * false. A constructed rule can never be equal to a predefined rule.
	 * @return If this is a predefined rule.
	 */
	public boolean isCannedRule(){
		return this.value < 0;
	}
	
}
